package com.amazonaws.lambda.http;

public class AddTaskResponse {
	public String taskName;
	public int statusCode;
	public String error;

	//If error
	public AddTaskResponse(int statusCode, String errorMessage) {
		this.statusCode = statusCode;
		this.error = errorMessage;
		this.taskName = "";
	}
	
	// If successful
	public AddTaskResponse(String taskName, int statusCode) {
		this.statusCode = statusCode;
		this.error = "";
		this.taskName = taskName;
	}
	
	public String toString() {
		return "taskName(" + this.taskName + ")";
	}
	
}