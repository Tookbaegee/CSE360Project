package CSE360Project.CSE360Project;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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
        String a= "Wed Dec 31";
         
        String pattern = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        
            if ((getPriorityNum()==-1)&&getStartDate().toString().contains(a)){
                String s= "Description: " +getDescription()+" ; "+ " Status: Completed  "+ getStatus();
            String total = intro+s+ "\n";
             return total;
                
            }
            else if(getStartDate().toString().contains(a))
            {
            String s= "Description: " +getDescription()+" ; Priority Number: "+getPriorityNum()+ " ; Due Date:  "+ sdf.format(dueDate)+" ; Status:  "+ getStatus();
            String total = intro+s+ "\n";
             return total;
            }
            else if (getPriorityNum()==-1){
                String s= "Description: " +getDescription()+ " ; Status: Completed  "  + getStatus()+" ; Start Date:  "+ sdf.format(getStartDate()) +" ; Final Date:  "+ sdf.format(getFinishDate()) + " \n";
            String total = intro+s+ "\n";
             return total;
                
            }
            else{
                 String s= "Description: " +getDescription()+" ; Priority Number: "+getPriorityNum()+ " ; Due Date:  "+ sdf.format(dueDate)+ " ; Status:  "+ getStatus()+" ; Start Date:  "+ sdf.format(getStartDate()) +" ; Final Date:  "+ sdf.format(getFinishDate())+ " \n";
            String total = intro+s+ "\n";
             return total;
            }
            
            
             
               
        }
        
        
            
            
             
}
        

      
