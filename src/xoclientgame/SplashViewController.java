/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xoclientgame;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.omg.CORBA.TRANSIENT;

/**
 * FXML Controller class
 *
 * @author SoHa
 */
public class SplashViewController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
     @FXML AnchorPane ap;
    @FXML
    private ImageView img;
      
    class ShowSplashScreen extends Thread{
        @Override
        public void run(){
            try {
                Thread.sleep(5000);
               
                Platform.runLater(() -> {
                    Stage stage = new Stage();
                    StageView.st=stage;
                    Parent root = null;
                    try {
                        root = FXMLLoader.load(getClass().getResource("StartView.fxml"));
                    } catch (IOException ex) {
                        Logger.getLogger(SplashViewController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Scene scene = new Scene(root);
                  //  stage.initStyle(StageStyle.TRANSPARENT);
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                    ap.getScene().getWindow().hide();
                });                
            } catch (InterruptedException ex) {
                Logger.getLogger(SplashViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         new ShowSplashScreen().start();
        TranslateTransition t=new TranslateTransition();
        t.setDuration(javafx.util.Duration.seconds(4));
        t.setNode(img);
        t.setToY(-50);
        ScaleTransition s= new ScaleTransition(javafx.util.Duration.seconds(4),img);
        s.setByX(2);
        s.setByY(2);
        t.play();
        s.play();
       
    }    
    
}
