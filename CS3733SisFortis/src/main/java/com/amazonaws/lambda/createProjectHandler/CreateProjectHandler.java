package com.amazonaws.lambda.createProjectHandler;

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
import com.amazonaws.lambda.db.ProjectDAO;
import com.amazonaws.lambda.http.*;
import com.amazonaws.lambda.model.Project;

public class CreateProjectHandler implements RequestHandler<CreateProjectRequest, CreateProjectResponse> {

	LambdaLogger logger;
	
	private AmazonS3 s3 = null;
	
	ProjectDAO dao = new ProjectDAO();
	
    @Override
    public CreateProjectResponse handleRequest(CreateProjectRequest req, Context context) {
    	logger = context.getLogger();
    	logger.log("Loading Java Lambda handler of CreateProjectHandler");
		logger.log(req.toString());
		
		String projectName = "";
		
		projectName = req.getProjectName();
		
		try {
			dao.addProject(new Project(projectName));
		}
		catch(Exception e){
			System.out.println("Could not add project.");
		}
		
		CreateProjectResponse response = new CreateProjectResponse(projectName, 200);
		
		return response;
    }
}