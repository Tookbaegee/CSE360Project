/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package CSE360Project;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author top12
 */
public class BinaryEditor {
    public static final String dir = System.getProperty("user.dir");
    // dir = FileSystems.getDefault().getPath(".").toAbsolutePath();
    public static String filePath = dir + "\\todolist.bin";
    public static String completedFilePath = dir + "\\completedlist.bin";
    public static FileOutputStream fos;
    public static ObjectOutputStream writer;
    public static FileInputStream fis;
    public static ObjectInputStream reader;
    
    public static void resetBin(){
        try {
            fos = new FileOutputStream(filePath);
            writer = new ObjectOutputStream(fos);
            List<Todo> todoList = new ArrayList<Todo>();
            writer.writeObject(todoList);
                    
        }
        catch (IOException ex) {
            ex.getStackTrace();
        }
    }
    public static void resetCompletedBin(){
        try {
            fos = new FileOutputStream(completedFilePath);
            writer = new ObjectOutputStream(fos);
            List<Todo> todoList = new ArrayList<Todo>();
            writer.writeObject(todoList);
                    
        }
        catch (IOException ex) {
            ex.getStackTrace();
        }
    }
    
    public static List<Todo> readTodo(){
        List<Todo> todoList = new ArrayList<Todo>();
        try{
            fis = new FileInputStream(filePath);
            reader = new ObjectInputStream(fis);
            todoList = (List<Todo>) reader.readObject();
            
            
        }
        catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BinaryEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sortByPriority(todoList);
    }
    
    public static void writeTodo(List<Todo> todoList){
        if(!todoList.isEmpty()) {
            try {
                fos = new FileOutputStream(filePath);
                writer = new ObjectOutputStream(fos);
                writer.writeObject(todoList);

            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
     public static List<Todo> readCompletedTodo(){
        List<Todo> todoList = new ArrayList<Todo>();
        try{
            fis = new FileInputStream(completedFilePath);
            reader = new ObjectInputStream(fis);
            todoList = (List<Todo>) reader.readObject();
          
        }
        catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BinaryEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sortByPriority(todoList);
    }
    
    public static void writeCompletedTodo(List<Todo> todoList){
        if(!todoList.isEmpty()) {
            try {
                fos = new FileOutputStream(completedFilePath);
                writer = new ObjectOutputStream(fos);
                writer.writeObject(todoList);

            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    /*
    choice: 1 - by Description
            2 - by Priority#
            3 - by DueDate
            4 - by State (If same state, by description)
    */
    public static List<Todo> sortByPriority(List<Todo> todoList){
      
        todoList.sort(new PriorityNumComparator());
       
        return todoList;
    }
    
    
    
    
    
   
}
