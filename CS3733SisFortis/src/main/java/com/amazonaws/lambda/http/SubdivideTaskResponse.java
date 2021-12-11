package com.amazonaws.lambda.http;

public class SubdivideTaskResponse {
	public String taskName;
	public int statusCode;
	public String error;

	//If error
	public SubdivideTaskResponse(int statusCode, String errorMessage) {
		this.statusCode = statusCode;
		this.error = errorMessage;
		this.taskName = "";
	}
	
	// If successful
	public SubdivideTaskResponse(String taskName, int statusCode) {
		this.statusCode = statusCode;
		this.error = "";
		this.taskName = taskName;
	}
	
	public String toString() {
		return "subtaskName(" + this.taskName + ")";
	}
}
