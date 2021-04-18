/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xoclientgame;

import java.util.ArrayList;
import java.util.Random;
import javafx.scene.control.Button;
import jdk.nashorn.internal.ir.BreakNode;

/**
 *
 * @author Abanob wadie
 */
public class mediumAIAlogorithm {

    private static int positionToPlay(ArrayList<Button> board, String symbol){
        
        if (board.get(0).getText().equals(symbol) && board.get(1).getText().equals(symbol) && board.get(2).getText().equals("")) {
            return 2;
        } else if (board.get(0).getText().equals(symbol) && board.get(2).getText().equals(symbol) && board.get(1).getText().equals("")) {
            return 1;
        } else if (board.get(1).getText().equals(symbol) && board.get(2).getText().equals(symbol) && board.get(0).getText().equals("")) {
            return 0;
        } 

        else if (board.get(3).getText().equals(symbol) && board.get(4).getText().equals(symbol) && board.get(5).getText().equals("")) {
            return 5;
        } else if (board.get(3).getText().equals(symbol) && board.get(5).getText().equals(symbol) && board.get(4).getText().equals("")) {
            return 4;
        } else if (board.get(4).getText().equals(symbol) && board.get(5).getText().equals(symbol) && board.get(3).getText().equals("")) {
            return 3;
        }

        else if (board.get(6).getText().equals(symbol) && board.get(7).getText().equals(symbol) && board.get(8).getText().equals("")) {
            return 8;
        } else if (board.get(6).getText().equals(symbol) && board.get(8).getText().equals(symbol) && board.get(7).getText().equals("")) {
            return 7;
        } else if (board.get(7).getText().equals(symbol) && board.get(8).getText().equals(symbol) && board.get(6).getText().equals("")) {
            return 6;
        }
        
        else if (board.get(0).getText().equals(symbol) && board.get(3).getText().equals(symbol) && board.get(6).getText().equals("")) {
            return 6;
        } else if (board.get(0).getText().equals(symbol) && board.get(6).getText().equals(symbol) && board.get(3).getText().equals("")) {
            return 3;
        } else if (board.get(3).getText().equals(symbol) && board.get(6).getText().equals(symbol) && board.get(0).getText().equals("")) {
            return 0;
        }

        else if (board.get(1).getText().equals(symbol) && board.get(4).getText().equals(symbol) && board.get(7).getText().equals("")) {
            return 7;
        } else if (board.get(1).getText().equals(symbol) && board.get(7).getText().equals(symbol) && board.get(4).getText().equals("")) {
            return 4;
        } else if (board.get(4).getText().equals(symbol) && board.get(7).getText().equals(symbol) && board.get(1).getText().equals("")) {
            return 1;
        }

        else if (board.get(2).getText().equals(symbol) && board.get(5).getText().equals(symbol) && board.get(8).getText().equals("")) {
            return 8;
        } else if (board.get(2).getText().equals(symbol) && board.get(8).getText().equals(symbol) && board.get(5).getText().equals("")) {
            return 5;
        } else if (board.get(5).getText().equals(symbol) && board.get(8).getText().equals(symbol) && board.get(2).getText().equals("")) {
            return 2;
        }

        else if (board.get(0).getText().equals(symbol) && board.get(4).getText().equals(symbol) && board.get(8).getText().equals("")) {
            return 8;
        } else if (board.get(0).getText().equals(symbol) && board.get(8).getText().equals(symbol) && board.get(4).getText().equals("")) {
            return 4;
        } else if (board.get(4).getText().equals(symbol) && board.get(8).getText().equals(symbol) && board.get(0).getText().equals("")) {
            return 0;
        }

        else if (board.get(2).getText().equals(symbol) && board.get(4).getText().equals(symbol) && board.get(6).getText().equals("")) {
            return 6;
        } else if (board.get(2).getText().equals(symbol) && board.get(6).getText().equals(symbol) && board.get(4).getText().equals("")) {
            return 4;
        } else if (board.get(4).getText().equals(symbol) && board.get(6).getText().equals(symbol) && board.get(2).getText().equals("")) {
            return 2;
        }
        
        return -1;
    }
    
    public static int getBestPosition(ArrayList<Button> board, String opponentSymbol, String MySymbol) {
        
        int WinningMove = positionToPlay(board, MySymbol);
        int avoidLosingMove = positionToPlay(board, opponentSymbol);
        
        if(WinningMove != -1){
            return WinningMove;
        }else if (avoidLosingMove != -1){
            return avoidLosingMove;
        }else {
            Random random = new Random();

            while (true) {
                int move = (int) (Math.random() * 10) % 9;
                if (board.get(move).getText().equals("")) {
                    return move;
                }
            }
        }
    }
}