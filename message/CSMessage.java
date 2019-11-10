package message;

import java.io.Serializable;

public class CSMessage implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Boolean[] keys = {
        false, false, false, false
    };

    private Boolean mouseClicked;
    private int xMouse;
    private int yMouse;

    private int id;

    private double diff;

    public CSMessage(){}

    public CSMessage(Boolean[] keys, Boolean mouseClicked, int xMouse, int yMouse, int id, double diff){
        for (int i=0; i<4; i++) {
            this.keys[i] = keys[i];
        }
        this.mouseClicked = mouseClicked;
        this.xMouse = xMouse;
        this.yMouse = yMouse;
        this.id = id;
        this.diff = diff;
    }

    public Boolean[] getKeys() {
        return this.keys;
    }

    public void setKeys(Boolean[] keys) {
        for (int i=0; i<4; i++) {
            this.keys[i] = keys[i];
        }
    }

    public Boolean getMouseClicked() {
        return this.mouseClicked;
    }

    public void isMouseClicked(Boolean mouseClicked) {
        this.mouseClicked = mouseClicked;
    }

    public int getXMouse() {
        return this.xMouse;
    }

    public void setXMouse(int xMouse) {
        this.xMouse = xMouse;
    }

    public int getYMouse() {
        return this.yMouse;
    }

    public void setYMouse(int yMouse) {
        this.yMouse = yMouse;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getDiff() {
        return this.diff;
    }

    public void setDiff(double diff) {
        this.diff = diff;
    }
}