package message;

import java.io.Serializable;

public class InitMessage implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private int id;

    private char[][] tab;

    private SCMessage scMessage;

    public InitMessage(){}

    public InitMessage(int id, char[][] tab, SCMessage scMessage){
        this.id = id;
        this.tab = tab;
        this.scMessage = scMessage;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public char[][] getTab() {
        return this.tab;
    }

    public void setTab(char[][] tab) {
        this.tab = tab;
    }

    public SCMessage getScMessage() {
        return this.scMessage;
    }

    public void setScMessage(SCMessage scMessage) {
        this.scMessage = scMessage;
    }


}