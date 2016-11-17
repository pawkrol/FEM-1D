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

    private int ne = 1;
    private int nh = 1;
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

    public void setNe(int ne) {
        this.ne = ne;
    }

    public int getNh() {
        return nh;
    }

    public void setNh(int nh) {
        this.nh = nh;
    }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public double getEnvT() {
        return envT;
    }

    public void setEnvT(double envT) {
        this.envT = envT;
    }

    public double getQ() {
        return q;
    }

    public void setQ(double q) {
        this.q = q;
    }

    public List<Element> getElements() {
        return elements;
    }

    public void setElements(List<Element> elements) {
        this.elements = elements;
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
                    ne++;

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
                    nh++;

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
    }
}