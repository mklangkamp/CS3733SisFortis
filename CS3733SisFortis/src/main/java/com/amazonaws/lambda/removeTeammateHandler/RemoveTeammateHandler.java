package com.amazonaws.lambda.removeTeammateHandler;

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

public class RemoveTeammateHandler implements RequestHandler<RemoveTeammateRequest, RemoveTeammateResponse> {

	LambdaLogger logger;
	
	private AmazonS3 s3 = null;
	
	TeammateDAO dao;
	TeammateToTaskDAO ttotdao;
	
    @Override
    public RemoveTeammateResponse handleRequest(RemoveTeammateRequest req, Context context) {
    	logger = context.getLogger();
    	dao = new TeammateDAO(logger);
    	ttotdao = new TeammateToTaskDAO(logger);
    	logger.log("Loading Java Lambda handler of RemoveTeammateHandler");
		logger.log(req.toString());
		
		logger.log("ttot");
		try {
			ttotdao.removeTeammate(req.projectName, req.teammateName);
		}
		catch(Exception e){
			System.out.println("Could not re-assign teammates to subtask.");
		}
		
		try {
			dao.removeTeammate(req.projectName, req.teammateName);
		}
		catch(Exception e){
			System.out.println("Could not remove teammate.");
		}
		

		
		RemoveTeammateResponse response = new RemoveTeammateResponse(req.teammateName, 200);
		
		return response;
    }
}