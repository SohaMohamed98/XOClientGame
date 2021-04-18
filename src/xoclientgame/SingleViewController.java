/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xoclientgame;

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
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author SoHa
 */
public class SingleViewController implements Initializable {

    @FXML
    private TextField txt_name;
    @FXML
    private Button btn_play;
    @FXML
    private CheckBox btn_record;
    @FXML
    private RadioButton X;
    @FXML
    private RadioButton O;

    final ToggleGroup group = new ToggleGroup();
    //final ToggleGroup groupLevels = new ToggleGroup();
    private boolean recordFlag;
    @FXML
    private ToggleButton btn_easy;
    @FXML
    private ToggleButton btn_meduim;
    @FXML
    private ToggleButton btn_hard;

    String levels="1"; 
    @FXML
    private ToggleGroup groupLevels;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        X.setToggleGroup(group);
        O.setToggleGroup(group);
        
        btn_easy.setToggleGroup(groupLevels);
        btn_meduim.setToggleGroup(groupLevels);
        btn_hard.setToggleGroup(groupLevels);
        
    
        
        recordFlag = false;

    }

    void move(Button button) {

        if (button.getText() == "") {
            button.setText("");
            if (X.isSelected()) {
                button.setText("X");
            } else if (O.isSelected()) {
                button.setText("O");
            }
        }
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
    void play(ActionEvent event) {
        if (txt_name.getText() == null || txt_name.getText().equals("")) {
            showAlert("Please,Enter your name.");

        } else if (X.isSelected() == false && O.isSelected() == false) {
            showAlert("Please, Choose X or O.");

        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("SinglePlayerView.fxml"));
                Parent root = loader.load();

                //Get controller of scene2
                SinglePlayerViewController o = loader.getController();
                o.scoreP1 = 0;
                o.scoreP2 = 0;

                //Pass whatever data you want. You can have multiple method calls here
                o.transferMesssageText(txt_name.getText());
                o.transferMessageButtons(X, O);
                o.transferMessageRecordFlag(recordFlag,levels);

                Scene scene=new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                scene.getStylesheets().add("/CSS/Project.css");
                stage.setResizable(false);
                stage.show();

            } catch (IOException ex) {
                Logger.getLogger(SingleViewController.class.getName()).log(Level.SEVERE, null, ex);
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
    private void easy(ActionEvent event) {
         
       
        btn_easy.setStyle("-fx-background-color: #00FF00; -fx-background-radius: 100;");
        btn_hard.setStyle("-fx-background-color:  #57D1C9; -fx-background-radius: 100;");
        btn_meduim.setStyle("-fx-background-color: #FFE869; -fx-background-radius: 100;");
       
        levels="1";
    }

    @FXML
    private void meduim(ActionEvent event) {
        
         btn_easy.setStyle("-fx-background-color: #ED5485; -fx-background-radius: 100;");
        btn_hard.setStyle("-fx-background-color:  #57D1C9; -fx-background-radius: 100;");
        btn_meduim.setStyle("-fx-background-color: #00FF00; -fx-background-radius: 100;");
        
        levels="2";
    }

    @FXML
    private void hard(ActionEvent event) {
        
         btn_easy.setStyle("-fx-background-color: #ED5485; -fx-background-radius: 100;");
        btn_hard.setStyle("-fx-background-color:  #00FF00; -fx-background-radius: 100;");
        btn_meduim.setStyle("-fx-background-color: #FFE869; -fx-background-radius: 100;");
        
        levels="3";
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
