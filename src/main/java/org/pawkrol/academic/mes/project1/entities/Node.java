package org.pawkrol.academic.mes.project1.entities;

/**
 * Created by pawkrol on 10/31/16.
 */
public class Node {

    public enum Type{
        NORMAL,
        STREAM,
        CONVECTION;

        public static Type fromInt(int id){
            switch (id){
                case 0:
                    return NORMAL;
                case 1:
                    return STREAM;
                case 2:
                    return CONVECTION;
            }
            return NORMAL;
        }
    }

    private int index;
    private double x;
    private Type type;

    public Node() {}

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
