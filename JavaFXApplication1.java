/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.*;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.geometry.*;
import javafx.scene.layout.GridPane;
import 	javafx.geometry.HPos;
import	javafx.geometry.VPos;
import javafx.geometry.Pos;
import javafx.scene.text.*;
import javafx.scene.control.*;
import javafx.scene.*;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.HBox;
import javafx.fxml.FXMLLoader;
import javafx.scene.paint.Color;


public class JavaFXApplication1 extends Application {

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
    BorderPane root = new BorderPane();
    Scene scene = new Scene(root, 380, 300, Color.WHITE);

    GridPane gridpane = createGridPane();
    

    
    root.setCenter(gridpane);
    
    

    primaryStage.setTitle("Task box");
    primaryStage.setScene(scene);
    primaryStage.show();
  }
   


  public static void main(String[] args) {
    launch(args);
  }
}
    
   