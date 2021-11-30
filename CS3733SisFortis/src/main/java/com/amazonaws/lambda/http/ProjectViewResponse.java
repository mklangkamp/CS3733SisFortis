package com.amazonaws.lambda.http;

import com.amazonaws.lambda.model.Project;

public class ProjectViewResponse {
	public Project project;
	public int statusCode;
	public String error;

	
	//If error
	public ProjectViewResponse(int statusCode, String errorMessage) {
		this.statusCode = statusCode;
		this.error = errorMessage;
		this.project = null;
	}
	
	// If successful
	public ProjectViewResponse(Project project, int statusCode) {
		this.statusCode = statusCode;
		this.error = "";
		this.project = project;
	}
	
	
	public String toString() {
		return "projectName(" + this.project.name + ")";
	}
	
}