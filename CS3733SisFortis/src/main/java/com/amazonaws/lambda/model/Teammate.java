package com.amazonaws.lambda.model;

import java.util.ArrayList;

public class Teammate {
	public String name;
	public ArrayList<Task> taskList;
	public Project project;
	
	public Teammate(String name) {
		this.name = name;
		//this.taskList = taskList;
		//this.project = project;
	}
	
	public Teammate(String name, ArrayList<Task> taskList) {
		this.name = name;
		this.taskList = taskList;
		//this.project = project;
	}
	
	
	public Teammate() {
		
	}
}
