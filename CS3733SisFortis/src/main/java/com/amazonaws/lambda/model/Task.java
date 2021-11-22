package com.amazonaws.lambda.model;

import java.util.ArrayList;

public class Task {
	String name;
	String id;
	boolean status;
	ArrayList<Teammate> assignedTeammates;
	ArrayList<Task> subtasks;
	
	public Task(String name, String id, boolean status, ArrayList<Teammate> assignedTeammates, ArrayList<Task> subtasks) {
		this.name = name;
		this.id = id;
		this.status = status;
		this.assignedTeammates = assignedTeammates;
		this.subtasks = subtasks;
	}
	
}
