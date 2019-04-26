package CSE360Project.CSE360Project;

import java.io.Serializable;
import java.util.Date;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Todo implements Serializable{
	private String description;
	private int priorityNum;
	private Date dueDate;
        private Date startDate;
        private Date finishDate;
	private String status;
	
	public Todo() {
		
	}
	
	public Todo(String description, int priorityNum, Date dueDate, String status) {
		this.setDescription(description);
		this.setPriorityNum(priorityNum);
		this.setDueDate(dueDate);
		this.setStatus(status);
                this.setStartDate(new Date(0));
                this.setFinishDate(new Date(0));
	}
        
        public Todo(String description, int priorityNum, Date dueDate, String status, Date startDate) {
		this.setDescription(description);
		this.setPriorityNum(priorityNum);
		this.setDueDate(dueDate);
		this.setStatus(status);
                this.setStartDate(startDate);
                this.setFinishDate(new Date(0));
	}
            public Todo(String description, int priorityNum, Date dueDate, String status, Date startDate, Date finishDate) {
		this.setDescription(description);
		this.setPriorityNum(priorityNum);
		this.setDueDate(dueDate);
		this.setStatus(status);
                this.setStartDate(startDate);
                this.setFinishDate(finishDate);
	}
        
      
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return this.description;
	}

     
	public int getPriorityNum() {
		return priorityNum;
	}

	public void setPriorityNum(int priorityNum) {
		this.priorityNum = priorityNum;
	}
        

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
        
        public void setStartDate(Date startDate){
            this.startDate = startDate;
        }
        public Date getStartDate(){
            return startDate;
        }
        
        public void setFinishDate(Date finishDate){
            this.finishDate = finishDate;
        }
        
        public Date getFinishDate(){
            return finishDate;
        }
        
        public void incrementPriority(){
            this.priorityNum++;
        }
        public void decrementPriority(){
            this.priorityNum--;
        }
	
       public String toString(){
            
        String intro="Task: \n";
         
               
           
            String s= "Description: " +getDescription()+" ; "+ "Priority Number: "+getPriorityNum()+"; Status:  "+ getStatus()+";   Start Date:  "+getStartDate()+"\n                  " "; Final Date:  "+getFinishDate()+ " \n";
            String total = intro+s+ "\n";
            
            
            
             
                return total;
        }
        

        
}
