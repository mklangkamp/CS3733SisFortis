package com.amazonaws.lambda.projectViewHandler;

import com.amazonaws.lambda.db.ProjectDAO;
import com.amazonaws.lambda.db.TaskDAO;
import com.amazonaws.lambda.http.CreateProjectRequest;
import com.amazonaws.lambda.http.CreateProjectResponse;
import com.amazonaws.lambda.http.ProjectViewRequest;
import com.amazonaws.lambda.http.ProjectViewResponse;
import com.amazonaws.lambda.model.Project;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

public class ProjectViewHandler implements RequestHandler<ProjectViewRequest, ProjectViewResponse> {
	
	LambdaLogger logger;
	
	ProjectDAO projectDAO;
	TaskDAO taskDAO;
	
    @Override
    public ProjectViewResponse handleRequest(ProjectViewRequest req, Context context) {
    	logger = context.getLogger();
    	projectDAO = new ProjectDAO(logger);
    	taskDAO = new TaskDAO(logger);
    	logger.log("Loading Java Lambda handler of ProjectViewHandler");
//		logger.log(req.toString());
		
		String projectName = "";
		
		projectName = req.getProjectName();
		
		Project p = null;
		
		
		//Try to get project from database
		try {
			p = projectDAO.getProject(projectName);
		}
		catch(Exception e){
			System.out.println("Could not find project.");
		}
		
		//Try to get tasks associated with that project
		try {
			p.tasks = taskDAO.getTasksForProject(p);
		}catch(Exception e) {
			logger.log("Could not find tasks for: " + projectName);
		}
		
		ProjectViewResponse response = new ProjectViewResponse(p, 200);
		
		return response;
    }
}