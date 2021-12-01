package com.amazonaws.lambda.model;

import java.util.ArrayList;

public class Project {
	public String name;
	public ArrayList<Teammate> teammates;
	public ArrayList<Task> tasks;
	public Boolean archived;
	
	
	public Project(String name) {
		this.name = name;
		this.archived = false;
	}
	
	public Project() {
		
	}
	

}
