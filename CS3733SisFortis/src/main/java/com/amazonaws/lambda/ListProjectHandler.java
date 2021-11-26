package com.amazonaws.lambda;

import java.util.ArrayList;

import com.amazonaws.lambda.db.ProjectDAO;
import com.amazonaws.lambda.http.ListProjectRequest;
import com.amazonaws.lambda.http.ListProjectResponse;
import com.amazonaws.lambda.model.Project;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class ListProjectHandler implements RequestHandler<ListProjectRequest, ListProjectResponse> {

	LambdaLogger logger;
	
	ProjectDAO dao;
	ArrayList<Project> projects;
	
	
    @Override
    public ListProjectResponse handleRequest(ListProjectRequest req, Context context) {
    	logger = context.getLogger();
    	dao = new ProjectDAO(logger);
    	logger.log("Loading Java Lambda handler of ListProjectHandler");
		logger.log(req.toString());
		
		try {
			projects = dao.getAllProjects();
//			System.out.println(projects.get(0).name);
		}
		catch(Exception e) {
			System.out.println("Could not list projects.");
		}
		
		ListProjectResponse response = new ListProjectResponse(projects, 200);
		
		return response;
		
    }

}
