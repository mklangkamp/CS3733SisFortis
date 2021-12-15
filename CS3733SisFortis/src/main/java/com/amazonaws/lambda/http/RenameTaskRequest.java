package com.amazonaws.lambda.http;

import com.amazonaws.lambda.model.Project;
import com.amazonaws.lambda.model.Task;

public class RenameTaskRequest {
	public Task task;
	public String newName;
	
	public String getTaskName() {return this.task.name;}
	public String getProjectName() {return this.task.idProject;}
	
	public RenameTaskRequest(Task task, String newName) {
		this.task = task;
		this.newName = newName;
	}
	
	public RenameTaskRequest() {
	}
	
	public String toString() {
		return "Requested to add task with name: " + this.task.name;
	}

}
