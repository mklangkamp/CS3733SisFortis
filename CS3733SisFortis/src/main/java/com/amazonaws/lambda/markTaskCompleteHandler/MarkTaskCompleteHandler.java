package com.amazonaws.lambda.markTaskCompleteHandler;

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

public class MarkTaskCompleteHandler implements RequestHandler<MarkTaskCompleteRequest, MarkTaskCompleteResponse> {

	LambdaLogger logger;
	
	TaskDAO dao;
	
    @Override
    public MarkTaskCompleteResponse handleRequest(MarkTaskCompleteRequest req, Context context) {
    	logger = context.getLogger();
    	dao = new TaskDAO(logger);
    	logger.log("Loading Java Lambda handler of MarkTaskCompleteHandler");
		logger.log(req.toString());
		
		
		
		try {
			dao.markTaskComplete(req.projectName, req.taskName);
		}
		catch(Exception e){
			System.out.println("Could not mark task complete.");
		}
		
		MarkTaskCompleteResponse response = new MarkTaskCompleteResponse(req.taskName, 200);
		
		return response;
    }
}