package com.amazonaws.lambda.model;

import java.util.ArrayList;

import com.amazonaws.lambda.db.TeammateToTaskDAO;

public class Task {
	public String id;
	public String name;
	public String idParent;
	public boolean status;
	public String idProject;
	public ArrayList<String> assignedTeammates;
	public ArrayList<String> emptyArrayListOfString;
	public ArrayList<Task> subtasks;
	public TeammateToTaskDAO teammateToTaskDAO;
	
	public Task(String id, String name, boolean status, String idParent) {
		this.id = id;
		this.name = name;
		this.idParent = idParent;
		this.status = status;
//		this.assignedTeammates = assignedTeammates;
//		this.subtasks = subtasks;
	}
	
	public Task(String id, String name, boolean status, String idParent, String idProject) {
		this.id = id;
		this.name = name;
		this.idParent = idParent;
		this.status = status;
		this.idProject = idProject;
//		this.assignedTeammates = assignedTeammates;
//		this.subtasks = subtasks;
	}
	
	public ArrayList<String> getAssignedTeammates(String id, String idProject){
		
		try {
			assignedTeammates = teammateToTaskDAO.getAllTaskTeammates(id,idProject);
		}catch (Exception e) {
           return emptyArrayListOfString;
        }
		
		return assignedTeammates;
	}

	public Task(String id, String name, boolean status) {
		this.id = id;
		this.name = name;
		this.status = status;
//		this.assignedTeammates = assignedTeammates;
//		this.subtasks = subtasks;
	}
	
	public Task() {
		
	}
	
	public Task(String id, String name, boolean status, ArrayList<String> assignedTeammates) {
		this.id = id;
		this.name = name;
		this.assignedTeammates = assignedTeammates;
		this.status = status;
//		this.assignedTeammates = assignedTeammates;
//		this.subtasks = subtasks;
	}
	
}
