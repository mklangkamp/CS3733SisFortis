package com.amazonaws.lambda.demo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Assert;
import org.junit.Test;

import com.amazonaws.lambda.addTeammateHandler.AddTeammateHandler;
import com.amazonaws.lambda.http.AddTeammateRequest;
import com.amazonaws.lambda.http.AddTeammateResponse;
import com.amazonaws.lambda.http.AssignTeammateRequest;
import com.amazonaws.lambda.http.AssignTeammateResponse;
import com.amazonaws.lamda.assignTeammateHandler.AssignTeammateHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.util.json.Jackson;
import com.fasterxml.jackson.databind.JsonNode;

import com.google.gson.Gson;


/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class AssignTeammateHandlerTest extends LambdaTest{
	
	/**
	 * Helper method that creates a context that supports logging so you can test lambda functions
	 * in JUnit without worrying about the logger anymore.
	 * 
	 * @param apiCall      An arbitrary string to identify which API is being called.
	 * @return
	 */

    void testInput(String incoming, String outgoing) throws IOException {
    	AssignTeammateHandler handler = new AssignTeammateHandler();

    	AssignTeammateRequest req = new Gson().fromJson(incoming, AssignTeammateRequest.class);
    	
    	AssignTeammateResponse response = handler.handleRequest(req, createContext("Assign Teammate"));
		
		Assert.assertEquals(outgoing, response.teammateName);
        Assert.assertEquals(200, response.statusCode);
		
    }
	
    
    @Test 
    public void testAssignTeammate() {
    	String SAMPLE_INPUT = "{\"idTask\": \"1\", \"teammateName\": \"Bob\", \"idProject\": \"abc\"}";
    	String RESULT = "Mitchell";
    	
    	try {
    		testInput(SAMPLE_INPUT, RESULT);
    	}catch (IOException ioe) {
    		Assert.fail("Invalid:" + ioe.getMessage());
    	}
    }
    
    @Test 
    public void testAssignTeammate2() {
    	String SAMPLE_INPUT = "{\"idTask\": \"1\", \"teammateName\": \"Jim\", \"idProject\": \"NewTestProject\"}";
    	String RESULT = "Jim";
    	
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


