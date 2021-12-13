package com.amazonaws.lambda;

import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.lambda.createProjectHandler.CreateProjectHandler;
import com.amazonaws.lambda.http.CreateProjectRequest;
import com.amazonaws.lambda.http.CreateProjectResponse;
import com.amazonaws.lambda.http.ListProjectRequest;
import com.amazonaws.lambda.http.ListProjectResponse;
import com.amazonaws.services.lambda.runtime.Context;
import com.google.gson.Gson;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class listProjectHandlerTest {

//    private static Object input;
//
//    @BeforeClass
//    public static void createInput() throws IOException {
//        // TODO: set up your sample input object here.
//        input = null;
//    }
//
    private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("ListProjectHandler");

        return ctx;
    }
//
//    @Test
//    public void testlistProjectHandler() {
//        listProjectHandler handler = new listProjectHandler();
//        Context ctx = createContext();
//
//        String output = handler.handleRequest(input, ctx);
//
//        // TODO: validate output here if needed.
//        Assert.assertEquals("Hello from Lambda!", output);
//    }
//    
    void testInput(String incoming, String outgoing) throws IOException {
    	ListProjectHandler handler = new ListProjectHandler();

		ListProjectRequest req = new Gson().fromJson(incoming, ListProjectRequest.class);
    	
		ListProjectResponse response = handler.handleRequest(req, createContext());
		
		System.out.println(response.toString());
		
		Assert.assertEquals(outgoing, response.projects);
        Assert.assertEquals(200, response.statusCode);
		
    }
	
    
    @Test 
    public void testListProject() {
    	String SAMPLE_INPUT = "{}";
    	String RESULT = "[aa, abc, aTestProjectName]";
    	
    	try {
    		testInput(SAMPLE_INPUT, RESULT);
    	}catch (IOException ioe) {
    		Assert.fail("Invalid:" + ioe.getMessage());
    	}
    }
}
