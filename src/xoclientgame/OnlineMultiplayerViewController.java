package xoclientgame;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class OnlineMultiplayerViewController implements Initializable {

    private String[][] ticTacToeTable = new String[3][3];
    private ArrayList<Button> buttonsList = new ArrayList<>();

    Player p1 = new Player();
    Player p2 = new Player();

    public static boolean myTurn;
    public static String gameMode = "twoPlayers";
    public static String key;
    public static String winner;
    private static String[] buttonText;

    private String position;
    private String moveSymbol;
    private boolean recordFlag;
    private Record record;
    private boolean mine;
    private boolean end = false;

    @FXML
    private Button b1;
    @FXML
    private Button b2;
    @FXML
    private Button b3;
    @FXML
    private Button b4;
    @FXML
    private Button b5;
    @FXML
    private Button b6;
    @FXML
    private Button b7;
    @FXML
    private Button b8;
    @FXML
    private Button b9;

    @FXML
    private Label lbl_player2;
    @FXML
    private Label lbl_player1;
    @FXML
    private Label lbl_name1;
    @FXML
    private Label lbl_name2;
    @FXML
    private Button btn_back;
    @FXML
    private Circle recordSign;
    @FXML
    private Label lbl_p1_score;
    @FXML
    private Label lbl_p2_score;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        loadGame();

        ServerConnection.running = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (ServerConnection.running) {
                    String result = ServerConnection.recivePlayInPostion();
                    if (result == null) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {

                                    Parent root = FXMLLoader.load(getClass().getResource("StartView.fxml"));
                                    Scene scene = new Scene(root);
                                    
                                    StageView.st.setScene(scene);
                                    StageView.st.setResizable(false);
                                    StageView.st.show();
                                } catch (IOException ex) {
                                    Logger.getLogger(SignUpViewController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                
                                Dialog<ButtonType> dialog = new Dialog<>();
                                dialog.setTitle("Sorry");
                                ButtonType okBtn = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
                                dialog.setContentText("Sorry, the server is closed we have to logout you!");
                                dialog.getDialogPane().getButtonTypes().addAll(okBtn);

                                dialog.showAndWait();
                            }
                        });
                        ServerConnection.running = false;
                    } else {
                        String arr[] = result.split("|");

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                switch (arr[0]) {
                                    case "1":
                                        position = "1";
                                        move(b1);
                                        ChangeTurn();
                                        disableButtons(false);
                                        break;

                                    case "2":
                                        position = "2";
                                        move(b2);
                                        ChangeTurn();
                                        disableButtons(false);
                                        break;

                                    case "3":
                                        position = "3";
                                        move(b3);
                                        ChangeTurn();
                                        disableButtons(false);
                                        break;

                                    case "4":
                                        position = "4";
                                        move(b4);
                                        ChangeTurn();
                                        disableButtons(false);
                                        break;

                                    case "5":
                                        position = "5";
                                        move(b5);
                                        ChangeTurn();
                                        disableButtons(false);
                                        break;

                                    case "6":
                                        position = "6";
                                        move(b6);
                                        ChangeTurn();
                                        disableButtons(false);
                                        break;

                                    case "7":
                                        position = "7";
                                        move(b7);
                                        ChangeTurn();
                                        disableButtons(false);
                                        break;

                                    case "8":
                                        position = "8";
                                        move(b8);
                                        ChangeTurn();
                                        disableButtons(false);
                                        break;

                                    case "9":
                                        position = "9";
                                        move(b9);
                                        ChangeTurn();
                                        disableButtons(false);
                                        break;
                                }
                            }
                        });
                    }

                }
            }
        }).start();
    }

    @FXML
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {
            position = "1";
            move(b1);
            ChangeTurn();
        }
        if (e.getSource() == b2) {
            position = "2";
            move(b2);
            ChangeTurn();
        }
        if (e.getSource() == b3) {
            position = "3";
            move(b3);
            ChangeTurn();
        }
        if (e.getSource() == b4) {
            position = "4";
            move(b4);
            ChangeTurn();
        }
        if (e.getSource() == b5) {
            position = "5";
            move(b5);
            ChangeTurn();
        }
        if (e.getSource() == b6) {
            position = "6";
            move(b6);
            ChangeTurn();
        }
        if (e.getSource() == b7) {
            position = "7";
            move(b7);
            ChangeTurn();
        }
        if (e.getSource() == b8) {
            position = "8";
            move(b8);
            ChangeTurn();
        }
        if (e.getSource() == b9) {
            position = "9";
            move(b9);
            ChangeTurn();
        }

    }

    private void move(Button button) {
        if (button.getText().equals("")) {
            if (gameMode.equals("twoPlayers")) {
                String s = playController(buttonsList, ticTacToeTable);
                if(button.getText().equals("")){
                    button.setText(s);
                    disableButtons(true);
                    if (recordFlag) {
                        record.setMove(position, moveSymbol);
                    }
                    updateGame();
                }
            }
        }
    }

    private void loadGame() {
        ticTacToeTable = new String[3][3];
        loadButtonsList();
        loadButtons();
        loadTicTacToeTable();
        myTurn = true;
        System.out.println("load");
        recordSign.setVisible(false);
    }

    private void loadButtons() {
        for (int i = 0; i < buttonsList.size(); i++) {
            buttonsList.get(i).setText("");
            buttonsList.get(i).setStyle("-fx-font: 40 arial; -fx-base: #b6e7c9;");
        }

    }

    public void showVictory(String key) {
        reddeningButtons();
        switch (key) {
            case "line 0": {
                b1.setStyle("-fx-background-color: #00FF00;");
                b2.setStyle("-fx-background-color: #00FF00;");
                b3.setStyle("-fx-background-color: #00FF00;");
                break;
            }
            case "line 1": {
                b4.setStyle("-fx-background-color: #00FF00;");
                b5.setStyle("-fx-background-color: #00FF00;");
                b6.setStyle("-fx-background-color: #00FF00;");
                break;
            }
            case "line 2": {
                b7.setStyle("-fx-background-color: #00FF00;");
                b8.setStyle("-fx-background-color: #00FF00;");
                b9.setStyle("-fx-background-color: #00FF00;");
                break;
            }
            case "column 0": {
                b1.setStyle("-fx-background-color: #00FF00;");
                b4.setStyle("-fx-background-color: #00FF00;");
                b7.setStyle("-fx-background-color: #00FF00;");
                break;
            }
            case "column 1": {
                b2.setStyle("-fx-background-color: #00FF00;");
                b5.setStyle("-fx-background-color: #00FF00;");
                b8.setStyle("-fx-background-color: #00FF00;");
                break;
            }
            case "column 2": {
                b3.setStyle("-fx-background-color: #00FF00;");
                b6.setStyle("-fx-background-color: #00FF00;");
                b9.setStyle("-fx-background-color: #00FF00;");
                break;
            }
            case "main diagonal": {
                b1.setStyle("-fx-background-color: #00FF00;");
                b5.setStyle("-fx-background-color: #00FF00;");
                b9.setStyle("-fx-background-color: #00FF00;");
                break;
            }
            case "secundary diagonal": {
                b3.setStyle("-fx-background-color: #00FF00;");
                b5.setStyle("-fx-background-color: #00FF00;");
                b7.setStyle("-fx-background-color: #00FF00;");
                break;
            }
            case "draw": {
                reddeningButtons();
                break;
            }
        }
        disableButtons(true);
        showEndGameAlert(key);

    }

    private void showEndGameAlert(String key) {
        Alert endGame = new Alert(AlertType.INFORMATION);
        if (!key.equals("draw")) {
            if (winner.equals("X")) {
                if (lbl_name1.getText().equals("X")) {
                    new ShowVideo().video(lbl_player1.getText(), true);
                } else {
                    new ShowVideo().video(lbl_player1.getText(), false);
                }

            } else {
                if (lbl_name1.getText().equals("O")) {
                    new ShowVideo().video(lbl_player1.getText(), true);
                } else {
                    new ShowVideo().video(lbl_player1.getText(), false);
                }
            }
        } else {
            endGame.setContentText("The game was a draw.");
            endGame.setTitle("Draw");
            endGame.setHeaderText(null);
            endGame.show();
        }
        disableButtons(true);
        if (recordFlag) {
            RecordedGamesProcess.save(record);
            ServerConnection.running=false;
            end = true;
        }
    }

    private void reddeningButtons() {
        for (int i = 0; i < buttonsList.size(); i++) {
            buttonsList.get(i).setStyle("-fx-background-color: #FF0000;");
        }
    }

    private void disableButtons(boolean disable) {
        for (int i = 0; i < buttonsList.size(); i++) {
            buttonsList.get(i).setDisable(disable);
        }
    }

    private void loadButtonsList() {
        buttonsList.add(b1);
        buttonsList.add(b2);
        buttonsList.add(b3);
        buttonsList.add(b4);
        buttonsList.add(b5);
        buttonsList.add(b6);
        buttonsList.add(b7);
        buttonsList.add(b8);
        buttonsList.add(b9);
    }

    private boolean updateGame() {
        loadTicTacToeTable();
        if (winningChecker(ticTacToeTable)) {
            if (key.equals("draw")) {
                if (mine) {
                    mine = false;
                    System.out.println("finalDraw");
                    ServerConnection.sendPlayInPostion(false, true, moveSymbol, position);
                } else {
                    mine = true;
                }
            } else {
                if (mine) {
                    mine = false;
                    System.out.println("finalWin");
                    ServerConnection.sendPlayInPostion(true, false, moveSymbol, position);
                } else {
                    mine = true;
                }
            }

            showVictory(key);
            System.out.println(key);
            return false;
        } else {
            if (mine) {
                mine = false;
                ServerConnection.sendPlayInPostion(false, false, moveSymbol, position);
                System.out.println(key);
                System.out.println("play");
            } else {
                mine = true;
            }
            return true;
        }

    }

    private void loadTicTacToeTable() {
        int temp = 0;
        for (int i = 0; i < ticTacToeTable.length; i++) {
            for (int j = 0; j < ticTacToeTable[0].length; j++) {
                ticTacToeTable[i][j] = buttonsList.get(temp++).getText();
            }
        }

    }

    public String playController(ArrayList<Button> buttonsList, String[][] ticTacToeTable) {
        switch (gameMode) {

            case "twoPlayers": {
                /*if(myTurn) {
                            
				myTurn = !myTurn;
                               return p1.getSymbol();
				
			}else {
				myTurn = !myTurn;
				return p2.getSymbol();
			}*/
                if (myTurn) {
                    if (Turn.getTurn() == p1) {
                        if (p1.getSymbol().equals("X")) {
                            moveSymbol = "X";
                            p2.setSymbol("O");
                            return "X";
                        } else {
                            moveSymbol = "O";
                            p1.setSymbol("O");
                            return "O";
                        }
                    } else {
                        if (p2.getSymbol().equals("X")) {
                            moveSymbol = "X";
                            p1.setSymbol("O");
                            return "X";
                        } else {
                            moveSymbol = "O";
                            p2.setSymbol("O");
                            return "O";
                        }
                    }
                }

            }
            default: {
                return null;
            }
        }
    }

    private void ChangeTurn() {
        if (Turn.getTurn() == (p1)) {
            Turn.setTurn(p2);
            // Setting underlines visible or invisible acc to player turn

        } else {
            Turn.setTurn(p1);

        }
    }

    public static boolean winningChecker(String[][] ticTacToeTable) {
        buttonText = new String[3];
        //Checking line 0
        for (int i = 0; i < buttonText.length; i++) {
            buttonText[i] = ticTacToeTable[0][i];
        }
        if (checkVictory(buttonText)) {
            key = "line 0";
            return true;
        }

        buttonText = new String[3];
        //Checking line 1
        for (int i = 0; i < buttonText.length; i++) {
            buttonText[i] = ticTacToeTable[1][i];
        }
        if (checkVictory(buttonText)) {
            key = "line 1";
            return true;
        }

        buttonText = new String[3];
        //Checking line 2
        for (int i = 0; i < buttonText.length; i++) {
            buttonText[i] = ticTacToeTable[2][i];

        }
        if (checkVictory(buttonText)) {
            key = "line 2";
            return true;
        }

        buttonText = new String[3];
        //Checking column 0
        for (int i = 0; i < buttonText.length; i++) {
            buttonText[i] = ticTacToeTable[i][0];

        }
        if (checkVictory(buttonText)) {
            key = "column 0";
            return true;
        }

        buttonText = new String[3];
        //Checking column 1
        for (int i = 0; i < buttonText.length; i++) {
            buttonText[i] = ticTacToeTable[i][1];

        }
        if (checkVictory(buttonText)) {
            key = "column 1";
            return true;
        }

        buttonText = new String[3];
        //Checking column 2
        for (int i = 0; i < buttonText.length; i++) {
            buttonText[i] = ticTacToeTable[i][2];

        }
        if (checkVictory(buttonText)) {
            key = "column 2";
            return true;
        }

        buttonText = new String[3];
        //main diagonal
        for (int i = 0; i < buttonText.length; i++) {
            buttonText[i] = ticTacToeTable[i][i];

        }
        if (checkVictory(buttonText)) {
            key = "main diagonal";
            return true;
        }

        buttonText = new String[3];
        //secondary diagonal
        for (int i = 0; i < buttonText.length; i++) {
            int j = buttonText.length - 1 - i;
            buttonText[i] = ticTacToeTable[i][j];

        }
        if (checkVictory(buttonText)) {
            key = "secundary diagonal";
            return true;
        }

        //draw
        if (checkDraw(ticTacToeTable)) {
            key = "draw";
            return true;
        }
        return false;
    }

    public static boolean checkVictory(String[] vector) {
        if (vector[0].equals("X") && vector[1].equals("X") && vector[2].equals("X")) {
            winner = "X";
            return true;

        } else if (vector[0].equals("O") && vector[1].equals("O") && vector[2].equals("O")) {
            winner = "O";
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkDraw(String[][] ticTacToeTable) {
        for (int i = 0; i < ticTacToeTable.length; i++) {
            for (int j = 0; j < ticTacToeTable[0].length; j++) {
                if (ticTacToeTable[i][j].equals("")) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean checkFreeButton(ArrayList<Button> buttonsList) {
        for (int i = 0; i < buttonsList.size(); i++) {
            if (buttonsList.get(i).getText().equals("")) {
                return true;
            }
        }
        return false;
    }

    @FXML
    void back(ActionEvent event) {
        try {
            ServerConnection.back();
            ServerConnection.running = false;
            
            if(end && recordFlag){
                String value = record.getPlayer1() + " " + record.getPlayer2();
                for (String move : record.getMoves()) {
                    value += " " + move;
                }
                ServerConnection.saveRecord(value);
            }
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ListPlayerView.fxml"));
            Parent root = loader.load();

            //Get controller of scene2
            ListPlayerViewController o = loader.getController();
            //Pass whatever data you want. You can have multiple method calls here
            o.transferMessageName1(lbl_player1.getText());

            
            StageView.st.setScene(new Scene(root));
            StageView.st.show();

        } catch (IOException ex) {
            Logger.getLogger(LocalMultiplayerViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void transferMessageRecordFlag(boolean flag) {
        
        recordFlag = flag;
    }

    void transferMessageNames(String name1, String p1Score, String get, String p2Score) {
        lbl_player1.setText(name1);
        lbl_player2.setText(get);
        lbl_p1_score.setText(p1Score);
        lbl_p2_score.setText(p2Score);
        System.out.println("names");
        if (recordFlag) {
            record = new Record(name1, get);
                    ServerConnection.running = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (ServerConnection.running) {
                   
                       
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                recordSign.setVisible(!recordSign.isVisible());
                                
                            }
                               
                        });
                    try {
                        Thread.sleep(1000l);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(LocalMultiplayerViewController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                        

                }
            }
        }).start();
        }
    }

    void transferMessageSymbol(String s) {
        if (s.equals("X")) {
            disableButtons(false);
            Turn.setTurn(p1);
            mine = true;
            p1.setSymbol("X");
            p2.setSymbol("O");

            lbl_name1.setText("X");
            lbl_name2.setText("O");
        } else {
            disableButtons(true);
            Turn.setTurn(p2);
            mine = false;
            p1.setSymbol("O");
            p2.setSymbol("X");

            lbl_name1.setText("O");
            lbl_name2.setText("X");
        }
    }
}
