package com.amazonaws.lambda.deleteProjectHandler;

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
import com.amazonaws.lambda.db.TaskDAO;
import com.amazonaws.lambda.db.TeammateDAO;
import com.amazonaws.lambda.http.*;
import com.amazonaws.lambda.model.Project;

public class DeleteProjectHandler implements RequestHandler<DeleteProjectRequest, DeleteProjectResponse> {

	LambdaLogger logger;
	
	ProjectDAO projectDAO;
	TaskDAO taskDAO;
	TeammateDAO teammateDAO;
	
    @Override
    public DeleteProjectResponse handleRequest(DeleteProjectRequest req, Context context) {
    	logger = context.getLogger();
    	projectDAO = new ProjectDAO(logger);
    	taskDAO = new TaskDAO(logger);
    	teammateDAO = new TeammateDAO(logger);
    	logger.log("Loading Java Lambda handler of DeleteProjectHandler");
		logger.log(req.toString());
		
		String projectName = "";
		
		projectName = req.getProjectName();
		
		try {
			logger.log("Attempting to delete project: " + projectName);
			projectDAO.deleteProject(new Project(projectName));
			logger.log("Successfully deleted " + projectName);
		}
		catch(Exception e){
			System.out.println("Could not delete project.");
		}
		
		DeleteProjectResponse response = new DeleteProjectResponse(projectName, 200);
		
		return response;
    }
}