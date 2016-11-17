package org.pawkrol.academic.mes.project1.entities;

import org.apache.commons.math3.linear.*;

import java.util.List;

/**
 * Created by pawkrol on 10/31/16.
 */
public class Mesh {

    private InputEntity inputEntity;
    private RealMatrix gH;
    private RealMatrix gP;

    public Mesh(InputEntity inputEntity){
        this.inputEntity = inputEntity;
    }

    public void resolveAndDisplay(){
        resolveLHMatrices();
        resolveLPMatrices();
        createGHMatrix();
        createGPMatrix();

        DecompositionSolver solver = new LUDecomposition(gH).getSolver();
        RealVector vector = gP.scalarMultiply(-1).getColumnVector(0);
        RealVector solution = solver.solve(vector);

        displayMatrixForm();

        System.out.println("\nSolution:");
        displayVectorHorizontal("t", solution);
    }

    private void resolveLHMatrices(){
        List<Element> elements = inputEntity.getElements();

        for (Element e: elements){
            e.calculateLHMatrix(inputEntity.getAlpha());
        }
    }


    private void resolveLPMatrices(){
        List<Element> elements = inputEntity.getElements();

        for (Element e: elements){
            e.calculateLPMatrix(inputEntity);
        }
    }

    private RealMatrix createGHMatrix(){
        int nh = inputEntity.getNh();
        gH = MatrixUtils.createRealMatrix(nh, nh);
        List<Element> elements = inputEntity.getElements();
        int idx1, idx2;

        for (Element e: elements){
            idx1 = e.getN1().getIndex() - 1; //to numerate from 0
            idx2 = e.getN2().getIndex() - 1; //to numerate from 0
            RealMatrix lH = e.getlH();

            gH.addToEntry(idx1, idx1, lH.getEntry(0, 0));
            gH.addToEntry(idx1, idx2, lH.getEntry(0, 1));
            gH.addToEntry(idx2, idx1, lH.getEntry(1, 0));
            gH.addToEntry(idx2, idx2, lH.getEntry(1, 1));
        }

        return gH;
    }

    private RealMatrix createGPMatrix(){
        int nh = inputEntity.getNh();
        gP = MatrixUtils.createRealMatrix(nh, 1);
        List<Element> elements = inputEntity.getElements();
        int idx1, idx2;

        for (Element e: elements) {
            idx1 = e.getN1().getIndex() - 1; //to numerate from 0
            idx2 = e.getN2().getIndex() - 1;
            RealMatrix lP = e.getlP();

            gP.addToEntry(idx1, 0, lP.getEntry(0, 0));
            gP.addToEntry(idx2, 0, lP.getEntry(1, 0));
        }

        return gP;
    }

    private void displayMatrix(RealMatrix m){
        double[][] data = m.getData();
        int rows = m.getRowDimension();
        int cols = m.getColumnDimension();

        for (int i = 0; i < rows; i++){
            System.out.print("| ");

            for (int j = 0; j < cols; j++){
                System.out.printf("%10.3f ", data[i][j]);
            }

            System.out.print("\t|\n");
        }
    }

    private void displayVectorVertical(RealVector v){
        int length = v.getDimension();

        for (int i = 0; i < length; i++){
            System.out.printf("| %10.3f |\n", v.getEntry(i));
        }
    }

    private void displayVectorHorizontal(String name, RealVector v){
        int length = v.getDimension();

        System.out.print("{" + name + "} = {");
        for (int i = 0; i < length; i++){
            System.out.printf("%10.3f", v.getEntry(i));
        }
        System.out.println("\t}");
    }

    private void displayMatrixForm(){
        List<Element> elements = inputEntity.getElements();
        int ne = inputEntity.getNe();

        double[][] hData = gH.getData();
        double[][] pData = gP.getData();

        int hRows = gH.getRowDimension();
        int hCols = gH.getColumnDimension();
        int pRows = gP.getRowDimension();

        assert (hRows == pRows);

        for (int i = 0; i < hRows; i++){
            System.out.print("| ");

            for (int j = 0; j < hCols; j++){
                System.out.printf("%10.3f ", hData[i][j]);
            }

            System.out.print("\t|\t");

            if (i == (hRows/2)) System.out.print("*");

//            int idx;
//            if (i < ne){
//                idx = elements.get(i).getN1().getIndex();
//            } else {
//                idx = elements.get(ne - 1).getN2().getIndex();
//            }
            System.out.printf("\t| t%d |\t", i + 1);

            if (i == (hRows/2)) System.out.print("+");

            System.out.printf("\t| %10.3f |", pData[i][0]);

            if (i == (hRows/2)) System.out.print("\t=\t0");

            System.out.println();
        }
    }
}
