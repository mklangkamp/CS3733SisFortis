package com.amazonaws.lambda.http;

import java.util.ArrayList;

import com.amazonaws.lambda.model.Project;
import com.amazonaws.lambda.model.Teammate;

public class AddTeammateRequest {
	public Teammate teammate;
	public Project project;
	
	public String getTeammateName() {return this.teammate.name;}
	public String getProjectName() {return this.project.name;}
	
	public AddTeammateRequest(Project project, Teammate teammate) {
		this.project = project;
		this.teammate = teammate;
	}
	
	public AddTeammateRequest() {
	}
	
	public String toString() {
		return "Requested to add task with name: " + this.teammate.name;
	}

}
