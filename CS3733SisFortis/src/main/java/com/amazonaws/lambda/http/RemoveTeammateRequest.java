package com.amazonaws.lambda.http;

import java.util.ArrayList;

import com.amazonaws.lambda.model.Project;
import com.amazonaws.lambda.model.Teammate;

public class RemoveTeammateRequest {
	public Teammate teammate;
	public Project project;
	
	public String getTeammateName() {return this.teammate.name;}
	public String getProjectName() {return this.project.name;}
	
	public RemoveTeammateRequest(Project project, Teammate teammate) {
		this.project = project;
		this.teammate = teammate;
	}
	
	public RemoveTeammateRequest() {
	}
	
	public String toString() {
		return "Requested to remove teammate with name: " + this.teammate.name;
	}

}
