/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xoclientgame;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Abanob wadie
 */
public class RecordedGamesProcess {

    public static ArrayList<Record> records = new ArrayList<>();
    
    
    public static boolean save(Record record){
        String saveLine = record.getPlayer1() + " " + record.getPlayer2();
        for (String move : record.getMoves()) {
            saveLine += " " + move;
        }
 
        File file = new File(".", "Save");
        FileInputStream fis;
        FileOutputStream fos;
        try {
            if (file.exists()) {
                fis = new FileInputStream(file);
                DataInputStream dis = new DataInputStream(fis);
                
                if(dis.available() != 0){
                    String line = dis.readUTF();
                    line += "," + saveLine;
                    saveLine = line;
                }
                
                fis.close();
                dis.close();
            }else{
                try {
                    file.createNewFile();
                } catch (IOException ex) {
                    Logger.getLogger(RecordedGamesProcess.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            
            fos = new FileOutputStream(file);
            DataOutputStream dos = new DataOutputStream(fos);
            dos.writeUTF(saveLine);
            fos.close();
            dos.close();
            
            return true;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RecordedGamesProcess.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RecordedGamesProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }

    public static void read() {
        String line;

        File file = new File(".", "Save");
        FileInputStream fis;
        try {
            if (file.exists()) {
                fis = new FileInputStream(file);
                DataInputStream dis = new DataInputStream(fis);
                
                if(dis.available() != 0){
                    line = dis.readUTF();

                    String[] arr = line.split(",");
                    for (int i = 0; i < arr.length; i++) {
                        String[] arr2 = arr[i].split(" ");
                        Record record = new Record(arr2[0], arr2[1]);

                        for (int j = 2; j < arr2.length; j++) {
                            String[] arr3 = arr2[j].split("\\|");
                            record.setMove(arr3[0], arr3[1]);
                        }
                        records.add(record);
                    }
                }
 
                fis.close();
                dis.close();
            } else {
                try {
                    file.createNewFile();
                } catch (IOException ex) {
                    Logger.getLogger(RecordedGamesProcess.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(RecordedGamesProcess.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RecordedGamesProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    static void clear() {
        records.clear();
    }
}

class Record {
    private String player1;
    private String player2;
    private ArrayList<String> moves;

    public Record(String player1, String player2) {
        this.player1 = player1;
        this.player2 = player2;
        moves = new ArrayList<>();
    }

    
    public void setMove(String position, String symbol){
        moves.add(position + "|" + symbol);
    }
    
    public String getPlayer1(){
        return player1;
    }
    public String getPlayer2(){
        return player2;
    }
    public ArrayList<String> getMoves(){
        return moves;
    }
}
