/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xoclientgame;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author SoHa
 */
public class SingleRecordViewController implements Initializable {

    @FXML
    private ListView<String> listRecord;

    @FXML
    private Button btn_back;
    String p1 = new String();
    String p2 = new String();
    boolean f;
    static ArrayList<Record> records;
    static String titleFlag;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    void back(ActionEvent event) {
        try {
            Parent root;
            if (titleFlag.equals("offline")) {
                root = FXMLLoader.load(getClass().getResource("StartView.fxml"));
            } else {
                root = FXMLLoader.load(getClass().getResource("ListPlayerView.fxml"));
            }
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(LocalMultiplayerViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void fillTable() {
        ObservableList recp1 = FXCollections.observableArrayList();

        for (int i = 0; i < records.size(); i++) {
            String p1 = records.get(i).getPlayer1();
            String p2 = records.get(i).getPlayer2();
            System.out.println(records.get(i).getMoves());

            recp1.add(p1 + " vs " + p2);

            listRecord.getItems();

        }
        listRecord.setItems(recp1);

        listRecord.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {

                for (int i = 0; i < recp1.size(); i++) {
                    if (listRecord.getSelectionModel().getSelectedIndex() == i) {

                        try {
                            //  player2 = listRecord.getItems().get(i);
                            System.out.println(i);

                            FXMLLoader loader = new FXMLLoader(getClass().getResource("LocalMultiplayerView.fxml"));
                            Parent root = loader.load();

                            LocalMultiplayerViewController l = loader.getController();

                            LocalMultiplayerViewController.gameMode = "twoPlayers";
                            l.transferFlag(true, records.get(i));

                            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            stage.setScene(new Scene(root));
                            stage.setResizable(false);
                            stage.show();

                        } catch (IOException ex) {
                            Logger.getLogger(SingleRecordViewController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }

                }

            }
        });
    }

    void translate() {
        fillTable();
    }

}
