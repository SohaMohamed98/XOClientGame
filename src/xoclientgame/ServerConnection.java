/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xoclientgame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Abanob wadie
 */
public class ServerConnection {

    static private BufferedReader in;
    static private PrintWriter out;
    static volatile boolean running = false;

    public static boolean init(String ip) {
        try {
            Socket mySocket = new Socket(ip, 5005);
            if(mySocket.isConnected()){
                in = new BufferedReader(new InputStreamReader(
                        mySocket.getInputStream()));
                out = new PrintWriter(
                        mySocket.getOutputStream());
                return true;
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    


    public static String SignIn(String userName, String password) {

        out.println("singin " + userName + " " + password);
        out.flush();
        try {
            String requestState = in.readLine();
            return requestState;
        } catch (IOException ex) {
            Logger.getLogger(ServerConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static boolean SignUp(String userName, String password) {
        out.println("singup " + userName + " " + password);
        out.flush();
        try {
            String requestState = in.readLine();
            if (requestState.equals("true")) {
                return true;
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public static boolean forgetPassword(String userName, String password) {
        out.println("forget " + userName + " " + password);
        out.flush();
        try {
            String requestState = in.readLine();
            if (requestState.equals("true")) {
                return true;
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static void exit() {
        out.println("exit");
        out.flush();
        running = false;
       
    }
    
    public static void back(){
        out.println("back");
        out.flush();
        running = false;

    }

    public static void playWith(String name) {
        out.println("play " + name);
        out.flush();        
    }
    
    public static void history() {
        out.println("history");
        out.flush();        
    }
    
    public static void onlineRecords() {
        out.println("records");
        out.flush();        
    }
    
    public static void saveRecord(String record) {
        out.println("save" + record);
        out.flush();        
    }

    public static ArrayList<String> getOnlineUsers() {
        ArrayList<String> arr = new ArrayList<>();
        try {
            //out.println("ready");
            //out.flush();
                String str = in.readLine();

                if(str == null){
                     return arr;
                }else if(str.contains("(online-list)")){
                    String[] strArr = str.split(" ");
                    
                    for (int i = 1; i < strArr.length; i++) {
                        String newStr = strArr[i].replace(",", " ");
                        arr.add(newStr);
                    }
                    
                }else {
                    arr.add(str);
                }
            
        } catch (IOException ex) {
            Logger.getLogger(ServerConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arr;
    }

    public static void ok() {
        out.println("ok");
        out.flush();
        out.println("ok");
        out.flush();
    }
    
    public static void no() {
        out.println("no");
        out.flush();
        out.println("no");
        out.flush();
    }

    public static void sendPlayInPostion(boolean win, boolean draw, String c, String position) {
        if (win) {
            out.println(position + "|" + c);
            out.println("win");
        } else {
            out.println(position + "|" + c);
            
            if(draw){
                out.println("draw");
            }
        }
        out.flush();
    }

    public static String recivePlayInPostion() {

        try {
            String str =  in.readLine();
            if(str == null){
                in.close();
                out.close();
            }else{
                return str;
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
