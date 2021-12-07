package com.amazonaws.lambda.http;

import java.util.ArrayList;

import com.amazonaws.lambda.model.Project;
import com.amazonaws.lambda.model.Task;

public class SubdivideTaskRequest {
	public Task task;
	public Project project;
	
	public String getTaskName() {return this.task.name;}
	public String getProjectName() {return this.project.name;}
	public String getParentTaskName() {return this.task.parentName;}
	
	public SubdivideTaskRequest(Project project, Task task) {
		this.project = project;
		this.task = task;
	}
	
	public SubdivideTaskRequest() {
	}
	
	public String toString() {
		return "Requested to add task with name: " + this.task.name;
	}

}

