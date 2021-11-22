package com.amazonaws.lambda.model;

import java.util.ArrayList;

public class Project {
	String name;
	ArrayList<Teammate> teammates;
	ArrayList<Task> tasks;
	Boolean archived;
	
	
	public Project(String name) {
		this.name = name;
		this.archived = false;
	}
	

}
