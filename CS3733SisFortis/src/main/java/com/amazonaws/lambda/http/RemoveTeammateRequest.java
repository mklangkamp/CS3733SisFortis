package com.amazonaws.lambda.http;

import java.util.ArrayList;

import com.amazonaws.lambda.model.Project;
import com.amazonaws.lambda.model.Teammate;

public class RemoveTeammateRequest {
	public String teammateName;
	public String projectName;
	
	public String getTeammateName() {return this.teammateName;}
	public String getProjectName() {return this.projectName;}
	
	public RemoveTeammateRequest(String projectName, String teammateName) {
		this.projectName = projectName;
		this.teammateName = teammateName;
	}
	
	public RemoveTeammateRequest() {
	}
	
	public String toString() {
		return "Requested to remove teammate with name: " + this.teammateName;
	}

}
