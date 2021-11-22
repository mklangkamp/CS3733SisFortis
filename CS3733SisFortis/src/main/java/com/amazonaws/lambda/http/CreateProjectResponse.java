package com.amazonaws.lambda.http;

public class CreateProjectResponse {
	public String projectName;
	public int statusCode;
	public String error;

	
	//If error
	public CreateProjectResponse(int statusCode, String errorMessage) {
		this.statusCode = statusCode;
		this.error = errorMessage;
		this.projectName = "";
	}
	
	// If successful
	public CreateProjectResponse(String projectName, int statusCode) {
		this.statusCode = statusCode;
		this.error = "";
		this.projectName = projectName;
	}
	
	
	public String toString() {
		return "projectName(" + this.projectName + ")";
	}
	
}