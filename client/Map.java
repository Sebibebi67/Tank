package client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import java.awt.*;

public class Map{

    private int cellSize = 40;
    private int nbMapMax = 1;
    private int sizeX = 1600, sizeY = 800;

    private File file;
    private char[][] tab;
    private ArrayList<Rectangle> walls;

    public Map(SPanel panel){
        this.initFile();
        this.readFile();
        //this.print();
        this.makeWalls();
    }

    public void initFile(){
        Random r = new Random();
        int nb = r.nextInt(nbMapMax) + 1;
        // System.out.println(nb);
        file =  new File("./ressources/map/map"+nb);
    }

    public void readFile(){
        tab = new char[20][40];

        try {
            //opening file for reading in Java
            FileInputStream fileIS = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileIS));
         
            //reading file content line by line
            String line = reader.readLine();
            int lineNb = 0;
            while(line != null){
                // System.out.println(line);
                int charNb = 0;
                for (char c : line.toCharArray()) {
                    tab[lineNb][charNb] = c;
                    charNb++;
                }
                lineNb++;
                line = reader.readLine();
            }
                 
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void print(){
        for (int i=0; i<sizeY/cellSize ; i++) {
            for (int j=0; j<sizeX/cellSize; j++){
                System.out.print(tab[i][j]);
            }
            System.out.println();
        }
    }

    public void display(Graphics g){
        for (int i=0; i<sizeY/cellSize ; i++) {
            for (int j=0; j<sizeX/cellSize; j++){
                switch(tab[i][j]){
                    case 'W': //Wall
                        //System.out.println(i+" "+j+" "+cellSize);
                        g.setColor(Color.DARK_GRAY);
                        g.fillRect(j*cellSize, i*cellSize, cellSize, cellSize);
                        break;
                    case 'D': //Durt
                        g.setColor(Color.GRAY);
                        g.fillRect(j*cellSize, i*cellSize, cellSize, cellSize);
                        break;
                }
            }
        }
    }

    public char[][] getTab(){return tab;}
    public int getCellSize(){return cellSize;}

    public Boolean wallCollision(Shape s){
        for (int i = 0 ; i<walls.size(); i++){
            if (s.intersects(walls.get(i))){
                return true;
            }
        }
        return false;
    }

    public void makeWalls(){
        walls = new ArrayList<Rectangle>();
        for (int i=0; i<sizeY/cellSize ; i++) {
            for (int j=0; j<sizeX/cellSize; j++){
                if (tab[i][j] == 'W'){
                    walls.add(new Rectangle(j*cellSize, i*cellSize, cellSize, cellSize));
                }
            }
        }
    }
}