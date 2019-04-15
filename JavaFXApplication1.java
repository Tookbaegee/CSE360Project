/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
/**
 *
 * @author khyati
 */

//status dropdown box
public class JavaFXApplication1 extends Application {
    
    Stage window;
    Scene scene;
    Button button;
    
    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        window.setTitle("Status");
        

        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll("Status","Not Started","In progress","Finished");
        choiceBox.setValue("Status");
        

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.getChildren().addAll(choiceBox);

        scene = new Scene(layout, 300, 250);
        window.setScene(scene);
        window.show();
    }

    //To get the value of the selected item
    private void getChoice(ChoiceBox<String> choiceBox){
        String choice = choiceBox.getValue();
        
    }


        
                
        
     

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
