/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xoclientgame;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class GameHistoryController implements Initializable {
     @FXML
    private Button btn_back;
    @FXML
    private ListView listPlayer1;
    @FXML
    private ListView listPlayer2;
    @FXML
    private ListView listWinner;
  
    @FXML
    private Text historyText;
    @FXML
    private HBox hBox;
   
    ArrayList<Game> game;
   
  @FXML
  void back(ActionEvent event)
    {       
        try {
             Parent root;
             if(historyText.getText().equals("Local History")){
                 root = FXMLLoader.load(getClass().getResource("StartView.fxml"));
             }else{
                 root = FXMLLoader.load(getClass().getResource("ListPlayerView.fxml"));
             }
             Scene scene = new Scene(root);
             Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
             stage.setScene(scene);
             stage.setResizable(false);
             stage.show();
        } catch (IOException ex) {
            Logger.getLogger(GameHistoryController.class.getName()).log(Level.SEVERE, null, ex);
        }                
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
              
  
      
    }
    
    public void fillTable(){
        ObservableList hisP1 = FXCollections.observableArrayList();
        ObservableList hisP2 = FXCollections.observableArrayList();
        ObservableList hisW = FXCollections.observableArrayList();

        for (int i = 0; i < game.size(); i++) {
            String p1 = game.get(i).getPlayer1();
            String p2 = game.get(i).getPlayer2();
            String w = game.get(i).getWinner();
            hisP1.add((i+1)+"-  "+p1);
            hisP2.add((i+1)+"-  "+p2);
            hisW.add((i+1)+"-  "+w);

        }
        listPlayer1.setItems(hisP1);
        listPlayer2.setItems(hisP2);
        listWinner.setItems(hisW);
    }
     void translate(ArrayList<Game> game, String title){
        this.game=game;
        historyText.setText(title);
        
        fillTable();
    }
      
}
  