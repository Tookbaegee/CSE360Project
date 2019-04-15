package to.pkgdo.list.project.CSE360Project;

import java.util.Date;

public class Todo{
	private String description;
	private int priorityNum;
	private Date dueDate;
        private Date startDate;
	private int status;
	
	public Todo() {
		
	}
	
	public Todo(String description, int priorityNum, Date dueDate, int status) {
		this.description = description;
		this.setPriorityNum(priorityNum);
		this.setDueDate(dueDate);
		this.setStatus(status);
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
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

	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
        
        public void setStartDate(Date startDate){
            this.startDate = startDate;
        }
        public Date getStartDate(){
            return startDate;
        }
	
}
