package com.amazonaws.lambda.http;

public class ArchiveProjectRequest {
	public String projectName;
	
	public ArchiveProjectRequest() {};
	
	public ArchiveProjectRequest(String projectName) {
		this.projectName = projectName;
	}
	
	public String toString() {
		return "ProjectName: " + this.projectName;
	}
}
