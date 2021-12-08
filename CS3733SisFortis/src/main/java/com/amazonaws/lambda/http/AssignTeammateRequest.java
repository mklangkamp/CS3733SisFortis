package com.amazonaws.lambda.http;

import java.util.ArrayList;

import com.amazonaws.lambda.model.Project;
import com.amazonaws.lambda.model.Teammate;

public class AssignTeammateRequest {
	public String teammateName;
	public String idTask;
	public String idProject;
	
	public String getTeammateName() {return this.teammateName;}
	public String getTaskName() {return this.idTask;}
	public String geIdProject() {return this.idProject;}
	
	public AssignTeammateRequest(String idTask, String teammateName, String idProject) {
		this.idTask = idTask;
		this.teammateName = teammateName;
		this.idProject =idProject;
	}
	
	public AssignTeammateRequest() {
	}
	
	public String toString() {
		return "Requested to add teammate with name: " + this.teammateName;
	}

}
