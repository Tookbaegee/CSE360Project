/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CSE360Project;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 *
 * @author Komal
 */
public class Interface extends Application {
    
    private ObservableList<Todo> todos = FXCollections.observableArrayList();
    
    private TableView<Todo> createTableView(ObservableList<Todo> todos){
        final TableView<Todo> todoTableView = new TableView<>();
        todoTableView.setPrefWidth(600);
        todoTableView.setItems(todos);
        
        TableColumn<Todo, String> descripCol = new TableColumn("Description");
        TableColumn<Todo, Integer> priorityCol = new TableColumn("Priority");
        TableColumn<Todo, Date> dateCol = new TableColumn("Due Date");
        TableColumn<Todo, String> statusCol = new TableColumn("Status");
        
        // Add column headers to table
        todoTableView.getColumns().addAll(descripCol, priorityCol, dateCol, statusCol);
        
        //set column width
        descripCol.setPrefWidth(todoTableView.getPrefWidth() / 2);
        priorityCol.setPrefWidth(todoTableView.getPrefWidth() / 6);
        dateCol.setPrefWidth(todoTableView.getPrefWidth() / 6);
        statusCol.setPrefWidth(todoTableView.getPrefWidth() / 6);
        
        
        descripCol.setCellValueFactory(new PropertyValueFactory<Todo,String>("description"));
        priorityCol.setCellValueFactory(new PropertyValueFactory<Todo,Integer>("priorityNum"));
        dateCol.setCellValueFactory(new PropertyValueFactory<Todo, Date>("dueDate"));
        dateCol.setCellFactory(new Callback<TableColumn<Todo, Date>, TableCell<Todo, Date>>() {
            @Override
            public TableCell<Todo, Date> call(TableColumn<Todo, Date> col) {
              return new TableCell<Todo, Date>(){
                  protected void updateItem(Date duedate, boolean empty){
                      super.updateItem(duedate, empty);
                      if(empty){
                          setText(null);
                      }
                      else{
                          String pattern = "MM-dd-yyyy";
                          SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                          setText(simpleDateFormat.format(duedate));
                      }
                  }
              };
            }
        });
        statusCol.setCellValueFactory(new PropertyValueFactory<Todo,String>("status"));
       

        return todoTableView;
    }
    
    
     
