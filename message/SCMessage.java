package message;

import java.io.Serializable;
import java.util.ArrayList;

public class SCMessage implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private double alphaCanon;
    private double alphaMove;

    private double xTank;
    private double yTank;

    private ArrayList<Double[]> shotsPos;

    private int id;

    public SCMessage(){}

    public SCMessage(double alphaCanon, double alphaMove, double xTank, double yTank, ArrayList<Double[]> shotsPos, int id){
        this.alphaCanon = alphaCanon;
        this.alphaMove = alphaMove;
    
        this.xTank = xTank;
        this.yTank = yTank;
    
        this.shotsPos = shotsPos;
    
        this.id = id;
    }

    public double getAlphaCanon() {
        return this.alphaCanon;
    }

    public void setAlphaCanon(double alphaCanon) {
        this.alphaCanon = alphaCanon;
    }

    public double getAlphaMove() {
        return this.alphaMove;
    }

    public void setAlphaMove(double alphaMove) {
        this.alphaMove = alphaMove;
    }

    public double getXTank() {
        return this.xTank;
    }

    public void setXTank(double xTank) {
        this.xTank = xTank;
    }

    public double getYTank() {
        return this.yTank;
    }

    public void setYTank(double yTank) {
        this.yTank = yTank;
    }

    public ArrayList<Double[]> getShotsPos() {
        return this.shotsPos;
    }

    public void setShotsPos(ArrayList<Double[]> shotsPos) {
        this.shotsPos = shotsPos;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
}

