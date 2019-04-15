/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package to.pkgdo.list.project.CSE360Project;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author top12
 */
public class JsonEditor {
    public static String filePath = "todolist.json";
    public static ObjectMapper mapper = new ObjectMapper();
    public File todojson;
    
    public static void resetJson(){
        List<Todo> todoList = new ArrayList<Todo>();
    }
    
    
   
}
