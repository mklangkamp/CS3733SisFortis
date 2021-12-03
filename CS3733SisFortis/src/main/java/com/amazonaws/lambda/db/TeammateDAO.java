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
            PreparedStatement ps = conn.prepareStatement("DELETE FROM " + tblName + " WHERE (idTeammate,Project) = (?,?);");
            ps.setString(1, teammateName);
            ps.setString(2, projectName);
            int numAffected = ps.executeUpdate();
            logger.log("numAffected: " + numAffected);
            return (numAffected == 1);

        } catch (Exception e) {
            throw new Exception("Failed to delete: " + e.getMessage());
        }
    }
    
        private Teammate generateTeammate(ResultSet resultSet) throws Exception {
            String name  = resultSet.getString("idTeammate");
            return new Teammate(name);
        }
        
}