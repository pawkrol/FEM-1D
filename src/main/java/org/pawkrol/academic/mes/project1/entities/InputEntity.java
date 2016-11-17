package org.pawkrol.academic.mes.project1.entities;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pawkrol on 10/31/16.
 */
public class InputEntity {

    private int ne;
    private int nh;
    private double alpha;
    private double envT;
    private double q;
    private List<Element> elements;

    public InputEntity(String file){
        try {
            setAttributesFromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getNe() {
        return ne;
    }

    public int getNh() {
        return nh;
    }

    public double getAlpha() {
        return alpha;
    }

    public double getEnvT() {
        return envT;
    }

    public double getQ() {
        return q;
    }

    public List<Element> getElements() {
        return elements;
    }

    private void setAttributesFromFile(String file) throws IOException{
        StringReader stringReader = new StringReader(file);
        BufferedReader reader = new BufferedReader(stringReader);

        Map<Integer, Node> nodesMap = new HashMap<>();
        elements = new ArrayList<>();

        String line;
        while ((line = reader.readLine()) != null){
            String tokens[] = line.split("\\s+");

            switch (tokens[0]){
                case "#":
                    break;

                case "n":
                    Node n = new Node();

                    int index = Integer.parseInt(tokens[1]);
                    n.setIndex(index);
                    n.setType(Node.Type.NORMAL);

                    double x = Double.parseDouble(tokens[2]);
                    n.setX(x);

                    nodesMap.put(index, n);
                    break;

                case "t":
                    Node.Type type = Node.Type.fromInt(Integer.parseInt(tokens[2]));
                    nodesMap.get(Integer.parseInt(tokens[1])).setType(type);
                    break;

                case "e":
                    Element e = new Element();
                    int nodeIdx;

                    nodeIdx = Integer.parseInt(tokens[1]);
                    e.setN1(nodesMap.get(nodeIdx));

                    nodeIdx = Integer.parseInt(tokens[2]);
                    e.setN2(nodesMap.get(nodeIdx));

                    elements.add(e);
                    break;

                case "p":
                    int sElementID = Integer.parseInt(tokens[1]);
                    int eElementID = Integer.parseInt(tokens[2]);
                    double S = Double.parseDouble(tokens[3]);
                    double k = Double.parseDouble(tokens[4]);

                    for (int i = sElementID; i <= eElementID; i++){
                        Element element = elements.get(i);
                        element.setS(S);
                        element.setK(k);
                    }

                    break;

                case "a":
                    alpha = Double.parseDouble(tokens[1]);
                    break;

                case "T":
                    envT = Double.parseDouble(tokens[1]);
                    break;

                case "q":
                    q = Double.parseDouble(tokens[1]);
                    break;
            }
        }

        nh = nodesMap.size();
        ne = elements.size();
    }
}