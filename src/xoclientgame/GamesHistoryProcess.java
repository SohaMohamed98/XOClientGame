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
public class GamesHistoryProcess {
    public static ArrayList<Game> games = new ArrayList<>();
    
    public static boolean save(Game game){
        String saveLine = game.getPlayer1() + " " + game.getPlayer2() + " " + game.getWinner();
 
        File file = new File(".", "History");
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
            } else {
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
    
    public static void read(){
        String line;

        File file = new File(".", "History");
        FileInputStream fis;
        try {
            if (file.exists()) {
                fis = new FileInputStream(file);
                DataInputStream dis = new DataInputStream(fis);
                
                if (dis.available() != 0) {
                    line = dis.readUTF();

                    String[] arr = line.split(",");
                    for (int i = 0; i < arr.length; i++) {
                        String[] arr2 = arr[i].split(" ");
                        Game game = new Game(arr2[0], arr2[1], arr2[2]);
                        games.add(game);
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
    
    static void clear(){
        games.clear();
        
    }
}

class Game {
    private String player1;
    private String player2;
    private String winner;

    public Game(String player1, String player2, String winner) {
        this.player1 = player1;
        this.player2 = player2;
        this.winner = winner;
    }
    
    public String getPlayer1(){
        return player1;
    }
    public String getPlayer2(){
        return player2;
    }
    public String getWinner(){
        return winner;
    }
}
