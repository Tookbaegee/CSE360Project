/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkginterface.CSE360Project;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Komal
 */
public class Interface extends Application {

    @Override
    public void start(Stage primaryStage) {

        GridPane baseGridPane = new GridPane();
        GridPane leftGridPane = new GridPane();
        GridPane rightGridPane = new GridPane();

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

        //Add panels to Left Grid Pane
        leftGridPane.add(table, 0, 0);
        leftGridPane.add(hbox, 0, 1);

        // Add panels to Right Grid Pane
        TextField descripField = new TextField();
        TextField priorityField = new TextField();
        TextField dueField = new TextField();
        TextField status = new TextField();

        // Labels for the textfields for the right pane
        Label descripLabel = new Label("Description");
        Label priorityLabel = new Label("Priority");
        Label dueLabel = new Label("Due Date");
        Label statusLabel = new Label("Status");

        // Create Right Pane
        rightGridPane.add(descripLabel, 0, 0);
        rightGridPane.add(descripField, 0, 1);
        rightGridPane.add(priorityLabel, 0, 2);
        rightGridPane.add(priorityField, 0, 3);
        rightGridPane.add(dueLabel, 0, 4);
        rightGridPane.add(dueField, 0, 5);
        rightGridPane.add(statusLabel, 0, 6);
        rightGridPane.add(status, 0, 7);
        
        // Center labels on right pane
        rightGridPane.setHalignment(descripLabel, javafx.geometry.HPos.CENTER);
        rightGridPane.setHalignment(priorityLabel, javafx.geometry.HPos.CENTER);
        rightGridPane.setHalignment(dueLabel, javafx.geometry.HPos.CENTER);
        rightGridPane.setHalignment(statusLabel, javafx.geometry.HPos.CENTER);
        
        
        HBox hboxLayOut = new HBox(leftGridPane, rightGridPane);
        //vbox.getChildren().addAll(leftGridPane, rightGridPane);
        Scene scene = new Scene(hboxLayOut, 450, 250);
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
