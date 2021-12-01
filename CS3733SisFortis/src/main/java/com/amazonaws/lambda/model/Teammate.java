package com.amazonaws.lambda.model;

import java.util.ArrayList;

public class Teammate {
	String name;
	ArrayList<Task> taskList;
	
	public Teammate(String name, ArrayList<Task> taskList) {
		this.name = name;
		this.taskList = taskList;
	}
	
	
	public Teammate() {
		
	}
}
