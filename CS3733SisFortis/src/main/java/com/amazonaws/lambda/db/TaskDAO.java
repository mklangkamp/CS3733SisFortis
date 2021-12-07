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
    
    public boolean addSubtask(Project project, Task task) throws Exception {
        try {
      	  logger.log("adding subtask");
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE idTask = ? AND Project = ? AND ParentTask = ?;");
            ps.setString(1, task.id);
            ps.setString(2, project.name); 
            ps.setString(3, task.idParent);
//            logger.log("Before the resultSet");
            ResultSet resultSet = ps.executeQuery();
//            logger.log("After the resultSet");
            
            while (resultSet.next()) {
//            	logger.log("in the while loop");
                resultSet.close();
                ps.close();
//                logger.log("Task already exists, could not add.");
                return false; //IF the taskID and the project name are already in the project, don't insert it.
            }
            ps.close();
            
//            logger.log("checking for parent to be in same project");
            ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE idTask = ? AND Project = ?;");
            ps.setString(1, task.idParent);
            ps.setString(2, project.name);
//            logger.log("Before second resultSet");
            resultSet = ps.executeQuery();
//            logger.log("After second resultSet");
            
            if (resultSet.next()) {
//            	logger.log("in the second while loop");
            	Task t = generateTask(resultSet);
                resultSet.close();
                logger.log("Parent Task exists.");
                ps.close();
                // pass through
            }
    
            else {
            	logger.log("Parent task does not exist");
            	ps.close();
            	return false; //IF the parent does not exist, do not insert
            }
            
            logger.log("Before the preparedStatement");
            ps = conn.prepareStatement("INSERT INTO " + tblName + " (idTask,Name,Status,ParentTask,Project) values(?,?,?,?,?);");
            logger.log("After the preparedStatement");
            
            ps.setString(1, task.id);
            ps.setString(2, task.name);
            ps.setBoolean(3, task.status);
            // if this is a subtask, this should not be null (null for top level tasks only, iteration #2)
            //ps.setString(4, null);
//            logger.log("Before setting parentName");
            ps.setString(4, task.idParent);
//            logger.log("After setting parentName");
            ps.setString(5, project.name);
//            logger.log("Before the execute");
            ps.execute();
            ps.close();
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to add: " + e.getMessage());
        }
    }
    
    
//    public boolean addSubtask(Project project, Task task) throws Exception {
//        try {
//      	  logger.log("adding subtask");
//            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE idTask = ? AND Project = ? AND ParentTask = ?;");
//            ps.setString(1, task.id);
//            ps.setString(2, project.name);
//            ps.setString(3, task.parentName);
//            logger.log("Before the resultSet");
//            ResultSet resultSet = ps.executeQuery();
//            logger.log("After the resultSet");
//            
//            while (resultSet.next()) {
//            	logger.log("in the while loop");
//                resultSet.close();
//                logger.log("Task already exists, could not add.");
//                return false; //IF the taskID and the project name are already in the project, dont insert it.
//            }
//            
//            logger.log("Before the preparedStatement");
//            ps = conn.prepareStatement("INSERT INTO " + tblName + " (idTask,Name,Status,ParentTask,Project) values(?,?,?,?,?);");
//            logger.log("After the preparedStatement");
//            
//            ps.setString(1, task.id);
//            ps.setString(2, task.name);
//            ps.setBoolean(3, task.status);
//            // if this is a subtask, this should not be null (null for top level tasks only, iteration #2)
//            //ps.setString(4, null);
//            logger.log("Before setting parentName");
//            ps.setString(4, task.parentName);
//            logger.log("After setting parentName");
//            ps.setString(5, project.name);
//            logger.log("Before the execute");
//            ps.execute();
//            return true;
//
//        } catch (Exception e) {
//            throw new Exception("Failed to add: " + e.getMessage());
//        }
//    }
    
    
        private Task generateTask(ResultSet resultSet) throws Exception {
            String id  = resultSet.getString("idTask");
            String name = resultSet.getString("Name");
            Boolean status = resultSet.getBoolean("Status");
            return new Task (id, name, status);
        }
        
}
