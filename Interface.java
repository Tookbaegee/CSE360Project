/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CSE360Project.CSE360Project;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
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
import javafx.scene.layout.ColumnConstraintsBuilder;
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
    
    // Class variables
    TextField descripField;
    TextField priorityField;
    DatePicker duePicker;
    ComboBox<String> statusComboBox;
    Label descripLabel;
    Label priorityLabel;
    Label dueLabel;
    Label statusLabel;
    Label startLabel;
    DatePicker startPicker;
    Label finishLabel;
    DatePicker finishPicker;
    Button edit;
    Button delete;
    Button apply;
    Button cancelEdit;
    Button complete;
    
    TableView<Todo> todoTableView;
    private ObservableList<Todo> todos = FXCollections.observableArrayList();
    
    // Make table method
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
        dateCol.setCellFactory(new Callback<TableColumn<Todo, Date>, TableCell<Todo, Date>>() 
        {
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
    
     private void deletePopUp()
    {
        Alert deleteAlert = new Alert(AlertType.CONFIRMATION);
        deleteAlert.setTitle("Confirmation Dialog");
        deleteAlert.setHeaderText("Delete Confirmation Dialog");
        deleteAlert.setContentText("Are you sure you want to delete?");
        Optional<ButtonType> result = deleteAlert.showAndWait();
        if(!result.isPresent()){
        }  // alert is exited, no button has been pressed.
        else if(result.get() == ButtonType.OK){
            todoTableView.getItems().remove(todoTableView.getSelectionModel().getSelectedItem());
        }
    }
    
    private void savePopUp()
    {
         Alert alert=new Alert(AlertType.CONFIRMATION);
         alert.setTitle("Save Button");
         alert.setHeaderText("Are you sure you want to save these changes and overwrite the saved list?");
         Optional<ButtonType> result = alert.showAndWait();
         if(!result.isPresent()){
         }  // alert is exited, no button has been pressed.
         else if(result.get() == ButtonType.OK){
            List<Todo> todolist = new ArrayList<>();
            todolist.addAll(todos);
            JsonEditor.resetJson();
            JsonEditor.writeTodo(todolist);
         }       
    } 
    
    private void restartPopUp()
    {
        Alert alert=new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Restart Button");
        alert.setHeaderText("Are you sure you want to discard all changes and start from scratch?"); 
        Optional<ButtonType> result = alert.showAndWait();
        if(!result.isPresent()){
        }  // alert is exited, no button has been pressed.
        else if(result.get() == ButtonType.OK){
            todos.clear();
        }
    }
    
    private void restorePopUp()
    {
        Alert alert=new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Restore Button");
        alert.setHeaderText("Are you sure you want to discard all changes and load the saved list?");
        Optional<ButtonType> result = alert.showAndWait();
        if(!result.isPresent()){
        }  // alert is exited, no button has been pressed.
        else if(result.get() == ButtonType.OK)
        {
            todos.clear();
            todos.addAll(JsonEditor.readTodo());
        }      
    }
      
    private void applyPopUp()
    {
        Alert alert=new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Restore Button");
        alert.setHeaderText("Are you sure you apply the edit made?");
        Optional<ButtonType> result = alert.showAndWait();
        if(!result.isPresent()){
        }  // alert is exited, no button has been pressed.
        else if(result.get() == ButtonType.OK){
            Todo editedTodo = new Todo();
            apply.setVisible(false);
            edit.setVisible(true);
            delete.setVisible(true);
            
            String description = descripField.getText();
            int priorityNum = Integer.parseInt(priorityField.getText());
            LocalDate localDueDate = duePicker.getValue();
            Instant dueDateInstant = Instant.from(localDueDate.atStartOfDay(ZoneId.systemDefault()));
            Date dueDate = Date.from(dueDateInstant);
            if(startPicker.visibleProperty().get()){
                LocalDate localStartDate = startPicker.getValue();
                Instant startDateInstant = Instant.from(localStartDate.atStartOfDay(ZoneId.systemDefault()));
                Date startDate = Date.from(startDateInstant);
            }
            if(finishPicker.visibleProperty().get()){
                LocalDate localFinishDate = duePicker.getValue();
                Instant finishDateInstant = Instant.from(localFinishDate.atStartOfDay(ZoneId.systemDefault()));
                Date finishDate = Date.from(finishDateInstant);
            }
          
            
        }      
    }
    
    private GridPane createPopUp() 
    {
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
            ComboBox<String> comboBox = new ComboBox<>();
            comboBox.getItems().addAll("Not Started","In Progress", "Finished");
            comboBox.setValue("Status");

            VBox layout = new VBox(10);
            layout.setPadding(new Insets(10, 10, 10, 10));
            layout.getChildren().addAll(comboBox);

            Label startDateLabel = new Label("Start Date");          
            DatePicker startDatePicker = new DatePicker();               
            HBox hBoxStartDate = new HBox(startDatePicker);
            hBoxStartDate.setPadding(new Insets( 0,0,0,0));       
            startDateLabel.visibleProperty().set(false);
            hBoxStartDate.visibleProperty().set(false);
            
            Label finishDateLabel = new Label ("Finish Date");
            DatePicker finishDatePicker = new DatePicker();
            HBox hBoxFinishDate = new HBox(finishDatePicker);
            hBoxFinishDate.setPadding(new Insets(0, 0, 0, 0));
            finishDateLabel.visibleProperty().set(false);
            hBoxFinishDate.visibleProperty().set(false);
            
            //hides if comboBox choice is not in progress
            comboBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>()
            {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) 
                {
                    int index = (Integer)newValue;
                    startDateLabel.visibleProperty().set(index == 1 || index == 2);
                    hBoxStartDate.visibleProperty().set(index == 1 || index == 2);
                    finishDateLabel.visibleProperty().set(index == 2);
                    hBoxFinishDate.visibleProperty().set(index == 2);
                }
            });
            
            Button saveButton = new Button("Create");
            saveButton.setOnAction(new EventHandler<ActionEvent>() 
            {
                @Override
                public void handle(ActionEvent event) 
                {
                    String description = descriptionField.getText();
                    int priorityNum = Integer.parseInt(numberField.getText());
                    LocalDate localDueDate = dueDatePicker.getValue();
                    Instant dueDateInstant = Instant.from(localDueDate.atStartOfDay(ZoneId.systemDefault()));
                    Date dueDate = Date.from(dueDateInstant);
                    String status = comboBox.getValue();
                    Date startDate = new Date();
                    Date finishDate = new Date();
                    if(comboBox.getValue().equals("In Progress") || comboBox.getValue().equals("Finished"))
                    {
                        LocalDate localStartDate = startDatePicker.getValue();
                        Instant startDateInstant = Instant.from(localStartDate.atStartOfDay(ZoneId.systemDefault()));
                        startDate = Date.from(startDateInstant);        
                    }
                    else if(comboBox.getValue().equals("Finished")){
                        LocalDate localFinishDate = finishDatePicker.getValue();
                        Instant finishDateInstant = Instant.from(localFinishDate.atStartOfDay(ZoneId.systemDefault()));
                        finishDate = Date.from(finishDateInstant);    
                    }
                    
                    Todo todo = new Todo(description, priorityNum, dueDate, status, startDate, finishDate);
                    boolean priorityDup = false;
                    for(int i = 0; i < todos.size(); i++){
                        if(todos.get(i).getPriorityNum() == priorityNum){
                            priorityDup = true;
                        }
                    }
                    if(priorityDup){
                    
                        //allert user that an entry with the priority number being entered already exists in the list.
                        
                           Alert alert = new Alert(AlertType.WARNING);
                           alert.setTitle("Warning Button");
                           alert.setHeaderText("The priority number is not unique! Do you want to push all other events?");
                           alert.show(); 
                    }else{
                          todos.add(todo);
                    }
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
            GridPane.setHalignment(comboBox, HPos.LEFT);
            GridPane.setHalignment(hBoxStartDate, HPos.LEFT);

            GridPane.setHalignment(saveButton, HPos.RIGHT);

            gridpane.add(descriptionLabel, 0, 0);
            gridpane.add(descriptionField, 1, 0);

            gridpane.add(numberLabel, 0, 1);
            gridpane.add(numberField, 1, 1);

            gridpane.add(dueDateLabel, 0, 2);
            gridpane.add(hBoxDueDate, 1, 2);

            gridpane.add(statusLabel, 0, 3);
            gridpane.add(comboBox, 1, 3);
            
            gridpane.add(startDateLabel, 0, 4);
            gridpane.add(hBoxStartDate, 1, 4);
            
            gridpane.add(finishDateLabel, 0, 5);
            gridpane.add(hBoxFinishDate, 1, 5);

            gridpane.add(saveButton, 1, 6);

            return gridpane;

       }
    private void completePopUp()
    {
        Alert alert=new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Complete Button");
        alert.setHeaderText("Are you sure you have completed task and would like to remove it from list?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK)
        {
            todoTableView.getItems().remove(todoTableView.getSelectionModel().getSelectedItem());
        }
    }
    
    private void priorityUniqueError()
    {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error Alert");
        alert.setHeaderText("Cannot edit task");
        alert.setContentText("Priority is not an integer");
        alert.showAndWait();
    }
    
    private GridPane rightAppPane()
    {
        // Declare right GridPane to work on
        GridPane rightGridPane = new GridPane();
                
        // Add constraints and gaps between components
        rightGridPane.setHgap(8);
        rightGridPane.setVgap(8);
                
        // Add panels to Right Grid Pane
        descripField = new TextField();
        descripField.setDisable(true);
        priorityField = new TextField();
        priorityField.setDisable(true);
        duePicker = new DatePicker();
        duePicker.setDisable(true);
        statusComboBox = new ComboBox<>();
        statusComboBox.getItems().addAll("Not Started","In Progress", "Finished");
        statusComboBox.setValue("");
        statusComboBox.setDisable(true);
        startPicker = new DatePicker();
        startPicker.setDisable(true);
        startPicker.setVisible(false);
        finishPicker = new DatePicker();
        finishPicker.setDisable(true);
        finishPicker.setVisible(false);
        
        
        // Labels for the textfields for the right pane
        descripLabel = new Label("Description");
        priorityLabel = new Label("Priority");
        dueLabel = new Label("Due Date");
        statusLabel = new Label("Status");
        startLabel = new Label("Start Date");
        startLabel.setVisible(false);
        finishLabel = new Label("Finish Date");
        finishLabel.setVisible(false);
        
        // Add Edit and Delete Buttons
        edit = new Button("Edit");
        delete = new Button("Delete");
        apply = new Button("Apply");
        cancelEdit = new Button("Cancel");
        complete = new Button("Complete");
        apply.setVisible(false);
        cancelEdit.setVisible(false);
        edit.setDisable(true);
        complete.setDisable(true);
        delete.setDisable(true);
        edit.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                apply.setVisible(true);
                cancelEdit.setVisible(true);
                edit.setVisible(false);
                delete.setVisible(false);
                descripField.setDisable(false);
                priorityField.setDisable(false);
                duePicker.setDisable(false);
                statusComboBox.setDisable(false);
                startPicker.setDisable(false);
                finishPicker.setDisable(false);
            }
            
        });
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                deletePopUp();
            }
        });
        apply.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                applyPopUp();
            }
        });
        
        complete.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                completePopUp();
            }});
      
        HBox buttonBox = new HBox(edit, delete, complete);
        HBox editBox = new HBox(apply, cancelEdit);
        
        // Create Right Pane
        rightGridPane.add(descripLabel, 0, 0);
        rightGridPane.add(descripField, 0, 1);
        rightGridPane.add(priorityLabel, 0, 2);
        rightGridPane.add(priorityField, 0, 3);
        rightGridPane.add(dueLabel, 0, 4);
        rightGridPane.add(duePicker, 0, 5);
        rightGridPane.add(statusLabel, 0, 6);
        rightGridPane.add(statusComboBox, 0, 7);
        rightGridPane.add(startLabel, 0, 8);
        rightGridPane.add(startPicker,0, 9);
        rightGridPane.add(finishLabel, 0, 10);
        rightGridPane.add(finishPicker, 0, 11);
        rightGridPane.add(buttonBox, 0, 15);
        rightGridPane.add(editBox, 0, 16);
        
         // Center labels on right pane
        rightGridPane.setHalignment(descripLabel, javafx.geometry.HPos.CENTER);
        rightGridPane.setHalignment(priorityLabel, javafx.geometry.HPos.CENTER);
        rightGridPane.setHalignment(dueLabel, javafx.geometry.HPos.CENTER);
        rightGridPane.setHalignment(statusLabel, javafx.geometry.HPos.CENTER);
        rightGridPane.setHalignment(buttonBox, javafx.geometry.HPos.CENTER);
        return rightGridPane;
    }
   
    @Override
    public void start(Stage primaryStage) {
        GridPane baseGridPane = new GridPane();
        GridPane leftGridPane = new GridPane();      
        GridPane rightGridPane = rightAppPane();
        //add constraints and gaps between components
        leftGridPane.setHgap(8);
        leftGridPane.setVgap(8);
        baseGridPane.setHgap(8);
        baseGridPane.setVgap(8);
        
        baseGridPane.setHgrow(leftGridPane, Priority.ALWAYS);
        baseGridPane.setVgrow(leftGridPane, Priority.ALWAYS);
        
        // Create buttons at bottom of app
        Button create = new Button("Create");
        Button print = new Button("Print");
        Button save = new Button("Save");
            
        Button restore = new Button("Restore");
        Button restart = new Button("Restart");
        
        save.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) 
            {
                savePopUp();
            }
         });
    
        restart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                restartPopUp();
            }
        });
        
        restore.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                 restorePopUp();
            }
        });
        HBox hbox = new HBox(create, print, save, restore, restart);
        todoTableView = createTableView(todos);
        
        // Add table that will display To-Do List Items
      
        //Add panels to Left Grid Pane
        leftGridPane.add(todoTableView, 0, 0);
        leftGridPane.add(hbox, 0, 1);
        
        // right panel listens to the table view selection
        todoTableView.getSelectionModel().selectedItemProperty().addListener
        (
            (ObservableValue<? extends Todo> observable, Todo oldValue,Todo newValue) -> 
            {
              if (observable != null && observable.getValue() != null) 
              {
                descripField.setDisable(false);
                priorityField.setDisable(false);
                duePicker.setDisable(false);
                statusComboBox.setDisable(false);
                descripField.setText(observable.getValue().getDescription());
                priorityField.setText(""+observable.getValue().getPriorityNum());
                 String pattern = "MM-dd-yyyy";
                 SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                 duePicker.getEditor().setText(simpleDateFormat.format(observable.getValue().getDueDate()));
                 statusComboBox.setValue(observable.getValue().getStatus());
                 if(observable.getValue().getStatus().equals("In Progress") || observable.getValue().getStatus().equals("Finished")){
                     
                     startLabel.setVisible(true);
                     startPicker.getEditor().setText(simpleDateFormat.format(observable.getValue().getStartDate()));
                     startPicker.setVisible(true);
                     startPicker.setDisable(false);
                 }
                  if(observable.getValue().getStatus().equals("Finished")){
                     finishLabel.setVisible(true);
                     finishPicker.getEditor().setText(simpleDateFormat.format(observable.getValue().getStartDate()));
                     finishPicker.setVisible(true);
                     finishPicker.setDisable(false);
                 }
                 apply.setVisible(false);
                 edit.setVisible(true);
                 delete.setVisible(true);
                 edit.setDisable(false);
                 delete.setDisable(false);
                 complete.setDisable(false);
               }
            }
        );
        todoTableView.getSelectionModel().selectedItemProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                 descripField.clear();
                 priorityField.clear();
                 duePicker.getEditor().clear();
                 startPicker.getEditor().clear();
                 edit.setDisable(true);
                 delete.setDisable(true);
                 complete.setDisable(true);
                 apply.setVisible(false);
                 edit.setVisible(true);
                 delete.setVisible(true);
                 complete.setVisible(true);
            }
        });

        // Labels for the textfields for the right pane
        Label descripLabel = new Label("Description");
        Label priorityLabel = new Label("Priority");
        Label dueLabel = new Label("Due Date");
        Label statusLabel = new Label("Status");

        create.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                  final Stage popup = new Stage();
                  popup.initModality(Modality.APPLICATION_MODAL);
                  popup.initOwner(primaryStage);
                  BorderPane root = new BorderPane();
                  GridPane gridpane = createPopUp();
                  root.setCenter(gridpane); 
                  Scene popupScene = new Scene(root, 300, 200);
                  popup.setScene(popupScene);
                  popup.show();
            }
        });  
       baseGridPane.add(leftGridPane, 0, 0);
       baseGridPane.add(rightGridPane, 1, 0);

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
