package com.amazonaws.lambda.http;

public class RemoveTeammateResponse {
	public String teammateName;
	public int statusCode;
	public String error;

	//If error
	public RemoveTeammateResponse(int statusCode, String errorMessage) {
		this.statusCode = statusCode;
		this.error = errorMessage;
		this.teammateName = "";
	}
	
	// If successful
	public RemoveTeammateResponse(String teammateName, int statusCode) {
		this.statusCode = statusCode;
		this.error = "";
		this.teammateName = teammateName;
	}
	
	public String toString() {
		return "teammateName(" + this.teammateName + ")";
	}
	
}