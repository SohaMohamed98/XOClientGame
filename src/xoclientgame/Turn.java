/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xoclientgame;

/**
 *
 * @author SoHa
 */
public class Turn {
    
     private static Player turn;

    public static Player getTurn()
    {
        return turn;
    }

    public static void setTurn(Player turn)
    {
        Turn.turn = turn;
    }
    

    
    
}
