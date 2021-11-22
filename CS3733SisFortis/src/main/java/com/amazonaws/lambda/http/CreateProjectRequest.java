package com.amazonaws.lambda.http;

public class CreateProjectRequest {
	public String projectName;
	
	public String getProjectName() {return this.projectName;}
	
	public CreateProjectRequest(String projectName) {
		this.projectName = projectName;
	}
	
	public CreateProjectRequest() {
	}
	
	public String toString() {
		return "Requested to create project with name: " + this.projectName;
	}

}
