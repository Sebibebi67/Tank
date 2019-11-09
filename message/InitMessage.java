package message;

import java.io.Serializable;
import java.util.ArrayList;

public class InitMessage implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private int id;

    private char[][] tab;

    private ArrayList<SCMessage> Messages;

    public InitMessage(){}

    public InitMessage(int id, char[][] tab, ArrayList<SCMessage> Messages){
        this.id = id;
        this.tab = tab;
        this.Messages = Messages;
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

    public ArrayList<SCMessage> getMessages() {
        return this.Messages;
    }

    public void setMessages(ArrayList<SCMessage> Messages) {
        this.Messages = Messages;
    }



}