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
import com.amazonaws.lambda.addTaskHandler.AddTaskHandler;
import com.amazonaws.lambda.http.AddTaskRequest;
import com.amazonaws.lambda.http.AddTaskResponse;
import com.amazonaws.lambda.http.RenameTaskRequest;
import com.amazonaws.lambda.http.RenameTaskResponse;
import com.amazonaws.lambda.renameTaskHandler.RenameTaskHandler;
import com.google.gson.Gson;


/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class renameTaskHandlerTest extends LambdaTest{
	
	/**
	 * Helper method that creates a context that supports logging so you can test lambda functions
	 * in JUnit without worrying about the logger anymore.
	 * 
	 * @param apiCall      An arbitrary string to identify which API is being called.
	 * @return
	 */

    void testInput(String incoming, String outgoing) throws IOException {
    	RenameTaskHandler handler = new RenameTaskHandler();
    	System.out.println("Created handler");

    	RenameTaskRequest req = new Gson().fromJson(incoming, RenameTaskRequest.class);
    	
    	RenameTaskResponse response = handler.handleRequest(req, createContext("Add Task"));
		
		System.out.println(response.taskName);
		System.out.println(outgoing);
		
		Assert.assertEquals(outgoing, response.taskName);
        Assert.assertEquals(200, response.statusCode);
		
    }
	
    
    @Test 
    public void renameTaskTest() {
    	String SAMPLE_INPUT = "{\"task\":{\"name\":\"test\", \"idProject\":\"NewTestProject\"}, \"newName\":\"testRenamed\"}";
    	String RESULT = "testRenamed";
    	
    	try {
    		testInput(SAMPLE_INPUT, RESULT);
    	}catch (IOException ioe) {
    		Assert.fail("Invalid:" + ioe.getMessage());
    	}
    }
    
   
    
//    @Test 
//    public void testAddTask2() {
//    	String SAMPLE_INPUT = "{\"taskName\": \"aTaskName2\"}";
//    	String RESULT = "aTaskName";
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