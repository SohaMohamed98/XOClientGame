package xoclientgame;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import xoclientgame.HardAIAlogorithm.Move;


public class SinglePlayerViewController implements Initializable {

    @FXML
    private Label lbl_player;
    @FXML
    private Label lbl_AI;
    @FXML
    private Button btn_back;

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
    private Button restartButton;
    private String[][] ticTacToeTable;
    private ArrayList<Button> buttonsList = new ArrayList<>();

    String levels;
    boolean recflag = true;
    static int scoreP1;
    static int scoreP2;

    RadioButton X1 = new RadioButton();

    RadioButton O1 = new RadioButton();
    public static boolean myTurn;
    public static String gameMode;
    public static String key;
    public static String winner;
    private static String[] buttonText;

    private boolean recordFlag;
    private Record record;
    private String position;
    @FXML
    private Label lbl_symbol1;
    @FXML
    private Label lbl_symbol2;
    @FXML
    private Label score1;
    @FXML
    private Label score2;
    @FXML
    private Circle recordSign;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        
        loadGame();
    }

    @FXML
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {
            position = "1";
            move(b1);
        }
        if (e.getSource() == b2) {
            position = "2";
            move(b2);
        }
        if (e.getSource() == b3) {
            position = "3";
            move(b3);
        }
        if (e.getSource() == b4) {
            position = "4";
            move(b4);
        }
        if (e.getSource() == b5) {
            position = "5";
            move(b5);
        }
        if (e.getSource() == b6) {
            position = "6";
            move(b6);
        }
        if (e.getSource() == b7) {
            position = "7";
            move(b7);
        }
        if (e.getSource() == b8) {
            position = "8";
            move(b8);
        }
        if (e.getSource() == b9) {
            position = "9";
            move(b9);
        }
        System.out.println(position);

    }

    @FXML
    void back(ActionEvent event) {
        try {
            scoreP1 = 0;
            scoreP2 = 0;
            ServerConnection.running = false;
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = (Scene) ((Node) event.getSource()).getScene();
            Parent root = FXMLLoader.load(getClass().getResource("StartView.fxml"));
            scene.setRoot(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(LocalMultiplayerViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void resetButton(ActionEvent event) {
        ((Stage) restartButton.getScene().getWindow()).close(); // Close the recent window so that there remains only one window
        // Running a new instance of the start method
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SinglePlayerView.fxml"));
            Parent root = loader.load();

            SinglePlayerViewController o = loader.getController();

            o.transferMesssageText(lbl_player.getText());
            o.transferMessageButtons(X1, O1);

            ((Stage) restartButton.getScene().getWindow()).close();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = (Scene) ((Node) event.getSource()).getScene();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
            loadGame();

        } catch (IOException ex) {
            ex.getMessage();
        } catch (Exception ex) {
            Logger.getLogger(LocalMultiplayerViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void showAlert(String mess) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, mess, ButtonType.CANCEL);
        alert.setTitle("Winner");
        alert.setHeaderText(null);
        alert.setContentText(mess);
        alert.show();
    }

    void move(Button button) {
        recflag = true;
        if (button.getText().equals("")) {
            button.setText("");
            System.out.println(position);
            if (X1.isSelected()) {
                button.setText("X");

                if (recordFlag) {
                    record.setMove(position, "X");
                }
            } else if (O1.isSelected()) {
                button.setText("O");
                if (recordFlag) {
                    record.setMove(position, "O");
                }
            }
            if (updateGame()) {
                playController(buttonsList, ticTacToeTable);
            }

        }
    }

    private void loadGame() {
        ticTacToeTable = new String[3][3];
        loadButtonsList();
        loadButtons();
        loadTicTacToeTable();
        myTurn = true;
        disableButtons(false);
        recordSign.setVisible(false);
        score1.setText(""+scoreP1);
        score2.setText(""+scoreP2);
    }

    private void loadButtons() {
        for (int i = 0; i < buttonsList.size(); i++) {
            buttonsList.get(i).setText("");
            buttonsList.get(i).setStyle("-fx-background: #b6e7c9;");
        }
        restartButton.setVisible(false);
    }

    public void showVictory(String key) {
        reddeningButtons();
        restartButton.setVisible(true);
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

    void showEndGameAlert(String key) {
        Alert endGame = new Alert(AlertType.INFORMATION);
        if (!key.equals("draw")) {
            if (X1.isSelected() && winner.equals("X")) {
                Game game = new Game(lbl_player.getText(), "AI", lbl_player.getText());
                GamesHistoryProcess history = new GamesHistoryProcess();
                history.save(game);
                endGame.setTitle("Winner");
                endGame.setContentText("\"" + lbl_player.getText() + "\" is winner.");
                scoreP1++;
                score1.setText(""+scoreP1);
            } else if (X1.isSelected() && winner.equals("O")) {
                Game game = new Game(lbl_player.getText(), "AI", "AI");
                GamesHistoryProcess history = new GamesHistoryProcess();
                history.save(game);
                endGame.setTitle("Winner");
                endGame.setContentText("\"" + " AI\" is winner.");
                scoreP2++;
                score2.setText(""+scoreP2);
            } else if (O1.isSelected() && winner.equals("O")) {
                Game game = new Game(lbl_player.getText(), "AI", lbl_player.getText());
                GamesHistoryProcess history = new GamesHistoryProcess();
                history.save(game);
                endGame.setTitle("Winner");
                endGame.setContentText("\"" + lbl_player.getText() + "\" is winner.");
                scoreP1++;
                score1.setText(""+scoreP1);
            } else {
                Game game = new Game(lbl_player.getText(), "AI", "AI");
                GamesHistoryProcess history = new GamesHistoryProcess();
                history.save(game);
                endGame.setTitle("Winner");
                endGame.setContentText("\"" + " AI\" is winner.");
                scoreP2++;
                score2.setText(""+scoreP2);
            }
        } else {
            Game game = new Game(lbl_player.getText(), "AI", "draw");
            GamesHistoryProcess history = new GamesHistoryProcess();
            history.save(game);

            endGame.setContentText("The game was a draw.");
            endGame.setTitle("Draw");
        }

        if (recordFlag) {
            RecordedGamesProcess.save(record);
            ServerConnection.running = false;
        }

        if (!key.equals("draw")) {
            if (winner.equals("X")) {
                if (lbl_symbol1.getText().equals("X")) {
                    new ShowVideo().video(lbl_player.getText(), true);
                } else {
                    new ShowVideo().video(lbl_player.getText(), false);
                }
            } else {
                if (lbl_symbol1.getText().equals("O")) {
                    new ShowVideo().video(lbl_player.getText(), true);
                } else {
                    new ShowVideo().video(lbl_player.getText(), false);
                }
            }
        } else {
            endGame.setHeaderText(null);
            endGame.show();
        }

    }

    void reddeningButtons() {
        for (int i = 0; i < buttonsList.size(); i++) {
            buttonsList.get(i).setStyle("-fx-background-color: #FF0000;");
        }
    }

    private void disableButtons(boolean disable) {
        for (int i = 0; i < buttonsList.size(); i++) {
            buttonsList.get(i).setDisable(disable);
        }
    }

    void loadButtonsList() {
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

    boolean updateGame() {
        loadTicTacToeTable();
        if (winningChecker(ticTacToeTable)) {
            showVictory(key);
            return false;
        } else {
            return true;
        }

    }

    void loadTicTacToeTable() {
        int temp = 0;
        for (int i = 0; i < ticTacToeTable.length; i++) {
            for (int j = 0; j < ticTacToeTable[0].length; j++) {
                ticTacToeTable[i][j] = buttonsList.get(temp++).getText();
            }
        }

    }

    public void playController(ArrayList<Button> buttonsList, String[][] ticTacToeTable) {
        if (levels.equals("1")) {
            easyGameLogic(buttonsList);
        } else if (levels.equals("2")) {
            mediumGameLogic(buttonsList);
        } else {
            hardGameLogic(buttonsList);

        }

    }

    public boolean winningChecker(String[][] ticTacToeTable) {
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

    public boolean checkVictory(String[] vector) {
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

    public boolean checkDraw(String[][] ticTacToeTable) {
        for (int i = 0; i < ticTacToeTable.length; i++) {
            for (int j = 0; j < ticTacToeTable[0].length; j++) {
                if (ticTacToeTable[i][j].equals("")) {
                    return false;
                }
            }
        }
        return true;
    }

    void hardGameLogic(ArrayList<Button> buttonsList) {
        String symbol = null;
        char board[][] = new char[3][3];
        int temp = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttonsList.get(temp).getText().equals("")) {
                    board[i][j] = '_';
                } else {
                    board[i][j] = Character.toLowerCase(buttonsList.get(temp).getText().charAt(0));
                }
                temp++;
                System.out.print(board[i][j]);
            }
            System.out.println("");
        }

        HardAIAlogorithm hard = new HardAIAlogorithm();
        hard.player = Character.toLowerCase(lbl_symbol2.getText().charAt(0));
        hard.opponent = Character.toLowerCase(lbl_symbol1.getText().charAt(0));
        Move bestMove = hard.findBestMove(board);

        switch (bestMove.row) {
            case 0:
                switch (bestMove.col) {
                    case 0:
                        temp = 0;
                        break;
                    case 1:
                        temp = 1;
                        break;
                    case 2:
                        temp = 2;
                        break;
                }
                break;
            case 1:
                switch (bestMove.col) {
                    case 0:
                        temp = 3;
                        break;
                    case 1:
                        temp = 4;
                        break;
                    case 2:
                        temp = 5;
                        break;
                }
                break;
            case 2:
                switch (bestMove.col) {
                    case 0:
                        temp = 6;
                        break;
                    case 1:
                        temp = 7;
                        break;

                    case 2:
                        temp = 8;
                        break;
                }
                break;
        }

        System.out.println(temp);
        System.out.println(bestMove.row + ", " + bestMove.col);
        if (buttonsList.get(temp).getText().equals("")) {
            if (X1.isSelected()) {

                symbol = "O";

                if (recordFlag) {
                    record.setMove("" + (temp + 1), "O");
                }

            } else if (O1.isSelected()) {
                symbol = "X";

                if (recordFlag) {
                    record.setMove("" + (temp + 1), "X");
                }

            }
            switch (temp) {
                case 0:
                    buttonsList.get(temp).setText(symbol);
                    updateGame();
                    recflag = false;
                    break;
                case 1:
                    buttonsList.get(temp).setText(symbol);
                    updateGame();
                    recflag = false;
                    break;
                case 2:
                    buttonsList.get(temp).setText(symbol);
                    updateGame();
                    recflag = false;
                    break;
                case 3:
                    buttonsList.get(temp).setText(symbol);
                    updateGame();
                    recflag = false;
                    break;
                case 4:
                    buttonsList.get(temp).setText(symbol);
                    updateGame();
                    recflag = false;
                    break;
                case 5:
                    buttonsList.get(temp).setText(symbol);
                    updateGame();
                    recflag = false;
                    break;
                case 6:
                    buttonsList.get(temp).setText(symbol);
                    updateGame();
                    recflag = false;
                    break;
                case 7:
                    buttonsList.get(temp).setText(symbol);
                    updateGame();
                    recflag = false;
                    break;
                case 8:
                    buttonsList.get(temp).setText(symbol);
                    updateGame();
                    recflag = false;
                    break;
            }
        }
    }

    void mediumGameLogic(ArrayList<Button> buttonsList) {
        String symbol = null;

        int temp = mediumAIAlogorithm.getBestPosition(buttonsList, lbl_symbol1.getText(), lbl_symbol2.getText());
        System.out.println(temp);
        System.out.println(lbl_symbol1.getText());
        if (buttonsList.get(temp).getText().equals("")) {
            if (X1.isSelected()) {

                symbol = "O";

                if (recordFlag) {
                    record.setMove("" + (temp + 1), "O");
                }

            } else if (O1.isSelected()) {
                symbol = "X";

                if (recordFlag) {
                    record.setMove("" + (temp + 1), "X");
                }

            }
            switch (temp) {
                case 1:
                    buttonsList.get(temp).setText(symbol);
                    updateGame();
                    recflag = false;
                    break;
                case 2:
                    buttonsList.get(temp).setText(symbol);
                    updateGame();
                    recflag = false;
                    break;
                case 3:
                    buttonsList.get(temp).setText(symbol);
                    updateGame();
                    recflag = false;
                    break;
                case 4:
                    buttonsList.get(temp).setText(symbol);
                    updateGame();
                    recflag = false;
                    break;
                case 5:
                    buttonsList.get(temp).setText(symbol);
                    updateGame();
                    recflag = false;
                    break;
                case 6:
                    buttonsList.get(temp).setText(symbol);
                    updateGame();
                    recflag = false;
                    break;
                case 7:
                    buttonsList.get(temp).setText(symbol);
                    updateGame();
                    recflag = false;
                    break;
                case 8:
                    buttonsList.get(temp).setText(symbol);
                    updateGame();
                    recflag = false;
                    break;
                case 9:
                    buttonsList.get(temp).setText(symbol);
                    updateGame();
                    recflag = false;
                    break;
            }
        }

    }

    void easyGameLogic(ArrayList<Button> buttonsList) {
        Random randomMove = new Random();
        int temp;
        String symbol = null;
        temp = (int) (Math.random() * 10) % 9;

        while (true) {
            temp = (int) (Math.random() * 10) % 9;
            System.out.println(temp);
            if (buttonsList.get(temp).getText().equals("")) {
                if (X1.isSelected()) {

                    symbol = "O";

                    if (recordFlag) {
                        record.setMove("" + (temp + 1), "O");
                    }

                } else if (O1.isSelected()) {
                    symbol = "X";

                    if (recordFlag) {
                        record.setMove("" + (temp + 1), "X");
                    }

                }
                switch (temp) {
                    case 1:
                        buttonsList.get(temp).setText(symbol);
                        updateGame();
                        recflag = false;
                        break;
                    case 2:
                        buttonsList.get(temp).setText(symbol);
                        updateGame();
                        recflag = false;
                        break;
                    case 3:
                        buttonsList.get(temp).setText(symbol);
                        updateGame();
                        recflag = false;
                        break;
                    case 4:
                        buttonsList.get(temp).setText(symbol);
                        updateGame();
                        recflag = false;
                        break;
                    case 5:
                        buttonsList.get(temp).setText(symbol);
                        updateGame();
                        recflag = false;
                        break;
                    case 6:
                        buttonsList.get(temp).setText(symbol);
                        updateGame();
                        recflag = false;
                        break;
                    case 7:
                        buttonsList.get(temp).setText(symbol);
                        updateGame();
                        recflag = false;
                        break;
                    case 8:
                        buttonsList.get(temp).setText(symbol);
                        updateGame();
                        recflag = false;
                        break;
                    case 9:
                        buttonsList.get(temp).setText(symbol);
                        updateGame();
                        recflag = false;
                        break;
                }
            }

            if (!recflag) {
                break;
            }
        }
    }

    boolean checkFreeButton(ArrayList<Button> buttonsList) {
        for (int i = 0; i < buttonsList.size(); i++) {
            if (buttonsList.get(i).getText().equals("")) {
                return true;
            }
        }
        return false;
    }

    void transferMesssageText(String text) {
        lbl_player.setText(text);
    }

    void transferMessageButtons(RadioButton X, RadioButton O) {
        X1 = X;
        O1 = O;

        if (X1.isSelected()) {
            lbl_symbol1.setText(X1.getText());
            lbl_symbol2.setText("O");
        } else if (O1.isSelected()) {
            lbl_symbol1.setText("O");
            lbl_symbol2.setText("X");
        }

    }

    void transferMessageRecordFlag(boolean flag, String l) {
        levels = l;

        recordFlag = flag;
        if (recordFlag) {
            record = new Record(lbl_player.getText(), "AI");
            ServerConnection.running = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (ServerConnection.running) {

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                recordSign.setVisible(!recordSign.isVisible());
                                System.out.println("thread");

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
}
