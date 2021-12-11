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
		
		
		
		System.out.println("Projects, assignees and their tasks");
		projects.forEach(p -> {
			System.out.println("\n\n");
		      
		      System.out.println("Project Name: " + p.name);
		      
		      p.teammates.forEach(teammate -> {
		    	  System.out.println("\t" + teammate.name);
		    	  teammate.taskList.forEach(task -> {
		    		  System.out.println("\t\t" + task.name);
		    	  });
		    	  
			  });
		});
		
		
		
		System.out.println("\n------------------------------------------------------------\nProjects, tasks and their assignees");
		projects.forEach(p -> {
			System.out.println("\n\n");
		      
		      System.out.println("Project Name: " + p.name);
		      
		      p.tasks.forEach(task -> {
		    	  System.out.println("\t" + task.name);
		    	  task.assignedTeammates.forEach(teammate -> {
		    		  System.out.println("\t\t" + teammate);
		    	  });
		    	  
			  });
		});
		
		
		ListProjectResponse response = new ListProjectResponse(projects, 200);
		
		return response;
		
    }

}
