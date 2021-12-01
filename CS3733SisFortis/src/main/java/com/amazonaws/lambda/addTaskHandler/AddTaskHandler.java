package com.amazonaws.lambda.addTaskHandler;

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

public class AddTaskHandler implements RequestHandler<AddTaskRequest, AddTaskResponse> {

	LambdaLogger logger;
	
	private AmazonS3 s3 = null;
	
	TaskDAO dao;
	
    @Override
    public AddTaskResponse handleRequest(AddTaskRequest req, Context context) {
    	logger = context.getLogger();
    	dao = new TaskDAO(logger);
    	logger.log("Loading Java Lambda handler of AddTaskHandler");
		logger.log(req.toString());
		
		
		
		try {
			dao.addTask(req.project, req.task);
		}
		catch(Exception e){
			System.out.println("Could not add task.");
		}
		
		AddTaskResponse response = new AddTaskResponse(req.task.name, 200);
		
		return response;
    }
}