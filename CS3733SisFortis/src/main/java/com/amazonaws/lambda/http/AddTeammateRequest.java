package com.amazonaws.lambda.http;

import java.util.ArrayList;

import com.amazonaws.lambda.model.Project;
import com.amazonaws.lambda.model.Teammate;

public class AddTeammateRequest {
	public String teammateName;
	public String projectName;
	
	public String getTeammateName() {return this.teammateName;}
	public String getProjectName() {return this.projectName;}
	
	public AddTeammateRequest(String projectName, String teammateName) {
		this.projectName = projectName;
		this.teammateName = teammateName;
	}
	
	public AddTeammateRequest() {
	}
	
	public String toString() {
		return "Requested to add teammate with name: " + this.teammateName;
	}

}
