package com.amazonaws.lambda.http;

public class AssignTeammateResponse {
	public String teammateName;
	public int statusCode;
	public String error;

	//If error
	public AssignTeammateResponse(int statusCode, String errorMessage) {
		this.statusCode = statusCode;
		this.error = errorMessage;
		this.teammateName = "";
	}
	
	// If successful
	public AssignTeammateResponse(String teammateName, int statusCode) {
		this.statusCode = statusCode;
		this.error = "";
		this.teammateName = teammateName;
	}
	
	public AssignTeammateResponse() {};
	
	public String toString() {
		return "teammateName(" + this.teammateName + ")";
	}
	
}
