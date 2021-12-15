package com.amazonaws.lambda.http;

import com.amazonaws.lambda.model.Task;

public class RenameTaskResponse {
	public String taskName;
	public int statusCode;
	public String error;

	//If error
	public RenameTaskResponse(int statusCode, String errorMessage) {
		this.statusCode = statusCode;
		this.error = errorMessage;
	}
	
	// If successful
	public RenameTaskResponse(String taskName, int statusCode) {
		this.statusCode = statusCode;
		this.error = "";
		this.taskName = taskName;
	}
	
}
