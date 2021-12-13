package com.amazonaws.lambda.http;

public class MarkTaskCompleteRequest {
	public String taskName;
	public String projectName;
	
	public MarkTaskCompleteRequest() {};
	
	public MarkTaskCompleteRequest(String taskName, String projectName) {
		this.taskName = taskName;
		this.projectName = projectName;
	}
	
	public String toString() {
		return "TaskName: " + this.taskName + " ProjectName: " + this.projectName;
	}
}
