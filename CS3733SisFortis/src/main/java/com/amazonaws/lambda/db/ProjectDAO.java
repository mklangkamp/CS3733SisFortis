package com.amazonaws.lambda.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.lambda.model.Project;
import com.amazonaws.lambda.model.Task;
import com.amazonaws.lambda.model.Teammate;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

//import edu.wpi.cs.heineman.demo.model.Constant;

/**
 * Note that CAPITALIZATION matters regarding the table name. If you create with 
 * a capital "Constants" then it must be "Constants" in the SQL queries.
 *
 */
public class ProjectDAO { 

	java.sql.Connection conn;
	TaskDAO taskDAO;
	
	final String tblName = "Project";   // Exact capitalization
	final String tblName2 = "Task";
	final String tblName3 = "Teammate";
	LambdaLogger logger;

    public ProjectDAO(LambdaLogger logger) {
    	taskDAO = new TaskDAO(logger);
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
    
    public boolean addProject(Project project) throws Exception {
      try {
    	  //System.out.println("adding project");
    	  logger.log("adding project");
          PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE idProject = ?;");
          ps.setString(1, project.name);
          ResultSet resultSet = ps.executeQuery();
          
          // already present?
          while (resultSet.next()) {
        	  logger.log("in while loop");
        	 // System.out.println("in while loop");
              Project p = generateProject(resultSet);
              resultSet.close();
              return false;
          }
          
          //System.out.println("outside while loop");
          logger.log("outside of while loop");

          ps = conn.prepareStatement("INSERT INTO " + tblName + " (idProject) values(?);");
          ps.setString(1, project.name);
          ps.execute();
          logger.log("inserted into db");
          return true;

      } catch (Exception e) {
          throw new Exception("Failed to add: " + e.getMessage());
      }
  }
    
    
    public boolean deleteProject(Project project) throws Exception {
        try {
      	  //System.out.println("adding project");
      	  	logger.log("deleting project");
            PreparedStatement ps = conn.prepareStatement("DELETE FROM " + tblName + " WHERE idProject = ?;");
            ps.setString(1, project.name);
            PreparedStatement ps2 = conn.prepareStatement("DELETE * FROM " + tblName2 + " WHERE idProject = ?;");
//            ps2.setString(1, task.id);
            ps2.setString(2, project.name);
            PreparedStatement ps3 = conn.prepareStatement("DELETE * FROM " + tblName3 + " WHERE idProject = ?;");
//            ps3.setString(1, teammate.name);
            ps3.setString(2, project.name);
            int numAffected = ps.executeUpdate();
            
            return (numAffected == 1);

        } catch (Exception e) {
            throw new Exception("Failed to delete: " + e.getMessage());
        }
        
        
    }
    
    private Project generateProject(ResultSet resultSet) throws Exception {
      String name  = resultSet.getString("idProject");
      Project p = new Project (name);
      
      //Try to get tasks for project
      try {
			p.tasks = taskDAO.getTasksForProject(p);
		}catch(Exception e) {
			logger.log("Could not find tasks for: " + name);
		}
      
      return p;
  }
    
    public ArrayList<Project> getAllProjects() throws Exception {
      
	      ArrayList<Project> allProjects = new ArrayList<>();
	      try {
	          Statement statement = conn.createStatement();
	          String query = "SELECT * FROM " + tblName + ";";
	          ResultSet resultSet = statement.executeQuery(query);
	          
	//          logger.log("Got resultSet");
	          
	          while (resultSet.next()) {
	//        	  logger.log(resultSet.toString());
	              Project p = generateProject(resultSet);
	              logger.log(p.name);
	              allProjects.add(p);
	          }
	          logger.log("Projects added to allProjects");
	          resultSet.close();
	          statement.close();
	          return allProjects;
	
	      } catch (Exception e) {
	          throw new Exception("Failed in getting projects: " + e.getMessage());
	      }
  }
    
    public Project getProject(String projectName) throws Exception {
        
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE idProject=? ;");
            ps.setString(1, projectName);
            ResultSet resultSet = ps.executeQuery();
            
            Project p = null;
            while(resultSet.next()) {
                p = generateProject(resultSet);
            }
            resultSet.close();
            ps.close();
            return p;

        } catch (Exception e) {
            throw new Exception("Project Not Found: " + e.getMessage());
        }
    }

//    public Constant getConstant(String name) throws Exception {
//        
//        try {
//            Constant constant = null;
//            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE name=?;");
//            ps.setString(1,  name);
//            ResultSet resultSet = ps.executeQuery();
//            
//            while (resultSet.next()) {
//                constant = generateConstant(resultSet);
//            }
//            resultSet.close();
//            ps.close();
//            
//            return constant;
//
//        } catch (Exception e) {
//        	e.printStackTrace();
//            throw new Exception("Failed in getting constant: " + e.getMessage());
//        }
//    }
    
//    public boolean updateConstant(Constant constant) throws Exception {
//        try {
//        	String query = "UPDATE " + tblName + " SET value=? WHERE name=?;";
//        	PreparedStatement ps = conn.prepareStatement(query);
//            ps.setDouble(1, constant.value);
//            ps.setString(2, constant.name);
//            int numAffected = ps.executeUpdate();
//            ps.close();
//            
//            return (numAffected == 1);
//        } catch (Exception e) {
//            throw new Exception("Failed to update report: " + e.getMessage());
//        }
//    }
    
//    public boolean deleteConstant(Constant constant) throws Exception {
//        try {
//            PreparedStatement ps = conn.prepareStatement("DELETE FROM " + tblName + " WHERE name = ?;");
//            ps.setString(1, constant.name);
//            int numAffected = ps.executeUpdate();
//            ps.close();
//            
//            return (numAffected == 1);
//
//        } catch (Exception e) {
//            throw new Exception("Failed to insert constant: " + e.getMessage());
//        }
//    }


//    public boolean addConstant(Constant constant) throws Exception {
//        try {
//            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE name = ?;");
//            ps.setString(1, constant.name);
//            ResultSet resultSet = ps.executeQuery();
//            
//            // already present?
//            while (resultSet.next()) {
//                Constant c = generateConstant(resultSet);
//                resultSet.close();
//                return false;
//            }
//
//            ps = conn.prepareStatement("INSERT INTO " + tblName + " (name,value) values(?,?);");
//            ps.setString(1,  constant.name);
//            ps.setDouble(2,  constant.value);
//            ps.execute();
//            return true;
//
//        } catch (Exception e) {
//            throw new Exception("Failed to insert constant: " + e.getMessage());
//        }
//    }

//    public List<Constant> getAllConstants() throws Exception {
//        
//        List<Constant> allConstants = new ArrayList<>();
//        try {
//            Statement statement = conn.createStatement();
//            String query = "SELECT * FROM " + tblName + ";";
//            ResultSet resultSet = statement.executeQuery(query);
//
//            while (resultSet.next()) {
//                Constant c = generateConstant(resultSet);
//                allConstants.add(c);
//            }
//            resultSet.close();
//            statement.close();
//            return allConstants;
//
//        } catch (Exception e) {
//            throw new Exception("Failed in getting constants: " + e.getMessage());
//        }
//    }
    
//    private Constant generateConstant(ResultSet resultSet) throws Exception {
//        String name  = resultSet.getString("name");
//        Double value = resultSet.getDouble("value");
//        return new Constant (name, value);
//    }

}