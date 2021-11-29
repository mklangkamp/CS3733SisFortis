package com.amazonaws.lambda.http;

import java.util.ArrayList;

import com.amazonaws.lambda.model.Project;

public class ListProjectResponse {
	public ArrayList<Project> projects;
	public int statusCode;
	public String error;

	
	//If error
	public ListProjectResponse(int statusCode, String errorMessage) {
		this.statusCode = statusCode;
		this.error = errorMessage;
	}
	
	// If successful
	public ListProjectResponse(ArrayList<Project> projects, int statusCode) {
		this.statusCode = statusCode;
		this.error = "";
		this.projects = projects;

	}
	
	public String toString() {
		String projectList = "test";
//		projects.forEach((p) -> projectList.concat("," + p.name));
//		projects.forEach((p) -> System.out.println("," + p.name));
//		System.out.println("Test".concat("123"));
		System.out.println(projectList);
//		return "projects([" + projectList + "])";
		return projectList;
	}
	
}
