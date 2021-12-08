package com.amazonaws.lamda.assignTeammateHandler;

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
import com.amazonaws.lambda.db.TeammateToTaskDAO;
import com.amazonaws.lambda.http.*;
import com.amazonaws.lambda.model.Project;

public class AssignTeammateHandler implements RequestHandler<AssignTeammateRequest, AssignTeammateResponse> {

	LambdaLogger logger;
	
	private AmazonS3 s3 = null;
	
	TeammateToTaskDAO dao;
	
    @Override
    public AssignTeammateResponse handleRequest(AssignTeammateRequest req, Context context) {
    	logger = context.getLogger();
    	dao = new TeammateToTaskDAO(logger);
    	logger.log("Loading Java Lambda handler of AssignTeammateToTaskHandler");
		logger.log(req.toString());
		
		
		boolean successfullyAdded = false;
		try {
			successfullyAdded = dao.assignTeammate(req.idTask, req.teammateName, req.idProject);
		}
		catch(Exception e){
			
			System.out.println("Could not assign teammate to task.");
		}
		
		AssignTeammateResponse response = new AssignTeammateResponse();
		
		if(successfullyAdded) {
			response = new AssignTeammateResponse(req.teammateName, 200); //need to change
		}else {
			logger.log("Could not assign Teammate " + req.teammateName + " to Task with id number " + req.idTask + ".");
			response = new AssignTeammateResponse(200, "Teammate " + req.teammateName + " is already assigned to this Task.");
		}
		
		return response;
    }
}
