/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xoclientgame;

import java.io.File;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import static jdk.nashorn.internal.objects.NativeDebug.getClass;

/**
 *
 * @author Alshaimaa
 */
public class ShowVideo {
   
    void video(String Name, boolean flag) {
        MediaPlayer mediaPlayer;
        String vurl ;
        String title;

       if(flag){
           vurl = "/video/winnervideo.mp4";
           title="congaturation";
       }else{
            vurl = "/video/loservideo.mp4";
             title="Game Over";
       }
        Media media = new Media(getClass().getResource(vurl).toExternalForm());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        Stage dialog = new Stage();
        dialog.setHeight(370);
        dialog.setWidth(370);
        Scene scene = new Scene(new Group(new MediaView(mediaPlayer)));
        dialog.setTitle(title+"  "+Name);
        dialog.setResizable(false);
        dialog.setScene(scene);
        dialog.show();
        dialog.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                mediaPlayer.stop();
                dialog.close();
            }

        });
    
    }
        
  
}
