package com.tickit.model;

public class CardListItem {
	private String title;
    private String duedate;
    private int id;
    private String[] done;
    private int donecount = 0;
    // boolean to set visiblity of the counter
     
    public CardListItem(){}
 
    public CardListItem(int id, String title, String duedate, String[] done){
        this.title = title;
        this.id = id;
        this.duedate = duedate;
        this.done = done;
        this.donecount = done.length;
    }
     

     
    public String getTitle(){
        return this.title;
    }
    
    public String getDueDate(){
    	return this.duedate;
    }
    
    public int getId(){
    	return this.id;
    }
   
    
    public String getDone(){
    	return this.done[0];
    }
     
    public int getDoneCount(){
    	
        return this.donecount-1;
    }

    public void setTitle(String title){
        this.title = title;
    }
     

    public void setId(int id){
    	this.id = id;
    }
    
    public void setDueDate(String duedate){
    	this.duedate = duedate;
    }
    
    public void setDone(String[] done){
    	this.done = done;
    	this.donecount = done.length;
    }
}
