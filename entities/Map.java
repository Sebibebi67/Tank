package entities;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.awt.*;
import java.awt.geom.Area;

public class Map{

    private static int cellSize = 40;
    private int nbMapMax = 3;
    private static int sizeX = 1600;
    private static int sizeY = 800;

    private File file;
    private char[][] tab;
    private Area wallsArea;

    public Map() {
        this.initFile();
        this.readFile();
        // this.print();
        this.makeWalls();
    }

    public Map(char[][] tab) {
        this.tab = tab;
        this.wallsArea = null;
        this.file = null;
        this.makeWalls();
    }

    public static void displayMap(Graphics g, char[][] tab) {
        for (int i = 0; i < sizeY / cellSize; i++) {
            for (int j = 0; j < sizeX / cellSize; j++) {
                switch (tab[i][j]) {
                case 'W': // Wall
                    g.setColor(Color.DARK_GRAY);
                    g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
                        break;
                    case 'D': //Durt
                        g.setColor(Color.GRAY);
                        g.fillRect(j*cellSize, i*cellSize, cellSize, cellSize);
                        break;
                }
            }
        }
    }

    public void initFile(){
        Random r = new Random();
        int nb = r.nextInt(nbMapMax) + 1;
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

    public int getCellSize(){return cellSize;}
    public char[][] getTab(){return tab;}

    public Boolean wallCollision(Rectangle r){
        if ( wallsArea.intersects(r)){
            return true;
        }
        return false;
    }

    public void makeWalls(){
        wallsArea = new Area();
        for (int i=0; i<sizeY/cellSize ; i++) {
            for (int j=0; j<sizeX/cellSize; j++){
                if (tab[i][j] == 'W'){
                    wallsArea.add(new Area(new Rectangle(j*cellSize, i*cellSize, cellSize, cellSize)));
                }
            }
        }
    }
}