package com.amazonaws.lambda.addTeammateHandler;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.json.Jackson;
import com.fasterxml.jackson.databind.JsonNode;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.lambda.db.TeammateDAO;
import com.amazonaws.lambda.http.*;
import com.amazonaws.lambda.model.Project;

public class AddTeammateHandler implements RequestHandler<AddTeammateRequest, AddTeammateResponse> {

	LambdaLogger logger;
	
	private AmazonS3 s3 = null;
	
	TeammateDAO dao;
	
    @Override
    public AddTeammateResponse handleRequest(AddTeammateRequest req, Context context) {
    	logger = context.getLogger();
    	dao = new TeammateDAO(logger);
    	logger.log("Loading Java Lambda handler of AddTeammateHandler");
		logger.log(req.toString());
		
		
		boolean successfullyAdded = false;
		try {
			successfullyAdded = dao.addTeammate(req.projectName, req.teammateName);
		}
		catch(Exception e){
			
			System.out.println("Could not add teammate.");
		}
		
		
		AddTeammateResponse response = new AddTeammateResponse();
		
		if(successfullyAdded) {
			response = new AddTeammateResponse(req.teammateName, 200);
		}else {
			logger.log("Teammate " + req.teammateName + " already exists in project.");
			response = new AddTeammateResponse(200, "Teammate " + req.teammateName + " already exists in project.");
		}
		
		return response;
    }
}