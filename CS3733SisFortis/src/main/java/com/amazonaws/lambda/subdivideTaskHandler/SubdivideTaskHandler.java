package com.amazonaws.lambda.subdivideTaskHandler;

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
import com.amazonaws.lambda.db.TaskDAO;
import com.amazonaws.lambda.db.TeammateToTaskDAO;
import com.amazonaws.lambda.http.*;
import com.amazonaws.lambda.model.Project;

public class SubdivideTaskHandler implements RequestHandler<SubdivideTaskRequest,SubdivideTaskResponse> {

	LambdaLogger logger;
	
	private AmazonS3 s3 = null;
	
	TaskDAO taskdao;
	TeammateToTaskDAO ttotdao;
	
    @Override
    public SubdivideTaskResponse handleRequest(SubdivideTaskRequest req, Context context) {
    	logger = context.getLogger();
    	taskdao = new TaskDAO(logger);
    	logger.log("Loading Java Lambda handler of SubdivideTaskHandler");
		logger.log(req.toString());
		
		try {
			taskdao.addSubtask(req.project, req.task);
		}
		catch(Exception e){
			System.out.println("Could not add subtask.");
		}
		
		logger.log("ttot");
		try {
			ttotdao.shiftTeammates(req.project, req.task);
		}
		catch(Exception e){
			System.out.println("Could not add subtask.");
		}
		
		
		SubdivideTaskResponse response = new SubdivideTaskResponse(req.task.name, 200);
		
		return response;
    }
}