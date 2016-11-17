package org.pawkrol.academic.mes.project1.entities;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

/**
 * Created by pawkrol on 10/31/16.
 */
public class Element {

    private Node n1;
    private Node n2;

    private double s;
    private double k;

    private RealMatrix lH;
    private RealMatrix lP;

    public Element() {
    }

    public Element(Node n1, Node n2) {
        this.n1 = n1;
        this.n2 = n2;
    }

    public void calculateLHMatrix(double alpha){
        double l = getL();

        double c = (k * s) / l;

        RealMatrix h = MatrixUtils.createRealMatrix(
                new double[][]{ {c, -c}, {-c, c} }
        );

        RealMatrix hs = MatrixUtils.createRealMatrix(
                new double[][]{ {0, 0}, {0, 0} }
        );

        if (n1.getType() == Node.Type.CONVECTION){
            hs.setEntry(0, 0, 1);
            double aS = alpha * s;
            hs = hs.scalarMultiply(aS);
        } else if (n2.getType() == Node.Type.CONVECTION){
            hs.setEntry(1, 1, 1);
            double aS = alpha * s;
            hs = hs.scalarMultiply(aS);
        }

        lH = h.add(hs);
    }

    public void calculateLPMatrix(InputEntity inputEntity){
        Node.Type nt1 = n1.getType();
        Node.Type nt2 = n2.getType();

        //if both nodes are normal
        if ( (nt1 == Node.Type.NORMAL) && (nt2 == Node.Type.NORMAL) ){
            lP = MatrixUtils.createRealMatrix(new double[][]{ {0}, {0} });
            return;
        }

        //convection part of P matrix
        RealMatrix cP = MatrixUtils.createRealMatrix(
                new double[][]{ {0}, {0} }
        );

        //stream part of P matrix
        RealMatrix sP = MatrixUtils.createRealMatrix(
                new double[][]{ {0}, {0} }
        );

        if (nt1 == Node.Type.CONVECTION){
            cP.setEntry(0, 0, 1);
        } else if (nt1 == Node.Type.STREAM){
            sP.setEntry(0, 0, 1);
        }

        if (nt2 == Node.Type.CONVECTION){
            cP.setEntry(1, 0, 1);
        } else if (nt2 == Node.Type.STREAM){
            sP.setEntry(1, 0, 1);
        }

        double envT = inputEntity.getEnvT();
        double alpha = inputEntity.getAlpha();
        double q = inputEntity.getQ();

        double convFactor = s * envT * alpha;
        double streamFactor = q * s;

        cP = cP.scalarMultiply(-convFactor);
        sP = sP.scalarMultiply(streamFactor);

        lP = cP.add(sP);
    }

    public Node getN1() {
        return n1;
    }

    public void setN1(Node n1) {
        this.n1 = n1;
    }

    public Node getN2() {
        return n2;
    }

    public void setN2(Node n2) {
        this.n2 = n2;
    }

    public double getS() {
        return s;
    }

    public void setS(double s) {
        this.s = s;
    }

    public double getK() {
        return k;
    }

    public void setK(double k) {
        this.k = k;
    }

    public double getL(){
        double x1 = n1.getX();
        double x2 = n2.getX();

        return Math.abs(x2 - x1);
    }

    public RealMatrix getlH() {
        return lH;
    }

    public RealMatrix getlP() {
        return lP;
    }
}
