package com.amazonaws.lambda.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.lambda.model.Project;
import com.amazonaws.lambda.model.Task;
import com.amazonaws.lambda.model.Teammate;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

public class TeammateDAO {

java.sql.Connection conn;
	
	final String tblName = "Teammate";   // Exact capitalization
	final String tblName2 = "TeammateToTask";   // Exact capitalization
	
	TaskDAO taskDAO;
	
	LambdaLogger logger;

    public TeammateDAO(LambdaLogger logger) {
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
	
    public boolean addTeammate(String projectName, String teammateName) throws Exception {
        try {
      	  logger.log("adding teammate");
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE idTeammate = ?;");
            ps.setString(1, teammateName);
            ResultSet resultSet = ps.executeQuery();
            
            logger.log("before while");
            
            while (resultSet.next()) {
                resultSet.close();
                return false;
            }

            logger.log("after a while");
            
            ps = conn.prepareStatement("INSERT INTO " + tblName + " (idTeammate,Project) values(?,?);");
            
            logger.log("??");
            
            ps.setString(1, teammateName);
            ps.setString(2, projectName);
            
            logger.log("before execute");
            ps.execute();
            logger.log("inserted into db");
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to add: " + e.getMessage());
        }
    }
    
    public boolean removeTeammate(String projectName, String teammateName) throws Exception {
        try {
      	  //System.out.println("adding project");
      	  logger.log("removing teammate");
            PreparedStatement ps = conn.prepareStatement("DELETE FROM " + tblName + " WHERE idTeammate = ? AND Project = ?;");
            ps.setString(1, teammateName);
            ps.setString(2, projectName);
            int numAffected = ps.executeUpdate();
            logger.log("numAffected: " + numAffected);
            return (numAffected == 1);

        } catch (Exception e) {
            throw new Exception("Failed to delete: " + e.getMessage());
        }
    }
    
    
    public boolean removeTeammatesOnProject(String projectName) throws Exception {
        try {
      	  //System.out.println("adding project");
      	  	logger.log("removing teammates");
            PreparedStatement ps = conn.prepareStatement("DELETE FROM " + tblName + " WHERE Project = ?;");
            ps.setString(1, projectName);
            int numAffected = ps.executeUpdate();
            logger.log("numAffected: " + numAffected);
            return (numAffected == 1);
        } catch (Exception e) {
            throw new Exception("Failed to delete: " + e.getMessage());
        }
    }
    
    
    public ArrayList<Teammate> getTeammatesForProject(String projectName) throws Exception{
    	
    	ArrayList<Teammate> teammates = new ArrayList<Teammate>();
    	
    	try {
	    	logger.log("getting teammates for project: " + projectName);
	        PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE Project = ?;");
	        ps.setString(1, projectName);
	        ResultSet resultSet = ps.executeQuery();
	        
	        while (resultSet.next()) {
	            teammates.add(generateTeammate(resultSet));
	        }
	        resultSet.close();
	        
//	        logger.log("Teammate1 Name: " + teammates.get(0).name);
	        
	        
    	}catch(Exception e){
    		throw new Exception("Failed to get teammates: " + e.getMessage());
    	}
    	return teammates;
    }
    
    
        public Teammate generateTeammate(ResultSet resultSet) throws Exception {
            String name  = resultSet.getString("idTeammate");
            return new Teammate(name);
        }
        
}
