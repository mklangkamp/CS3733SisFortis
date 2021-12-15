package com.amazonaws.lambda.unassignTeammateHandler;

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

public class UnassignTeammateHandler implements RequestHandler<UnassignTeammateRequest, UnassignTeammateResponse> {

	LambdaLogger logger;
	
	private AmazonS3 s3 = null;
	
	TeammateToTaskDAO dao;
	
    @Override
    public UnassignTeammateResponse handleRequest(UnassignTeammateRequest req, Context context) {
    	logger = context.getLogger();
    	dao = new TeammateToTaskDAO(logger);
    	logger.log("Loading Java Lambda handler of AssignTeammateToTaskHandler");
		logger.log(req.toString());
		
		boolean successfullyUnassigned = false;
		try {
			successfullyUnassigned = dao.unassignTeammate(req.idTask, req.teammateName, req.idProject);
		}
		catch(Exception e){
			
			System.out.println("Could not un-assign teammate to task.");
		}
		
		UnassignTeammateResponse response = new UnassignTeammateResponse();
		
		if(successfullyUnassigned) {
			response = new UnassignTeammateResponse(req.teammateName, 200); //need to change
		}else {
			logger.log("Could not un-assign Teammate " + req.teammateName + " to Task with id number " + req.idTask + ".");
			response = new UnassignTeammateResponse(200, "Teammate " + req.teammateName + " is already un-assigned to this Task.");
		}
		return response;
    }
}

