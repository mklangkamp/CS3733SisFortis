package com.amazonaws.lambda.http;

import com.amazonaws.lambda.model.Task;

public class MarkTaskCompleteResponse {
	public String taskName;
	public int statusCode;
	public String error;
	
	public MarkTaskCompleteResponse(int statusCode, String errorMessage) {
		this.statusCode = statusCode;
		this.error = errorMessage;
		this.taskName = "";
	}
	
	public MarkTaskCompleteResponse(String taskName, int statusCode) {
		this.statusCode = statusCode;
		this.error = "";
		this.taskName = taskName;
	}
	
	
}

//public class AddTaskResponse {
//	public String taskName;
//	public int statusCode;
//	public String error;
//
//	//If error
//	public AddTaskResponse(int statusCode, String errorMessage) {
//		this.statusCode = statusCode;
//		this.error = errorMessage;
//		this.taskName = "";
//	}
//	
//	// If successful
//	public AddTaskResponse(String taskName, int statusCode) {
//		this.statusCode = statusCode;
//		this.error = "";
//		this.taskName = taskName;
//	}
//	
//	public String toString() {
//		return "taskName(" + this.taskName + ")";
//	}
//	
//}