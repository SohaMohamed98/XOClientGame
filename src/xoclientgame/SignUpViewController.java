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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author SoHa
 */
public class SignUpViewController implements Initializable {

    // Sign up Screen
    @FXML
    private TextField txt_nameS;

    @FXML
    private TextField txt_passS;

    @FXML
    private Button btn_sign;
    @FXML
    private Hyperlink link_login;

    @FXML
    public void signUP(ActionEvent event) {
        if (txt_nameS.getText().equals("") || txt_passS.getText().equals("")||
                 txt_nameS.getText().length()>20 || txt_passS.getText().length()>20) {
            showAlert("Rigistration Failed, Enter all data, please");

        } else {
            try {
                boolean result = ServerConnection.SignUp(txt_nameS.getText(), txt_passS.getText());

                if (result) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("ListPlayerView.fxml"));
                    Parent root = loader.load();
                    
                    ListPlayerViewController o = loader.getController();

                    o.transferMessageName1(txt_nameS.getText());
                            
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                   
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                }
            } catch (IOException ex) {
                Logger.getLogger(StartViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    public void loginLink(ActionEvent event) {
        try {

            Parent root = FXMLLoader.load(getClass().getResource("LoginView.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(SignUpViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showAlert(String mess) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, mess, ButtonType.CANCEL);
        alert.setTitle("");
        alert.setHeaderText(null);
        alert.setContentText(mess);
        alert.show();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
