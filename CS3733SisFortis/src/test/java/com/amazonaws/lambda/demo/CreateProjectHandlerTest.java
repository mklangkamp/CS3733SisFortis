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
import com.amazonaws.lambda.createProjectHandler.CreateProjectHandler;
import com.amazonaws.lambda.http.CreateProjectRequest;
import com.amazonaws.lambda.http.CreateProjectResponse;
import com.google.gson.Gson;


/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class CreateProjectHandlerTest extends LambdaTest{
	
	/**
	 * Helper method that creates a context that supports logging so you can test lambda functions
	 * in JUnit without worrying about the logger anymore.
	 * 
	 * @param apiCall      An arbitrary string to identify which API is being called.
	 * @return
	 */
//	Context createContext(String apiCall) {
//        TestContext ctx = new TestContext();
//        ctx.setFunctionName(apiCall);
//        return ctx;
//    }

    void testInput(String incoming, String outgoing) throws IOException {
    	CreateProjectHandler handler = new CreateProjectHandler();

//        InputStream input = new ByteArrayInputStream(incoming.getBytes());
//        OutputStream output = new ByteArrayOutputStream();

		CreateProjectRequest req = new Gson().fromJson(incoming, CreateProjectRequest.class);
    	
		CreateProjectResponse response = handler.handleRequest(req, createContext("Create New Project"));
		
		
		Assert.assertEquals(outgoing, response.projectName);
        Assert.assertEquals(200, response.statusCode);
		
		
//        handler.handleRequest(input, output, createContext("create Project"));
//
//        JsonNode outputNode = Jackson.fromJsonString(output.toString(), JsonNode.class);
//        JsonNode body = Jackson.fromJsonString(outputNode.get("body").asText(), JsonNode.class);
//        Assert.assertEquals(outgoing, body.get("projectName").asText());
//        Assert.assertEquals("200", outputNode.get("statusCode").asText());
    }
	
    
    @Test 
    public void testCreateProject() {
    	String SAMPLE_INPUT = "{\"projectName\": \"aTestProjectName\"}";
    	String RESULT = "aTestProjectName";
    	
    	try {
    		testInput(SAMPLE_INPUT, RESULT);
    	}catch (IOException ioe) {
    		Assert.fail("Invalid:" + ioe.getMessage());
    	}
    }
    
    
    @Test 
    public void testCreateProject2() {
    	String SAMPLE_INPUT = "{\"projectName\": \"aProjectName\"}";
    	String RESULT = "aProjectName";
    	
    	try {
    		testInput(SAMPLE_INPUT, RESULT);
    	}catch (IOException ioe) {
    		Assert.fail("Invalid:" + ioe.getMessage());
    	}
    }
    
    
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

