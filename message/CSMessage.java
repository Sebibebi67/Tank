package message;

import java.io.Serializable;

public class CSMessage implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Boolean[] keys;

    private Boolean mouseClicked;
    private int xMouse;
    private int yMouse;

    private int id;

    public CSMessage(){}

    public CSMessage(Boolean[] keys, Boolean mouseClicked, int xMouse, int yMouse, int id){
        this.keys = keys;
        this.mouseClicked = mouseClicked;
        this.xMouse = xMouse;
        this.yMouse = yMouse;
        this.id = id;
    }

    public Boolean[] getKeys() {
        return this.keys;
    }

    public void setKeys(Boolean[] keys) {
        this.keys = keys;
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
}