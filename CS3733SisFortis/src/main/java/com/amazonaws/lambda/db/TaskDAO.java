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
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE idTask = ?;");
            ps.setString(1, task.id);
            ResultSet resultSet = ps.executeQuery();
            
            logger.log("before while");
            
            while (resultSet.next()) {
                resultSet.close();
                return false;
            }

            logger.log("after a while");
            
            ps = conn.prepareStatement("INSERT INTO " + tblName + " (idTask,Name,Status,Project) values(?,?,?,?);");
            
            logger.log("??????");
            
            ps.setString(1, task.id);
            ps.setString(2, task.name);
            ps.setBoolean(3, task.status);
            // if this is a subtask, this should not be null (null for top level tasks only, iteration #2)
            //ps.setString(4, null);
            ps.setString(4, project.name);
            logger.log("before execute");
            ps.execute();
            logger.log("inserted into db");
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to add: " + e.getMessage());
        }
    }
    
        private Task generateTask(ResultSet resultSet) throws Exception {
            String id  = resultSet.getString("idTask");
            String name = resultSet.getString("Name");
            return new Task (id, name);
        }
        
}
