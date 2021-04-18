/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xoclientgame;

import com.sun.javaws.Main;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import static xoclientgame.Turn.setTurn;

/**
 * FXML Controller class
 *
 * @author SoHa
 */
public class LocalViewController implements Initializable {

    @FXML
    private Button btn_play;
    @FXML
    private CheckBox btn_record;

    final ToggleGroup group1 = new ToggleGroup();
    final ToggleGroup group2 = new ToggleGroup();
    private boolean recordFlag;
    @FXML
    private TextField txt_name1;
    @FXML
    private RadioButton X1;
    @FXML
    private RadioButton O1;
    @FXML
    private TextField txt_name2;
    @FXML
    private RadioButton X2;
    @FXML
    private RadioButton O2;

    Player p1=new Player();
    Player p2=new Player();
    @FXML
    private Button btn_back;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        X1.setToggleGroup(group1);
        O1.setToggleGroup(group1);
        X2.setToggleGroup(group2);
        O2.setToggleGroup(group2);
        recordFlag = false;

    }

    void move(Button button) {

        if (button.getText().equals("")) {
            button.setText("");
            if (X1.isSelected()) {
                O2.setSelected(true);
               
                //button.setText("X");
            } else if (O1.isSelected()) {
                X2.setSelected(true);
                p1.setSymbol("O");
                p2.setSymbol("X");
               // button.setText("O");
            }
        }
    }
    @FXML
    void player1Symbol(ActionEvent event) {

        if (X1.isSelected()) {
            O2.setSelected(true);

            // Setting Symbols for players
            p1.setSymbol("X");
            p2.setSymbol("O");
        } else if (O1.isSelected()) {
            X2.setSelected(true);

            // Setting Symbols for players
            p1.setSymbol("O");
            p2.setSymbol("X");
        }
        // Set initial turn for player with symbol CROSS
        if (p1.getSymbol().equals("X")) {
            setTurn(p1);

        } else {
            setTurn(p2);

        }
    }

    @FXML
    void player2Symbol(ActionEvent event) {

        if (X2.isSelected()) {
            O1.setSelected(true);

            p1.setSymbol("O");

            p2.setSymbol("X");

        } else if (O2.isSelected()) {
            X1.setSelected(true);

            p1.setSymbol("X");
            p2.setSymbol("O");
        }

        if (p1.getSymbol().equals("X")) {
            setTurn(p1);

        } else {
            setTurn(p2);

        }
    }


    

    @FXML
    void play(ActionEvent event) {
        if (txt_name2.getText().equals("") || txt_name1.getText().equals("")) {
            showAlert("Please,Enter your name.");

        } else if (!X1.isSelected() && !O1.isSelected()) {
            showAlert("Please, Choose X or O.");

        } else {
            
		
            try {
                LocalMultiplayerViewController.gameMode = "twoPlayers";
                FXMLLoader loader = new FXMLLoader(getClass().getResource("LocalMultiplayerView.fxml"));
                Parent root = loader.load();

                //Get controller of scene2
                LocalMultiplayerViewController o = loader.getController();
                o.scoreP1 = 0;
                o.scoreP2 = 0;
                //Pass whatever data you want. You can have multiple method calls here
                 o.transferMessageRecordFlag(recordFlag);
                o.transferMesssageText(txt_name1.getText(),txt_name2.getText());
                o.transferMessageButtons(X1, O1,X2,O2);
                
                //o.transferMessageRecordFlag(recordFlag);
                o.transferPlayers(p1,p2);
                o.transferFlag(false, null);
              

                Scene scene=new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                scene.getStylesheets().add("/CSS/Project.css");
                stage.setResizable(false);
                stage.show();

            } catch (IOException ex) {
                Logger.getLogger(LocalViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }


    void showAlert(String mess) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, mess, ButtonType.CANCEL);
        alert.setTitle("ERROR");
        alert.setHeaderText(null);
        alert.setContentText(mess);
        alert.show();
    }

  
    @FXML
    void back(ActionEvent event) {
        try {

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = (Scene) ((Node) event.getSource()).getScene();
            Parent root = FXMLLoader.load(getClass().getResource("StartView.fxml"));
            scene.setRoot(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(LocalMultiplayerViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void record(ActionEvent event) {
        // btn_record.setDisable(true);
        if(btn_record.isSelected()){
         recordFlag = true;
    }else
        {
         recordFlag = false;
        }
    }
  

}
