package com.amazonaws.lambda.http;

public class AddTeammateResponse {
	public String teammateName;
	public int statusCode;
	public String error;

	//If error
	public AddTeammateResponse(int statusCode, String errorMessage) {
		this.statusCode = statusCode;
		this.error = errorMessage;
		this.teammateName = "";
	}
	
	// If successful
	public AddTeammateResponse(String teammateName, int statusCode) {
		this.statusCode = statusCode;
		this.error = "";
		this.teammateName = teammateName;
	}
	
	public AddTeammateResponse() {};
	
	public String toString() {
		return "teammateName(" + this.teammateName + ")";
	}
	
}