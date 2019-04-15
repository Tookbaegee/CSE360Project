/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pkginterface;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Komal
 */
public class Interface extends Application {
    
    @Override
    public void start(Stage primaryStage) {
       
       GridPane gridPane = new GridPane();
       
       // Create buttons at bottom of app
       Button create = new Button("Create");
       Button print = new Button("Print");
       Button save = new Button("Save");
       Button restore = new Button("Restore");
       Button restart = new Button("Restart");
       HBox hbox = new HBox(create, print, save, restore, restart);
       
       TableView table = new TableView();
       // Add table that will display To-Do List Items
       TableColumn descripCol = new TableColumn("Description");
       TableColumn priorityCol = new TableColumn("Priority");
       TableColumn dateCol = new TableColumn("Due Date");
       TableColumn statusCol = new TableColumn("Status");

       // Add column headers to table
       table.getColumns().addAll(descripCol, priorityCol, dateCol, statusCol);
        
       //Add panels to Pane
       gridPane.add(table, 0, 0);
       gridPane.add(hbox, 0, 1);
       //pane.setTop(table);

       // set the scene 
       Scene scene = new Scene(gridPane, 300, 250);
       primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
