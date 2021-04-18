/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xoclientgame;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 *
 * @author 2NeT
 */
public class XOClientGame extends Application {
    
     @Override
    public void start(Stage stage) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("SplashView.fxml"));
           // Scene scene = new Scene(root);

            Scene scene = new Scene(root,725,550,Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);
        
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        } catch (IOException ex) {
            Logger.getLogger(XOClientGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                if (ServerConnection.running) {
                    ServerConnection.exit();
                    
                }
                System.exit(0);
            }
        });
    }

    @Override
    public void stop() throws Exception {
        if(ServerConnection.running)
        {
             ServerConnection.exit();
            
        }
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}

