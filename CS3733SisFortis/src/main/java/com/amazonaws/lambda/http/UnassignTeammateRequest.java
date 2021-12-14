package com.amazonaws.lambda.http;

public class UnassignTeammateRequest {
	public String teammateName;
	public String idTask;
	public String idProject;
	
	public String getTeammateName() {return this.teammateName;}
	public String getTaskName() {return this.idTask;}
	public String geIdProject() {return this.idProject;}
	
	public UnassignTeammateRequest(String idTask, String teammateName, String idProject) {
		this.idTask = idTask;
		this.teammateName = teammateName;
		this.idProject = idProject;
	}
	
	public UnassignTeammateRequest() {
	}
	
	public String toString() {
		return "Requested to unassign teammate with name: " + this.teammateName;
	}
}
