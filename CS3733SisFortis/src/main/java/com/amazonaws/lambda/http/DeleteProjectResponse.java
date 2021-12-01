package com.amazonaws.lambda.http;

public class DeleteProjectResponse {
	public String projectName;
	public int statusCode;
	public String error;

	
	//If error
	public DeleteProjectResponse(int statusCode, String errorMessage) {
		this.statusCode = statusCode;
		this.error = errorMessage;
		this.projectName = "";
	}
	
	// If successful
	public DeleteProjectResponse(String projectName, int statusCode) {
		this.statusCode = statusCode;
		this.error = "";
		this.projectName = projectName;
	}
	
	
	public String toString() {
		return "projectName(" + this.projectName + ")";
	}
}