    private GridPane createCreatePane() {
            
            GridPane gridpane = new GridPane();
            gridpane.setPadding(new Insets(5));
            gridpane.setHgap(5);
            gridpane.setVgap(5);

            ColumnConstraints column1 = new ColumnConstraints(100);
            ColumnConstraints column2 = new ColumnConstraints(50, 150, 300);

            column2.setHgrow(Priority.ALWAYS);
            gridpane.getColumnConstraints().addAll(column1, column2);

            Label descriptionLabel = new Label("Description");
            TextField descriptionField = new TextField();

            Label numberLabel = new Label("Priority Number");
            TextField numberField = new TextField();

            Label dueDateLabel = new Label("Due Date");

            DatePicker dueDatePicker = new DatePicker();

            HBox hBoxDueDate = new HBox(dueDatePicker);
            hBoxDueDate.setPadding(new Insets( 0,0,0,0));

            Label statusLabel = new Label("Status");
            ChoiceBox<String> choiceBox = new ChoiceBox<>();
                choiceBox.getItems().addAll("Not Started","In Progress","Finished");
                choiceBox.setValue("Status");


            VBox layout = new VBox(10);
            layout.setPadding(new Insets(10, 10, 10, 10));
            layout.getChildren().addAll(choiceBox);

            Label startDateLabel = new Label("Start Date");
            
            DatePicker startDatePicker = new DatePicker();

            HBox hBoxStartDate = new HBox(startDatePicker);
            hBoxStartDate.setPadding(new Insets( 0,0,0,0));
            
            startDateLabel.visibleProperty().set(false);
            hBoxStartDate.visibleProperty().set(false);
            //hides if choiceBox choice is not in progress
            choiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    int index = (Integer)newValue;
                    startDateLabel.visibleProperty().set(index == 1);
                    hBoxStartDate.visibleProperty().set(index == 1);
                }
            });
            
            

            Button saveButton = new Button("Create");
            saveButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    String description = descriptionField.getText();
                    int priorityNum = Integer.parseInt(numberField.getText());
                    LocalDate localDueDate = dueDatePicker.getValue();
                    Instant dueDateInstant = Instant.from(localDueDate.atStartOfDay(ZoneId.systemDefault()));
                    Date dueDate = Date.from(dueDateInstant);
                    String status = choiceBox.getValue();
                    Date startDate = new Date();
                    if(choiceBox.getValue().equals("In Progress")){
                        LocalDate localStartDate = startDatePicker.getValue();
                        Instant startDateInstant = Instant.from(localStartDate.atStartOfDay(ZoneId.systemDefault()));
                        startDate = Date.from(startDateInstant);        
                    }
                    
                    Todo todo = new Todo(description, priorityNum, dueDate, status, startDate);
                    todos.add(todo);
                }
            });

            GridPane.setHalignment(descriptionLabel, HPos.RIGHT);
            GridPane.setHalignment(numberLabel, HPos.RIGHT);
            GridPane.setHalignment(dueDateLabel, HPos.RIGHT);
            GridPane.setHalignment(statusLabel, HPos.RIGHT);
            GridPane.setHalignment(startDateLabel, HPos.RIGHT);

            GridPane.setHalignment(descriptionField, HPos.LEFT);
            GridPane.setHalignment(numberField, HPos.LEFT);
            GridPane.setHalignment(hBoxDueDate, HPos.LEFT);
            GridPane.setHalignment(choiceBox, HPos.LEFT);
            GridPane.setHalignment(hBoxStartDate, HPos.LEFT);

            GridPane.setHalignment(saveButton, HPos.RIGHT);

            gridpane.add(descriptionLabel, 0, 0);
            gridpane.add(descriptionField, 1, 0);

            gridpane.add(numberLabel, 0, 1);
            gridpane.add(numberField, 1, 1);

            gridpane.add(dueDateLabel, 0, 2);
            gridpane.add(hBoxDueDate, 1, 2);

            gridpane.add(statusLabel, 0, 3);
            gridpane.add(choiceBox, 1, 3);
            
            gridpane.add(startDateLabel, 0, 4);
            gridpane.add(hBoxStartDate, 1, 4);

            gridpane.add(saveButton, 1, 5);

            return gridpane;

       }

    @Override
    public void start(Stage primaryStage) {
        GridPane baseGridPane = new GridPane();
        GridPane leftGridPane = new GridPane();
        GridPane rightGridPane = new GridPane();

        //add constraints and gaps between components
        leftGridPane.setHgap(8);
        leftGridPane.setVgap(8);
        rightGridPane.setHgap(8);
        rightGridPane.setVgap(8);
        baseGridPane.setHgap(8);
        baseGridPane.setVgap(8);
        
        baseGridPane.setHgrow(leftGridPane, Priority.ALWAYS);
        baseGridPane.setVgrow(leftGridPane, Priority.ALWAYS);
//        ColumnConstraints leftcolcons = new ColumnConstraints();
//        leftcolcons.setHgrow(Priority.ALWAYS);
//        ColumnConstraints rightcolcons = new ColumnConstraints();
//        rightcolcons.setHgrow(Priority.NEVER);
//        RowConstraints tablerowcons = new RowConstraints();
//        tablerowcons.setVgrow(Priority.ALWAYS);
//        RowConstraints rightPanelrowcons = new RowConstraints();
//        rightPanelrowcons.setVgrow(Priority.ALWAYS);
        
//        baseGridPane.getColumnConstraints().addAll(leftcolcons, rightcolcons);
//        leftGridPane.getRowConstraints().addAll(tablerowcons);
//        rightGridPane.getRowConstraints().addAll(rightPanelrowcons);
        
        
        
        // Create buttons at bottom of app
        Button create = new Button("Create");
        Button print = new Button("Print");
        Button save = new Button("Save");
        Button restore = new Button("Restore");
        Button restart = new Button("Restart");
        HBox hbox = new HBox(create, print, save, restore, restart);
       
      
        // Add table that will display To-Do List Items
        TableView table = createTableView(todos);
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
                  GridPane gridpane = createCreatePane();
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
       baseGridPane.add(leftGridPane, 0, 0);
       baseGridPane.add(rightGridPane, 1, 0);
        //vbox.getChildren().addAll(leftGridPane, rightGridPane);
        Scene scene = new Scene(baseGridPane, 800, 600);
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
