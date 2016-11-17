package org.pawkrol.academic.mes.project1;

import org.pawkrol.academic.mes.project1.entities.InputEntity;
import org.pawkrol.academic.mes.project1.entities.Mesh;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by pawkrol on 10/31/16.
 */
public class Main {

    public static void main(String[] args){
        String file = readFile(Main.class.getResource("/input_data3.txt"));
        InputEntity inputEntity = new InputEntity(file);
        Mesh mesh = new Mesh(inputEntity);
        mesh.resolveAndDisplay();
    }

    public static String readFile(URL url){
        StringBuilder builder = new StringBuilder();

        try {
            Path path = Paths.get(url.toURI());
            Files.lines(path).forEachOrdered(l -> builder.append(l).append("\n"));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

        return builder.toString();
    }
}
