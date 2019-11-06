import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.awt.*;

import static java.lang.System.out;

public class Map{

    private int cellSize = 40;
    private int nbMapMax = 1;
    private int sizeX = 1600, sizeY = 800;

    private File file;
    private char[][] tab;
    private Graphics g = null;

    public Map(SPanel panel){
        g = panel.getGraphics();
        this.initFile();
        this.readFile();
        this.display();
        //this.print();
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
                System.out.println(line);
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

		// try{
		// 	BufferedReader reader = new BufferedReader (new FileReader(file));
		// 	int c;

		// 	for (int i=0; i<sizeY/cellSize ; i++) {
		// 		for (int j=0; j<sizeX/cellSize ; j++){
		// 			c=reader.read();
		// 			char car = (char) c;
		// 			if (c != -1 && c != '\n'){
		// 				tab[i][j] = car;
		// 			}else if (c =='\n'){
        //                 j--;
        //             }

		// 		}
				
		// 	}
		// 	reader.close();
		// }catch(FileNotFoundException e){
		// 	e.printStackTrace();
		// }catch(IOException e){
		// 	e.printStackTrace();
		// }
    }

    public void print(){
        for (int i=0; i<sizeY/cellSize ; i++) {
            for (int j=0; j<sizeX/cellSize; j++){
                System.out.print(tab[i][j]);
            }
            System.out.println();
        }
    }

    public void display(){
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
}