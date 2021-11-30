package com.amazonaws.lambda.http;

public class ProjectViewRequest {
	public String projectName;
	
	public String getProjectName() {return this.projectName;}
	
	public ProjectViewRequest(String projectName) {
		this.projectName = projectName;
	}
	
	public ProjectViewRequest() {
	}
	
	public String toString() {
		return "Requested to get project with name: " + this.projectName;
	}

}
