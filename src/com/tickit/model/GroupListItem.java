package com.tickit.model;

public class GroupListItem {

	private int groupId;
	private int numTasks;
	private String groupName;
	private String[] groupTask;
	private String groupDesc;
	
	 public GroupListItem(){}
	 
	 public GroupListItem(int id, String name, String[] task){
	        this.groupId = id;
	        this.groupTask = task;
	        this.groupName = name;
	        this.numTasks = task.length;
	        if (task.length==0){
				 this.groupDesc="No tasks left";
			 }
			 else this.groupDesc = task[0];
	    }
	
	 public String getName(){
		 return this.groupName;
	 }
	 
	 public String[] getTask(){
		 return this.groupTask;
	 }
	 
	 public int getId() {
		 return this.groupId;
	 }
	 
	 public int getNum(){
		 return this.numTasks;
	 }
	 
	 public String getDesc() {
		 return this.groupDesc;
	 }
	 
	 public void setId(int id){
		 this.groupId = id;
	 }
	 
	 public void setName(String name){
		 this.groupName = name;
	 }
	 
	 public void setTasks(String[] task){
		 this.groupTask = task;
		 this.numTasks = task.length;
		 if (task.length==0){
			 this.groupDesc="No tasks left";
		 }
		 else this.groupDesc = task[0];
	 }
}
