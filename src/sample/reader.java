package sample;

import java.util.*;
import java.io.*;

public class reader{
    private static ArrayList <String> save = new ArrayList<String>();

    public reader(int x){
        save.clear();
        File file;
        if(x==10){
            file = new File("C:\\Users\\meike\\IdeaProjects\\Phase3\\src\\sample\\Box.txt");
        }
        else{
            file = new File("C:\\Users\\meike\\IdeaProjects\\Phase3\\src\\sample\\pentomino.txt");
        }
        try {
            FileReader txt1 = new FileReader(file);
            BufferedReader out = new BufferedReader(txt1);
            String st ="";
            while ((st=out.readLine())!=null){
                save.add(st);
            }
        } catch (FileNotFoundException e) {
            System.out.print(e);
            System.exit(0);
        } catch(IOException e){
            System.out.print(e);
        }        
    }

    public static int [][][] data(int id,int rotation){
        int [] allint =operation(id, rotation);
        int xSize = allint[2];
        int ySize = allint[3];
        int zSize = allint[4];

        int[][][] piece = new int[xSize][ySize][zSize];
        int l=5;
        for(int i=0; i<zSize; i++){
            for(int j=0; j<ySize; j++){
                for (int k=0; k<xSize; k++){
                    piece[k][j][i] = allint[l++];
                }
            }
        }
        return piece;
    }

    public static int [] operation(int id,int rotation){
        int [] allint;
        for (int i=0;i<save.size();i++){
            String a=save.get(i);
            String [] all= a.split(",");
            allint =new int [all.length];
            for (int y=0;y<all.length;y++){
                allint [y]=Integer.parseInt(all[y]);
            }
            if (allint[0]==id){
                if (allint[1]==rotation){
                    return allint;
                }
            }
        }
        System.out.println("big problem");
        return null;
    }
}