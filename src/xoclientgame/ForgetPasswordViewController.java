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
public class ForgetPasswordViewController implements Initializable {

    @FXML
    private Button btn_confirm;
    @FXML
    private TextField txt_username;
    @FXML
    private TextField txt_newPass;
    @FXML
    private TextField txt_confirmPass;
    @FXML
    private Hyperlink link_login;
    
     @FXML
    public void loginLink(ActionEvent event)
    {
        try {
                
                 Parent root = FXMLLoader.load(getClass().getResource("LoginView.fxml"));
                 Scene scene = new Scene(root);
                 Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
                 stage.setScene(scene);
                 stage.show();
            } catch (IOException ex) {
                Logger.getLogger(SignUpViewController.class.getName()).log(Level.SEVERE, null, ex);
            }  
    }

    @FXML
    public void confirm(ActionEvent event){
        if (txt_username.getText().equals("") || txt_newPass.getText().equals("") || txt_confirmPass.getText().equals("")) {
            showAlert("Failed, Enter all data, please");

        }else if(!txt_newPass.getText().equals(txt_confirmPass.getText())){
            showAlert("Failed, new password must match confirm password, please");
        }else {
            showAlert("the password changed Successfully");

            try {
                boolean result = ServerConnection.forgetPassword(txt_username.getText(), txt_newPass.getText());

                if (result) {
                    Parent root = FXMLLoader.load(getClass().getResource("LoginView.fxml"));
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
