package com.amazonaws.lambda.demo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Assert;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.util.json.Jackson;
import com.fasterxml.jackson.databind.JsonNode;
import com.amazonaws.lambda.http.ProjectViewRequest;
import com.amazonaws.lambda.http.ProjectViewResponse;
import com.amazonaws.lambda.projectViewHandler.ProjectViewHandler;
import com.google.gson.Gson;


/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class ProjectViewHandlerTest extends LambdaTest{
	
	/**
	 * Helper method that creates a context that supports logging so you can test lambda functions
	 * in JUnit without worrying about the logger anymore.
	 * 
	 * @param apiCall      An arbitrary string to identify which API is being called.
	 * @return
	 */

    void testInput(String incoming, String outgoing) throws IOException {
    	ProjectViewHandler handler = new ProjectViewHandler();

		ProjectViewRequest req = new Gson().fromJson(incoming, ProjectViewRequest.class);
    	
		ProjectViewResponse response = handler.handleRequest(req, createContext("Find Project"));
		
		printTasks(response);
		
		Assert.assertEquals(outgoing, response.project.name); 
        Assert.assertEquals(200, response.statusCode);
    }
    
    
    void printTasks(ProjectViewResponse response) {
    	response.project.tasks.forEach(task -> System.out.println(task.name));
    }
	
    
    @Test 
    public void testProjectView() {
    	String SAMPLE_INPUT = "{\"projectName\": \"aTestProjectName2\"}";
    	String RESULT = "aTestProjectName2";
    	
    	try {
    		testInput(SAMPLE_INPUT, RESULT);
    	}catch (IOException ioe) {
    		Assert.fail("Invalid:" + ioe.getMessage());
    	}
    }
    
    @Test
    public void testProjectView2() {
    	String SAMPLE_INPUT = "{\"projectName\": \"aa\"}";
    	String RESULT = "aa";
    	
    	try {
    		testInput(SAMPLE_INPUT, RESULT);
    	}catch (IOException ioe) {
    		Assert.fail("Invalid:" + ioe.getMessage());
    	}
    }
    
    @Test
    public void testProjectView3() {
    	String SAMPLE_INPUT = "{\"projectName\": \"abc\"}";
    	String RESULT = "abc";
    	
    	try {
    		testInput(SAMPLE_INPUT, RESULT);
    	}catch (IOException ioe) {
    		Assert.fail("Invalid:" + ioe.getMessage());
    	}
    }
    
    
//    @Test 
//    public void testViewProject2() {
//    	String SAMPLE_INPUT = "{\"projectName\": \"aProjectName2\"}";
//    	String RESULT = "aProjectName";
//    	
//    	try {
//    		testInput(SAMPLE_INPUT, RESULT);
//    	}catch (IOException ioe) {
//    		Assert.fail("Invalid:" + ioe.getMessage());
//    	}
//    }
    
    
//    void testFailInput(String incoming, String outgoing) throws IOException {
//    	CreateProjectHandler handler = new CreateProjectHandler();
//
//        InputStream input = new ByteArrayInputStream(incoming.getBytes());
//        OutputStream output = new ByteArrayOutputStream();
//
//        handler.handleRequest(input, output, createContext("createProject"));
//
//        JsonNode outputNode = Jackson.fromJsonString(output.toString(), JsonNode.class);
//        Assert.assertEquals("400", outputNode.get("statusCode").asText());
//    }
//	
//    
//    @Test
//    public void testCreateProject() {
//    	String SAMPLE_INPUT_STRING = "{\"projectName\": \"aUniqueProjectName\"}";
//        String RESULT = "aUniqueProjectName";
//        
//        try {
//        	testInput(SAMPLE_INPUT_STRING, RESULT);
//        } catch (IOException ioe) {
//        	Assert.fail("Invalid:" + ioe.getMessage());
//        }
//    }
//    
//    @Test
//    public void testCalculatorConstant() {
//    	String SAMPLE_INPUT_STRING = "{\"projectName\": \"aDifferentProjectName\"}";
//        String RESULT = "aDifferentProjectName";
//        
//        try {
//        	testInput(SAMPLE_INPUT_STRING, RESULT);
//        } catch (IOException ioe) {
//        	Assert.fail("Invalid:" + ioe.getMessage());
//        }
//    }
    
    //Commented out bc it doesn't apply since we are just receiving a string
//    @Test
//    public void testFailInput() {
//    	String SAMPLE_INPUT_STRING = "{\"arg1\": \"- GARBAGE -\", \"arg2\": \"10\"}";
//        String RESULT = "";
//        
//        try {
//        	testFailInput(SAMPLE_INPUT_STRING, RESULT);
//        } catch (IOException ioe) {
//        	Assert.fail("Invalid:" + ioe.getMessage());
//        }
//    }
    
    public static void main(String[] args) {
		System.out.println("SDSD");
	}
}

