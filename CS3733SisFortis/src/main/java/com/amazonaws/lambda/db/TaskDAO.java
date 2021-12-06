package com.amazonaws.lambda.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.lambda.model.Project;
import com.amazonaws.lambda.model.Task;
import com.amazonaws.lambda.model.Teammate;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

public class TaskDAO {

java.sql.Connection conn;
	
	final String tblName = "Task";   // Exact capitalization
	LambdaLogger logger;

    public TaskDAO(LambdaLogger logger) {
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
	
    public boolean addTask(Project project, Task task) throws Exception {
        try {
      	  logger.log("adding task");
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE idTask = ? and Project = ?;");
            ps.setString(1, task.id);
            ps.setString(2, project.name);
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()) {
                resultSet.close();
                logger.log("Task already exists, could not add.");
                return false; //IF the taskID and the project name are already in the project, dont insert it.
            }
            
            ps = conn.prepareStatement("INSERT INTO " + tblName + " (idTask,Name,Status,Project) values(?,?,?,?);");
            
            ps.setString(1, task.id);
            ps.setString(2, task.name);
            ps.setBoolean(3, task.status);
            // if this is a subtask, this should not be null (null for top level tasks only, iteration #2)
            //ps.setString(4, null);
            ps.setString(4, project.name);
            ps.execute();
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to add: " + e.getMessage());
        }
    }
    
    
    public ArrayList<Task> getTasksForProject(Project p) throws Exception {
        try {
      	  	logger.log("getting Tasks for: " + p.name);
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE Project = ?;");
            ps.setString(1, p.name);
            ResultSet resultSet = ps.executeQuery();
                     
            ArrayList<Task> taskList = new ArrayList<Task>();
            
            while (resultSet.next()) {
                taskList.add(generateTask(resultSet));
            }
            resultSet.close();

            return taskList;

        } catch (Exception e) {
            throw new Exception("Failed to add: " + e.getMessage());
        }
    }
    
    
    public boolean removeTasksOnProject(String projectName) throws Exception {
        try {
      	  //System.out.println("adding project");
      	  	logger.log("removing tasks");
            PreparedStatement ps = conn.prepareStatement("DELETE FROM " + tblName + " WHERE Project = ?;");
            ps.setString(1, projectName);
            int numAffected = ps.executeUpdate();
            logger.log("numAffected: " + numAffected);
            return (numAffected == 1);
        } catch (Exception e) {
            throw new Exception("Failed to delete: " + e.getMessage());
        }
    }
    
    
        private Task generateTask(ResultSet resultSet) throws Exception {
            String id  = resultSet.getString("idTask");
            String name = resultSet.getString("Name");
            Boolean status = resultSet.getBoolean("Status");
            return new Task (id, name, status);
        }
        
}
