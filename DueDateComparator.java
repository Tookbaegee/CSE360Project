/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CSE360Project.CSE360Project;

import java.util.Comparator;

/**
 *
 * @author top12
 */
public class DueDateComparator implements Comparator<Todo> {

    @Override
    public int compare(Todo todo1, Todo todo2) {
        return todo1.getDueDate().compareTo(todo2.getDueDate());
    }
    
}
