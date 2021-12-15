package com.amazonaws.lambda.renameTaskHandler;

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
import com.amazonaws.lambda.http.*;
import com.amazonaws.lambda.model.Project;

public class RenameTaskHandler implements RequestHandler<RenameTaskRequest, RenameTaskResponse> {

	LambdaLogger logger;
	
	TaskDAO dao;
	
    @Override
    public RenameTaskResponse handleRequest(RenameTaskRequest req, Context context) {
    	logger = context.getLogger();
    	dao = new TaskDAO(logger);
    	logger.log("Loading Java Lambda handler of AddTaskHandler");
		logger.log(req.toString());
		
		
		
		try {
			dao.renameTask(req.task, req.newName);
		}
		catch(Exception e){
			System.out.println("Could not rename task.");
		}
		
		RenameTaskResponse response = new RenameTaskResponse(req.newName, 200);
		
		return response;
    }
}