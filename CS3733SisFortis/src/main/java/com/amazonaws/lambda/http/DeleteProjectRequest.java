package com.amazonaws.lambda.http;

public class DeleteProjectRequest {
	public String projectName;
	
	public String getProjectName() {return this.projectName;}
	
	public DeleteProjectRequest(String projectName) {
		this.projectName = projectName;
	}
	
	public DeleteProjectRequest() {
	}
	
	public String toString() {
		return "Requested to delete project with name: " + this.projectName;
	}

}
