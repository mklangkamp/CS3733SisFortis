package com.amazonaws.lambda.http;

public class ArchiveProjectResponse {
	public String projectName;
	public int statusCode;
	public String error;
	
	public ArchiveProjectResponse(int statusCode, String errorMessage) {
		this.statusCode = statusCode;
		this.error = errorMessage;
		this.projectName = "";
	}
	
	public ArchiveProjectResponse(String projectName, int statusCode) {
		this.statusCode = statusCode;
		this.error = "";
		this.projectName = projectName;
	}
	
	
}