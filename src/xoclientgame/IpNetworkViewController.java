/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xoclientgame;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
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
public class IpNetworkViewController implements Initializable {

    @FXML
    private TextField txt_ip;
    @FXML
    private Button btn_play;
    @FXML
    private Hyperlink btn_back;

    volatile boolean result = false;

    @FXML
    void back(ActionEvent event) {

        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("StartView.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(IpNetworkViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    void play(ActionEvent event) {
        boolean f;
        f = isNumericAddress(txt_ip.getText());
        if (f) {
            new Thread(new Runnable() {
                @Override
                public void run() {

                    result = ServerConnection.init(txt_ip.getText());

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (result) {

                                    Parent root;
                                    root = FXMLLoader.load(getClass().getResource("LoginView.fxml"));
                                    Scene scene = new Scene(root);
                                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                    stage.setScene(scene);
                                    stage.setResizable(false);
                                    stage.show();
                                } else {
                                    showAlert("IP address is not Valid please enter another one");
                                }
                            } catch (IOException ex) {
                                Logger.getLogger(StartViewController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                }
            }).start();
        } else {
            showAlert("IP address is not Valid please enter another one");
        }

    }

    private void showAlert(String mess) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, mess, ButtonType.CANCEL);
        alert.setTitle("Sorry");
        alert.setHeaderText(null);
        alert.setContentText(mess);
        alert.show();
    }

    public final static boolean isNumericAddress(String ipaddr) {

        //  Check if the string is valid
        if (ipaddr == null || ipaddr.length() < 9 || ipaddr.length() > 15) {
            return false;
        }

        //  Check the address string, should be n.n.n.n format
        StringTokenizer token = new StringTokenizer(ipaddr, ".");
        if (token.countTokens() != 4) {
            return false;
        }

        while (token.hasMoreTokens()) {
            //  Get the current token and convert to an integer value
            String ipNum = token.nextToken();
            try {
                int ipVal = Integer.valueOf(ipNum).intValue();
                if (ipVal < 0 || ipVal > 255) {
                    return false;
                }
            } catch (NumberFormatException ex) {
                // ex.printStackTrace();
                return false;

            }
        }

        //  Looks like a valid IP address
        return true;
    }

}
