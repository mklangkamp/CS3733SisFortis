package com.amazonaws.lambda.http;

public class UnassignTeammateResponse {
		public String teammateName;
		public int statusCode;
		public String error;

		//If error
		public UnassignTeammateResponse(int statusCode, String errorMessage) {
			this.statusCode = statusCode;
			this.error = errorMessage;
			this.teammateName = "";
		}
		
		// If successful
		public UnassignTeammateResponse(String teammateName, int statusCode) {
			this.statusCode = statusCode;
			this.error = "";
			this.teammateName = teammateName;
		}
		
		public UnassignTeammateResponse() {};
		
		public String toString() {
			return "teammateName(" + this.teammateName + ")";
		}
		
}
