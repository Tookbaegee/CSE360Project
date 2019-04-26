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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;


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
    Button displayAll;
    final Stage addPopUp = new Stage();
    final Stage displayPopUp = new Stage();
    Date date = new Date();   //new
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
    String formattedDate = sdf.format(date);
    
    TableView<Todo> todoTableView;
    private ObservableList<Todo> todos = FXCollections.observableArrayList();
    private List<Todo> completedTodos = new ArrayList<Todo>();
    private ObservableList<Todo> obsCompletedTodos = FXCollections.observableArrayList();
    
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
            public TableCell<Todo, Date> call(TableColumn<Todo, Date> col) 
            {
              return new TableCell<Todo, Date>(){
                  protected void updateItem(Date duedate, boolean empty)
                  {
                      super.updateItem(duedate, empty);
                      if(empty)
                      {
                          setText(null);
                      }
                      else
                      {
                          String pattern = "MM/dd/yyyy";
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
    
    private int getLastPriorityNum(){
        return todos.size();
    }
    
    private boolean checkDescripDup(String description){
        boolean descripDup = false;
        for(int todoIndex = 0; todoIndex < todos.size(); todoIndex++)
        {
            if(todos.get(todoIndex).getDescription().equals(description))
           {
               descripDup = true;
           }       
        }
        return descripDup;
    }
    
    private void incrementPriority(int priorityNum, int ogPriority){
        for(int todoIndex = 0; todoIndex < todos.size(); todoIndex++){
             if(todos.get(todoIndex).getPriorityNum() >= priorityNum && todos.get(todoIndex).getPriorityNum() < ogPriority ){
                 todos.get(todoIndex).incrementPriority();
             }
         }
    }
    
    private void decrementPriority(int priorityNum, int ogPriority){
        for(int todoIndex = 0; todoIndex < todos.size(); todoIndex++){
             if(todos.get(todoIndex).getPriorityNum() <= priorityNum && todos.get(todoIndex).getPriorityNum() > ogPriority ){
                 todos.get(todoIndex).decrementPriority();
             }
         }
    }
    
     private void decrementAllPriority(int priorityNum){
        for(int todoIndex = 0; todoIndex < todos.size(); todoIndex++){
             if(todos.get(todoIndex).getPriorityNum() > priorityNum){
                 todos.get(todoIndex).decrementPriority();
             }
         }
    }
    
     private void deletePopUp()
    {
        Alert deleteAlert = new Alert(AlertType.CONFIRMATION);
        deleteAlert.setTitle("Confirmation Dialog");
        deleteAlert.setHeaderText("Delete Confirmation Dialog");
        deleteAlert.setContentText("Are you sure you want to delete?");
        Optional<ButtonType> result = deleteAlert.showAndWait();
        if(result.get() == ButtonType.OK)
        {   
            int ogPriority = todoTableView.getSelectionModel().getSelectedItem().getPriorityNum();
            todoTableView.getItems().remove(todoTableView.getSelectionModel().getSelectedItem());
            decrementAllPriority(ogPriority);
        }
    }
    
      private void createConfirmPopUp()
    {
        Alert confirmAlert = new Alert(AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirmation Dialog");
        confirmAlert.setHeaderText("Entry successfully added");
        confirmAlert.setContentText("You may now exit this window");
        confirmAlert.show();
        addPopUp.close();
        
    };
      
    private void savePopUp()
    {
         Alert alert=new Alert(AlertType.CONFIRMATION);
         alert.setTitle("Save Button");
         alert.setHeaderText("Are you sure you want to save these changes and overwrite the saved list?");
         Optional<ButtonType> result = alert.showAndWait();
         if(result.get() == ButtonType.OK)
         {
            List<Todo> todolist = new ArrayList<>();
            todolist.addAll(todos);
            BinaryEditor.resetBin();
            BinaryEditor.writeTodo(todolist);
            BinaryEditor.resetCompletedBin();
            BinaryEditor.writeCompletedTodo(completedTodos);
         }       
    } 
    
    private void restartPopUp()
    {
        Alert alert=new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Restart Button");
        alert.setHeaderText("Are you sure you want to discard all changes and start from scratch?"); 
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK)
        {
            todos.clear();
            completedTodos.clear();
            
        }
    }
    
    private void restorePopUp()
    {
        Alert alert=new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Restore Button");
        alert.setHeaderText("Are you sure you want to discard all changes and load the saved list?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK)
        {
            todos.clear();
            todos.addAll(BinaryEditor.readTodo());
            completedTodos.addAll(BinaryEditor.readCompletedTodo());

         }      
    }
      
    private void applyPopUp()
    {
        Alert alert=new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Edit Button");
        alert.setHeaderText("Are you sure you apply the edit made?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK)
        {
            Todo editedTodo = new Todo();
            int ogPriority = todoTableView.getSelectionModel().getSelectedItem().getPriorityNum();
            String description = "";
            try{
                description = descripField.getText().trim();
            }catch(NullPointerException e){      
            }
            int priorityNum = -1;             
            LocalDate localDueDate = duePicker.getValue();
            Date dueDate = new Date(0);
            try{
                Instant dueDateInstant = Instant.from(localDueDate.atStartOfDay(ZoneId.systemDefault()));
                dueDate = Date.from(dueDateInstant);
            }catch(NullPointerException e){
                
            }
            String status = "";
            if(statusComboBox.getSelectionModel().getSelectedItem() != null)
                status = statusComboBox.getSelectionModel().getSelectedItem();        
            Date startDate = new Date(0);
            Date finishDate = new Date(0);
      
            if(startPicker.visibleProperty().get()){     
                LocalDate localStartDate = startPicker.getValue();
                try{                 
                    Instant startDateInstant = Instant.from(localStartDate.atStartOfDay(ZoneId.systemDefault()));
                    startDate = Date.from(startDateInstant);
                }catch(NullPointerException e){
                    
                }
            }
            if(finishPicker.visibleProperty().get()){
                LocalDate localFinishDate = finishPicker.getValue();
                try{
                    Instant finishDateInstant = Instant.from(localFinishDate.atStartOfDay(ZoneId.systemDefault()));
                    finishDate = Date.from(finishDateInstant);          
                }catch(NullPointerException e){
                    
                }
            }
            try{
                priorityNum = Integer.parseInt(priorityField.getText());
            }catch(NumberFormatException numberFormatException){
                
            }
            if(!description.equals(todoTableView.getSelectionModel().getSelectedItem().getDescription()) && checkDescripDup(description)){
                descripUniqueError(); 
            }
            else if(priorityNum == -1){
                  priorityUniqueError();  
            }
            else if(priorityNum < 1 ||  priorityNum > todos.size())
            {
                priorityRangeError();
            }
            else if(description == null || description.equals("") || dueDate.getTime() == 0 || status.equals("") || (startPicker.visibleProperty().get() && startDate.getTime() == 0) || (finishPicker.visibleProperty().get() && finishDate.getTime() == 0)){
                Alert fieldEmptyAlert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Alert");
                alert.setHeaderText("Cannot edit task");
                alert.setContentText("At least one of the field is empty.");
                alert.showAndWait();
            }
            else{
                editedTodo = new Todo(description, priorityNum, dueDate, status, startDate, finishDate);
                //alert user that an entry with the priority number being entered already exists in the list
                   if(!result.isPresent()){
                    }                 
                    else if(result.get() == ButtonType.OK){
                        incrementPriority(priorityNum, ogPriority);
                        decrementPriority(priorityNum, ogPriority);
                    }
            
                todos.set(todoTableView.getSelectionModel().getSelectedIndex(), editedTodo);    
                apply.setVisible(false);
                cancelEdit.setVisible(false);
                edit.setVisible(true);
                delete.setVisible(true);
                descripField.setEditable(false);
                priorityField.setEditable(false);
                duePicker.setDisable(true);
                statusComboBox.setDisable(true);
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
            numberField.setDisable(true);
            numberField.setVisible(true);
            numberField.setText(""+(getLastPriorityNum() + 1));

            Label dueDateLabel = new Label("Due Date");
            DatePicker dueDatePicker = new DatePicker();
            HBox hBoxDueDate = new HBox(dueDatePicker);
            hBoxDueDate.setPadding(new Insets( 0,0,0,0));

            Label statusLabel = new Label("Status");
            TextField statusField = new TextField("Not Started");
            statusField.setDisable(true);

            VBox layout = new VBox(10);
            layout.setPadding(new Insets(10, 10, 10, 10));
            
            Button saveButton = new Button("Create");
            saveButton.setOnAction(new EventHandler<ActionEvent>() 
            {
                @Override   
                public void handle(ActionEvent event) 
                {
                    
                    String description = "";
                    try{
                        description = descriptionField.getText().trim();
                    }catch(NullPointerException e){
                      
                    }
                    int priorityNumber = Integer.parseInt(numberField.getText());
                    LocalDate localDueDate = dueDatePicker.getValue();
                    Date dueDate = new Date(0);
                    try{
                        Instant dueDateInstant = Instant.from(localDueDate.atStartOfDay(ZoneId.systemDefault()));
                        dueDate= Date.from(dueDateInstant);
                    }catch(NullPointerException e){
                        
                    }
                    if(checkDescripDup(description)){
                        descripUniqueError();
                    }        
                    else if(description.equals("") || description == null || dueDate.getTime() == 0)
                    {
                        noCreateError();
                    }
                    else{
                        Todo newTodo = new Todo(description, priorityNumber, dueDate, "Not Started");
                        todos.add(newTodo);
                        createConfirmPopUp();
                    }
            }});

            GridPane.setHalignment(descriptionLabel, HPos.RIGHT);
            GridPane.setHalignment(numberLabel, HPos.RIGHT);
            GridPane.setHalignment(dueDateLabel, HPos.RIGHT);
            GridPane.setHalignment(statusLabel, HPos.RIGHT);
            GridPane.setHalignment(descriptionField, HPos.LEFT);
            GridPane.setHalignment(numberField, HPos.LEFT);
            GridPane.setHalignment(hBoxDueDate, HPos.LEFT);
            GridPane.setHalignment(statusField, HPos.LEFT);
            GridPane.setHalignment(saveButton, HPos.RIGHT);

            gridpane.add(descriptionLabel, 0, 0);
            gridpane.add(descriptionField, 1, 0);
            gridpane.add(numberLabel, 0, 1);
            gridpane.add(numberField, 1, 1);
            gridpane.add(dueDateLabel, 0, 2);
            gridpane.add(hBoxDueDate, 1, 2);
            gridpane.add(statusLabel, 0, 3);
            gridpane.add(statusField, 1, 3);
            gridpane.add(saveButton, 1, 5);

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
            int ogPriority = todoTableView.getSelectionModel().getSelectedItem().getPriorityNum();
            Todo completedTodo = todoTableView.getSelectionModel().getSelectedItem();
            completedTodo.setDueDate(new Date(0));
            completedTodo.setStatus("");
            completedTodo.setPriorityNum(-1);
            completedTodos.add(completedTodo);
            todoTableView.getItems().remove(todoTableView.getSelectionModel().getSelectedItem());
            decrementAllPriority(ogPriority);
        }
    }
    
    private void displayPopUp(){
        
        
        GridPane displayGridPane = new GridPane();
        displayGridPane.setPadding(new Insets(0));
        final TableView<Todo> displayTableView = new TableView<>();
        displayTableView.setPrefWidth(750);
        displayTableView.setItems(todos);
        
        
        TableColumn<Todo, String> descripCol = new TableColumn("Description");
        TableColumn<Todo, Integer> priorityCol = new TableColumn("Priority");
        TableColumn<Todo, Date> dueDateCol = new TableColumn("Due Date");
        TableColumn<Todo, String> statusCol = new TableColumn("Status");
        TableColumn<Todo, Date> startDateCol = new TableColumn("Start Date");
        TableColumn<Todo,Date> finishDateCol = new TableColumn("Finish Date");
        // Add column headers to table
        displayTableView.getColumns().addAll(descripCol, priorityCol, dueDateCol, statusCol, startDateCol, finishDateCol);
        
        //set column width
        descripCol.setPrefWidth(displayTableView.getPrefWidth() / 3);
        priorityCol.setPrefWidth(displayTableView.getPrefWidth() * 2/15);
        dueDateCol.setPrefWidth(displayTableView.getPrefWidth() * 2/15);
        statusCol.setPrefWidth(displayTableView.getPrefWidth() * 2/15);
        startDateCol.setPrefWidth(displayTableView.getPrefWidth() * 2/15);
        finishDateCol.setPrefWidth(displayTableView.getPrefWidth() * 2/15);
        
        
        descripCol.setCellValueFactory(new PropertyValueFactory<Todo,String>("description"));
        priorityCol.setCellValueFactory(new PropertyValueFactory<Todo,Integer>("priorityNum"));
        dueDateCol.setCellValueFactory(new PropertyValueFactory<Todo, Date>("dueDate"));
        dueDateCol.setCellFactory(new Callback<TableColumn<Todo, Date>, TableCell<Todo, Date>>() 
        {
            @Override
            public TableCell<Todo, Date> call(TableColumn<Todo, Date> col) 
            {
              return new TableCell<Todo, Date>(){
                  protected void updateItem(Date duedate, boolean empty)
                  {
                      super.updateItem(duedate, empty);
                      if(empty)
                      {
                          setText(null);
                      }
                      else
                      {
                          String pattern = "MM/dd/yyyy";
                          SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                          setText(simpleDateFormat.format(duedate));
                      }
                  }
              };
            }
        });
        statusCol.setCellValueFactory(new PropertyValueFactory<Todo,String>("status"));
        startDateCol.setCellValueFactory(new PropertyValueFactory<Todo, Date>("startDate"));
        startDateCol.setCellFactory(new Callback<TableColumn<Todo, Date>, TableCell<Todo, Date>>() 
        {
            @Override
            public TableCell<Todo, Date> call(TableColumn<Todo, Date> col) 
            {
              return new TableCell<Todo, Date>(){
                  protected void updateItem(Date startDate, boolean empty)
                  {
                      super.updateItem(startDate, empty);
                      if(empty)
                      {
                          setText(null);
                      }
                      else
                      {
                          
                          String pattern = "MM/dd/yyyy";
                          
                          SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                          if(startDate.getTime() == 0){
                              setText(null);
                          }
                          else{
                            setText(simpleDateFormat.format(startDate));
                          }
                      }
                  }
              };
            }
        });
        finishDateCol.setCellValueFactory(new PropertyValueFactory<Todo, Date>("finishDate"));
        finishDateCol.setCellFactory(new Callback<TableColumn<Todo, Date>, TableCell<Todo, Date>>() 
        {
            @Override
            public TableCell<Todo, Date> call(TableColumn<Todo, Date> col) 
            {
              return new TableCell<Todo, Date>(){
                  protected void updateItem(Date finishDate, boolean empty)
                  {
                      super.updateItem(finishDate, empty);
                      if(empty)
                      {
                          setText(null);
                      }
                      else
                      {     
                          
                          String pattern = "MM/dd/yyyy";
                          SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                          if(finishDate.getTime() == 0){
                              setText(null);
                          }
                          else{
                              setText(simpleDateFormat.format(finishDate));
                          }
                      }
                  }
              };
            }
        });
        
        Label compLabel = new Label("Completed To-Do Items");
        
        obsCompletedTodos.clear();
        obsCompletedTodos.addAll(completedTodos);
        final TableView<Todo> dpCompleteTableView = new TableView<>();
        dpCompleteTableView.setPrefWidth(750);
        dpCompleteTableView.setItems(obsCompletedTodos);
        TableColumn<Todo, String> compDescripCol = new TableColumn("Description");
        TableColumn<Todo, Date> compStartDateCol = new TableColumn("Start Date");
        TableColumn<Todo,Date> compFinishDateCol = new TableColumn("Finish Date");
        dpCompleteTableView.getColumns().addAll(compDescripCol , compStartDateCol, compFinishDateCol);
        
        compDescripCol.setPrefWidth(displayTableView.getPrefWidth() / 2);
        compStartDateCol.setPrefWidth(displayTableView.getPrefWidth() * 1/4);
        compFinishDateCol.setPrefWidth(displayTableView.getPrefWidth() * 1/4);
        
        compDescripCol.setCellValueFactory(new PropertyValueFactory<Todo,String>("description"));
        compStartDateCol.setCellValueFactory(new PropertyValueFactory<Todo, Date>("startDate"));
        compStartDateCol.setCellFactory(new Callback<TableColumn<Todo, Date>, TableCell<Todo, Date>>() 
        {
            @Override
            public TableCell<Todo, Date> call(TableColumn<Todo, Date> col) 
            {
              return new TableCell<Todo, Date>(){
                  protected void updateItem(Date startDate, boolean empty)
                  {
                      super.updateItem(startDate, empty);
                      if(empty)
                      {
                          setText(null);
                      }
                      else
                      {
                          
                          String pattern = "MM/dd/yyyy";
                          
                          SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                           if(startDate.getTime() == 0){
                              setText(null);
                          }
                          {
                            setText(simpleDateFormat.format(startDate));
                           }
                      }
                  }
              };
            }
        });
        compFinishDateCol.setCellValueFactory(new PropertyValueFactory<Todo, Date>("finishDate"));
        compFinishDateCol.setCellFactory(new Callback<TableColumn<Todo, Date>, TableCell<Todo, Date>>() 
        {
            @Override
            public TableCell<Todo, Date> call(TableColumn<Todo, Date> col) 
            {
              return new TableCell<Todo, Date>(){
                  protected void updateItem(Date finishDate, boolean empty)
                  {
                      super.updateItem(finishDate, empty);
                      if(empty)
                      {
                          setText(null);
                      }
                      else
                      {     
                          
                          String pattern = "MM/dd/yyyy";
                          SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                          if(finishDate.getTime() == 0){
                              setText(null);
                          }
                          else{
                              setText(simpleDateFormat.format(finishDate));
                          }
                      }
                  }
              };
            }
        });
        
        displayGridPane.add(displayTableView, 0, 0);
        BorderPane dpBorder = new BorderPane();
        dpBorder.setTop(compLabel);
        dpBorder.setCenter(dpCompleteTableView);
        displayGridPane.add(dpBorder, 0, 1);
        Scene popupScene = new Scene(displayGridPane, 780,780);
        addPopUp.setScene(popupScene);
        addPopUp.show();
        
    }
    
    private GridPane printPopUp(){
            GridPane printgridpane = new GridPane();
            printgridpane.setPadding(new Insets(0));
            printgridpane.setHgap(5);
            printgridpane.setVgap(5);
            ColumnConstraints column1 = new ColumnConstraints(100);
            ColumnConstraints column2 = new ColumnConstraints(50, 150, 300);
            List<Todo> uniformTodos = new ArrayList<>();
            uniformTodos.clear();
            uniformTodos.addAll(todos);
            uniformTodos.addAll(completedTodos);
            
            
            
            Label headingLabel = new Label("To-Do List \n");
           
            Text text = new Text();      
     
            
 
            Text completed = new Text(); 
            
           //String final1= todos.toString();
        String final1= uniformTodos.toString();
          final1 = final1.substring(1, final1.length() - 1);
          final1 = final1.replace(",", "");

            text.setText(final1); 
            
           
            
           
            
            Label timestamp = new Label ("Timestamp:");
            Text timestamp1=new Text();
            timestamp1.setText(formattedDate);
           
            text.setStyle("-fx-font: normal bold 18px 'Arial' "); 
            headingLabel.setStyle("-fx-font: normal bold 20px 'Arial' "); 
            printgridpane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            
            printgridpane.setStyle("-fx-background-color: WHITE;"); 
            
          
            printgridpane.add(headingLabel, 0, 0);
            printgridpane.add(text,0,3);
           
            printgridpane.add(timestamp,0,9);
            printgridpane.add(timestamp1,0,10);
            
           
            
           
            
            
   
            
            return printgridpane;

      }

    private void noCreateError()
    {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error Alert");
        alert.setHeaderText("Cannot create task");
        alert.setContentText("At least one of the fields is not entered");
        alert.showAndWait();
    }
    
    private void priorityUniqueError()
    {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error Alert");
        alert.setHeaderText("Cannot edit task");
        alert.setContentText("Priority is not an integer");
        alert.showAndWait();
    }
    
    private void priorityRangeError()
    {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error Alert");
        alert.setContentText("Cannot edit task");
        alert.setContentText("Priority is not in range. Please submit integer within number of entries.");
        alert.showAndWait();
    }
    
    private void descripUniqueError()
    {
       Alert alert = new Alert(AlertType.ERROR);
       alert.setTitle("Error Alert");
       alert.setHeaderText("Cannot create/edit task");
       alert.setContentText("Description is not unique");
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
        descripField.setEditable(false);
        priorityField = new TextField();
        priorityField.setDisable(true);
        priorityField.setEditable(false);
        duePicker = new DatePicker();
        duePicker.setDisable(true);
        duePicker.setEditable(false);
        duePicker.getEditor().setEditable(false);
        statusComboBox = new ComboBox<>();
        statusComboBox.getItems().addAll("Not Started","In Progress", "Finished");
        statusComboBox.setValue("");
        statusComboBox.setDisable(true);
        statusComboBox.setEditable(false);
        startPicker = new DatePicker();
        startPicker.setDisable(true);
        startPicker.setVisible(false);
        startPicker.setEditable(false);
        startPicker.getEditor().setEditable(false);
        finishPicker = new DatePicker();
        finishPicker.setDisable(true);
        finishPicker.setVisible(false);
        finishPicker.setEditable(false);
        finishPicker.getEditor().setEditable(false);
        
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
                complete.setVisible(false);
                descripField.setDisable(false);
                descripField.setEditable(true);
                priorityField.clear();
                priorityField.setDisable(false);
                priorityField.setEditable(true);
                duePicker.setDisable(false);
                duePicker.setEditable(true);
                duePicker.getEditor().clear();
                statusComboBox.setDisable(false);
                statusComboBox.setEditable(true);
                startPicker.setDisable(false);
                startPicker.setEditable(true);
                startPicker.getEditor().clear();
                finishPicker.getEditor().clear();
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
        
        cancelEdit.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                cancelEdit.setVisible(false);
                apply.setVisible(false);
                edit.setVisible(true);
                complete.setVisible(true);
                delete.setVisible(true);
                descripField.setEditable(false);
                priorityField.setEditable(false);
                statusComboBox.setDisable(true);
                duePicker.setDisable(true);
                startLabel.setVisible(false);
                startPicker.setDisable(true);
                startPicker.setVisible(false);
                finishLabel.setVisible(false);
                finishPicker.setDisable(true);
                finishPicker.setVisible(false);
           
            }
            
        });
        
        statusComboBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>()
            {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) 
                {
                    int index = (Integer)newValue;
                    startLabel.setVisible(index == 1 || index == 2);
                    startLabel.setDisable(!(index == 1 || index == 2));
                    startPicker.setVisible(index == 1 || index == 2);
                    startPicker.setDisable(!(index == 1 || index == 2));
                    finishLabel.setVisible(index == 2);
                    finishLabel.setDisable(!(index == 2));
                    finishPicker.setVisible(index == 2);
                    finishPicker.setDisable(!(index == 2));
                }
            });
      
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
        
        addPopUp.initModality(Modality.APPLICATION_MODAL);
        addPopUp.initOwner(primaryStage);
        displayPopUp.initModality(Modality.APPLICATION_MODAL);
        displayPopUp.initOwner(primaryStage);
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
        displayAll = new Button("Display All");
        
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
        
        
        
         
        displayAll.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                displayPopUp();
            }
        });

        
        HBox hbox = new HBox(create, print, save, restore, restart, displayAll);
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
                descripField.setEditable(false);
                priorityField.setDisable(false);
                priorityField.setEditable(false);
                duePicker.setDisable(true);
                statusComboBox.setDisable(true);
                descripField.setText(observable.getValue().getDescription());
                priorityField.setText(""+observable.getValue().getPriorityNum());
                 String pattern = "MM/dd/yyyy";
                 SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                 duePicker.getEditor().setText(simpleDateFormat.format(observable.getValue().getDueDate()));
                 statusComboBox.setValue(observable.getValue().getStatus());
                 if(observable.getValue().getStatus().equals("In Progress") || observable.getValue().getStatus().equals("Finished")){
                     
                     startLabel.setVisible(true);
                     startPicker.getEditor().setText(simpleDateFormat.format(observable.getValue().getStartDate()));
                     startPicker.setVisible(true);
                     startPicker.setDisable(true);
   
                 }
                  if(observable.getValue().getStatus().equals("Finished")){
                     finishLabel.setVisible(true);
                     finishPicker.getEditor().setText(simpleDateFormat.format(observable.getValue().getStartDate()));
                     finishPicker.setVisible(true);
                     finishPicker.setDisable(true);
                   
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
                 descripField.setEditable(false);
                 descripField.setDisable(true);
                 priorityField.clear();
                 priorityField.setEditable(false);
                 priorityField.setDisable(true);
                 duePicker.getEditor().clear();
                 duePicker.setEditable(false);
                 duePicker.setDisable(true);
                 statusComboBox.getEditor().clear();
                 statusComboBox.setEditable(false);
                 statusComboBox.setDisable(true);
                 startLabel.setVisible(false);
                 startPicker.getEditor().clear();
                 startPicker.setEditable(false);
                 startPicker.setDisable(true);
                 startPicker.setVisible(false);
                 finishLabel.setVisible(false);
                 finishPicker.getEditor().clear();
                 finishPicker.setEditable(false);
                 finishPicker.setDisable(true);
                 finishPicker.setVisible(false);
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
        print.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event) {
                BorderPane root1 = new BorderPane();
                GridPane printgridpane = printPopUp();
                root1.setCenter(printgridpane); 
                Scene popupScene1 = new Scene(root1, 500, 500);
                addPopUp.setScene(popupScene1);
                
                addPopUp.show();
            }
        }); 

        create.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event) {
                BorderPane root = new BorderPane();
                GridPane gridpane = createPopUp();
                root.setCenter(gridpane); 
                Scene popupScene = new Scene(root, 300, 250);
                addPopUp.setScene(popupScene);
                addPopUp.show();
            }
        });  
       baseGridPane.add(leftGridPane, 0, 0);
       baseGridPane.add(rightGridPane, 1, 0);

       Scene scene = new Scene(baseGridPane, 800, 450);
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
