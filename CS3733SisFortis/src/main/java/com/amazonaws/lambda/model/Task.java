package com.amazonaws.lambda.model;

import java.util.ArrayList;

public class Task {
	public String id;
	public String name;
	public boolean status;
	public ArrayList<Teammate> assignedTeammates;
	public ArrayList<Task> subtasks;
	
	public Task(String id, String name) {
		this.id = id;
		this.name = name;
//		this.status = status;
//		this.assignedTeammates = assignedTeammates;
//		this.subtasks = subtasks;
	}
	
}
