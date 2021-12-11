package com.amazonaws.lambda.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.lambda.model.Project;
import com.amazonaws.lambda.model.Task;
import com.amazonaws.lambda.model.Teammate;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

public class TeammateToTaskDAO {

java.sql.Connection conn;
	

	final String tblName = "TeammateToTask";   // Exact capitalization
	final String tblName2 = "Task";   // Exact capitalization
	final String tblName3 = "Teammate";   // Exact capitalization
	
	LambdaLogger logger;

    public TeammateToTaskDAO(LambdaLogger logger) {
    	this.logger = logger;
    	try  {
    		logger.log("Trying to connect to database");
    		conn = DatabaseUtil.connect();
    		logger.log("Connected to database");
    	} catch (Exception e) {
    		logger.log("failed to connect db");
    		conn = null;
    	}
    }
	
    public boolean assignTeammate(String idTask, String teammateName, String idProject) throws Exception {
        try {
      	  logger.log("assigning teammate to task");
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE idTask = ? AND idTeammate = ? AND idProject =?;");
            ps.setString(1, idTask);
            ps.setString(2, teammateName);
            ps.setNString(3, idProject);
//            logger.log(idProject);
            ResultSet resultSet = ps.executeQuery();
//            ps.close();
            
            while (resultSet.next()) {
                resultSet.close();
                logger.log("Teammate is already assigned to task.");
                return false; //IF the taskID and the project name are already in the project, dont insert it.
            }
            
            ps = conn.prepareStatement("SELECT * FROM " + tblName2 + " WHERE idTask = ? AND Project = ?;");
            ps.setString(1, idTask);
//            logger.log(idTask);
            ps.setString(2, idProject);
//            logger.log(idProject);
            resultSet = ps.executeQuery();
            
            if (resultSet.next()) {
//            	logger.log("in the second while loop");
//            	Task t = generateTask(resultSet);
                resultSet.close();
                logger.log("Task exists.");
                ps.close();
                // pass through
            }
    
            else {
            	logger.log("Task does not exist");
            	ps.close();
            	return false; //IF the task does not exist, do not insert
            }
            
            ps = conn.prepareStatement("SELECT * FROM " + tblName3 + " WHERE idTeammate = ? AND Project = ?;");
            ps.setString(1, teammateName);
            ps.setString(2, idProject);
            resultSet = ps.executeQuery();
            
            if (resultSet.next()) {
//            	logger.log("in the second while loop");
//            	Task t = generateTask(resultSet);
                resultSet.close();
                logger.log("Teammate exists.");
                ps.close();
                // pass through
            }
    
            else {
            	logger.log("Teammate does not exist");
            	ps.close();
            	return false; //IF the teammate does not exist, do not insert
            }
            
            ps = conn.prepareStatement("INSERT INTO " + tblName + " (idTask,idTeammate,idProject) values(?,?,?);");
            
            ps.setString(1, idTask);
//            logger.log(idTask);
            ps.setString(2, teammateName);
//            logger.log(teammateName);
            ps.setString(3, idProject);
//            logger.log(idProject);
            ps.execute();
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to add: " + e.getMessage());
        }
    }
    
    public ArrayList<String> getAllTaskTeammates(String idTask, String idProject) throws Exception {
//    	logger.log("in the getAllTaskTeammates");
        
	      ArrayList<String> allTaskTeammates = new ArrayList<>();
	    	
	    	try {
		    	logger.log("\tgetting teammate for task: " + idTask);
		        PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE idTask = ? and idProject = ?;");
		        ps.setString(1, idTask);
//		        logger.log(idTask);
		        ps.setString(2, idProject);
//		        logger.log(idProject);
		        ResultSet resultSet = ps.executeQuery();
		        
		        while (resultSet.next()) {
//		        	logger.log("in the while loop");
//		        	logger.log(resultSet.getString("idTeammate"));
		        	allTaskTeammates.add(resultSet.getString("idTeammate"));
		        }
		        resultSet.close();
		        
//		        logger.log("Teammate1 Name: " + teammates.get(0).name);
		        
		        
	    	}catch(Exception e){
//	    		logger.log("exception in getAllTaskTeammate");
	    		throw new Exception("Failed to get teammates for this task: " + e.getMessage());
	    	}
	    	return allTaskTeammates;
}
    
    public ArrayList<Task> getAllTeammateTasks(String teammateName, String idProject) throws Exception {
        
	      ArrayList<Task> allTeammateTasks = new ArrayList<>();
	    	
	    	try {
		    	logger.log("getting tasks for teammate: " + teammateName);
		  
		    	//Determine the task IDs that the teammate is assigned to
		        PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE idTeammate = ?;");
		        
//		        System.out.println("SELECT * FROM " + tblName + " WHERE idTeammate = ?;");
		        ps.setString(1, teammateName);
		        
		        ResultSet resultSet = ps.executeQuery();
		       
		        
		        String str = "SELECT * FROM " + tblName2 + " WHERE (";
		        String projectName = "";
		        

		        //Use the task IDs found above to generate a query string
		        
		        while (resultSet.next()) {
		        	
		        	str = str + "idTask = \"" + resultSet.getString("idTask") + "\" OR ";
					projectName = resultSet.getString("idProject");
		        }
		        
		        str = str.substring(0, str.length() - 3);
		        
		        
		        str = str + ") AND Project = \"" + projectName + "\"";
		        resultSet.close();
		        
		        
		        
		        //Get the results of the query (all tasks with IDs of tasks that the person is assigned to in the project)
//		        System.out.println(str);
		        ps = conn.prepareStatement(str);
		        resultSet = ps.executeQuery();
		        
		        while (resultSet.next()) {
		        	allTeammateTasks.add(this.generateTaskWithoutTeammates(resultSet));
		        }
		        resultSet.close();
		        
//		        logger.log("Teammate1 Name: " + teammates.get(0).name);
		        
		        
	    	}catch(Exception e){
	    		logger.log("Failed to get tasks for this teammate: " + teammateName);
	    	}
	    	return allTeammateTasks;
    }
    
    public Task generateTaskWithoutTeammates(ResultSet resultSet) throws Exception {
//    	logger.log("generating task from result set");
        String id  = resultSet.getString("idTask");
        String name = resultSet.getString("Name");
        Boolean status = resultSet.getBoolean("Status");
//        String idProject = resultSet.getString("Project");

        return new Task (id, name, status);
    }
        
}
