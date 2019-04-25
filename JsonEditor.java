/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package to.pkgdo.list.project.CSE360Project;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author top12
 */
public class JsonEditor {
    public static final String dir = System.getProperty("user.dir");
    public static String filePath = "D:\\Document\\2019 Spring\\CSE 360\\To-do List Project\\todolist.json";
    public static ObjectMapper mapper = new ObjectMapper();
    public static FileWriter writer;
    public static FileReader reader;
    
    public static void resetJson(){
        try {
            writer = new FileWriter(filePath);
            List<Todo> todoList = new ArrayList<Todo>();
            mapper.writerWithDefaultPrettyPrinter().writeValue(writer, todoList);
                    
        }catch(JsonGenerationException e){
            e.printStackTrace();
        }catch(JsonMappingException e){
            e.printStackTrace();
        }
        catch (IOException ex) {
            Logger.getLogger(JsonEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static List<Todo> readTodo(){
        List<Todo> todoList = new ArrayList<Todo>();
        try{
            reader = new FileReader(filePath);
            todoList = mapper.readValue(reader, mapper.getTypeFactory().constructCollectionType(List.class, Todo.class));
            
            
        }
        catch (IOException ex) {
            Logger.getLogger(JsonEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return todoList;
    }
    
    public static void writeTodo(List<Todo> todoList){
        if(!todoList.isEmpty()) {
            try {
                writer = new FileWriter(filePath);
                mapper.writerWithDefaultPrettyPrinter().writeValue(writer, todoList);
                
            }catch(JsonGenerationException e){
                e.printStackTrace();
            }catch(JsonMappingException e){
                e.printStackTrace();
            }
            catch (IOException ex) {
                Logger.getLogger(JsonEditor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /*
    choice: 1 - by Description
            2 - by Priority#
            3 - by DueDate
            4 - by State (If same state, by description)
    */
    public static List<Todo> sortByAttribute(List<Todo> todoList, int choice){
        switch(choice){
            case 1: 
                todoList.sort(new DescriptionComparator());
                break;
            case 2:
                todoList.sort(new PriorityNumComparator());
                break;
            case 3: 
                todoList.sort(new DueDateComparator());
                break;
            case 4:
                todoList.sort(new StateComparator());
                break;
               
        }
        return todoList;
    }
    
    
    
    
    
   
}
