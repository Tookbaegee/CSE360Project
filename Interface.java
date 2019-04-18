/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CSE360Project;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Komal
 */
public class Interface extends Application {
    
    private ObservableList<Todo> todos;
     
    private GridPane createGridPane() {
            GridPane gridpane = new GridPane();
            gridpane.setPadding(new Insets(5));
            gridpane.setHgap(5);
            gridpane.setVgap(5);

            ColumnConstraints column1 = new ColumnConstraints(100);
            ColumnConstraints column2 = new ColumnConstraints(50, 150, 300);

            column2.setHgrow(Priority.ALWAYS);
            gridpane.getColumnConstraints().addAll(column1, column2);

            Label descriptionlabel = new Label("Description");
            TextField descriptionfield = new TextField();

            Label numberlabel = new Label("Priority Number");
            TextField numberfield = new TextField();

            Label datelabel = new Label("Date");

            DatePicker datePicker = new DatePicker();

                HBox hbox = new HBox(datePicker);
                hbox.setPadding(new Insets( 0,0,0,0));

            Label status =new Label("Status");
             ChoiceBox<String> choiceBox = new ChoiceBox<>();
                choiceBox.getItems().addAll("Status","Not Started","In progress","Finished");
                choiceBox.setValue("Status");


                VBox layout = new VBox(10);
                layout.setPadding(new Insets(10, 10, 10, 10));
                layout.getChildren().addAll(choiceBox);




            Button saveButton = new Button("Save");

            GridPane.setHalignment(descriptionlabel, HPos.RIGHT);
            GridPane.setHalignment(numberlabel, HPos.RIGHT);
            GridPane.setHalignment(datelabel, HPos.RIGHT);
            GridPane.setHalignment(status, HPos.RIGHT);

            GridPane.setHalignment(descriptionfield, HPos.LEFT);
            GridPane.setHalignment(numberfield, HPos.LEFT);
            GridPane.setHalignment(hbox, HPos.LEFT);
            GridPane.setHalignment(choiceBox, HPos.LEFT);

            GridPane.setHalignment(saveButton, HPos.RIGHT);

            gridpane.add(descriptionlabel, 0, 0);
            gridpane.add(descriptionfield, 1, 0);

            gridpane.add(numberlabel, 0, 1);
            gridpane.add(numberfield, 1, 1);

            gridpane.add(datelabel, 0, 2);
            gridpane.add(hbox, 1, 2);

            gridpane.add(status, 0, 3);
            gridpane.add(choiceBox, 1, 3);


            gridpane.add(saveButton, 1, 4);

            return gridpane;

       }

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
        
        todos = FXCollections.observableArrayList();
        table.setItems(todos);
        
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
        
        create.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                  final Stage popup = new Stage();
                  popup.initModality(Modality.APPLICATION_MODAL);
                  popup.initOwner(primaryStage);
                  BorderPane root = new BorderPane();
                  GridPane gridpane = createGridPane();
                  root.setCenter(gridpane); 
                  Scene popupScene = new Scene(root, 300, 200);
                  popup.setScene(popupScene);
                  popup.show();
//                Todo newTodo = new Todo("Test", 1, new Date(System.currentTimeMillis()), 1);
//                JsonEditor.resetJson();
//                List<Todo> todoList = new ArrayList<Todo>();
//                todoList.add(newTodo);
//                JsonEditor.writeTodo(todoList);   
            }
        });  
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
