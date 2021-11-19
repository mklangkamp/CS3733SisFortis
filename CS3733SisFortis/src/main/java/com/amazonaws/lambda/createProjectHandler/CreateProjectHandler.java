package com.amazonaws.lambda.createProjectHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.json.Jackson;
import com.fasterxml.jackson.databind.JsonNode;

public class CreateProjectHandler implements RequestStreamHandler {

	LambdaLogger logger;
	
	private AmazonS3 s3 = null;
	
//	double getDoubleFromBucket(String constantName) throws Exception {
//		if (s3 == null) {
//			// overly precise.... shouldn't have to live life this way...
//			s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_2).build();
//		}
//		S3Object obj = s3.getObject("calculator-aws-example/constants", constantName);
//
//		try (S3ObjectInputStream constantStream = obj.getObjectContent()) {
//			Scanner sc = new Scanner(constantStream);
//			String val = sc.nextLine();
//			sc.close();
//			return Double.parseDouble(val);
//		}
//	}
	
    @Override
    public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
    	logger = context.getLogger();
    	if (context != null) { context.getLogger(); }
    	
    	// load entire input into a String (since it contains JSON)
    	StringBuilder incoming = new StringBuilder();
    	try (BufferedReader br = new BufferedReader(new InputStreamReader(input))) {
    		String line = null;
    		while ((line = br.readLine()) != null) {
    			incoming.append(line);
    		}
    	}
    	
    	// When coming in from Lambda function is pure JSON. When coming from API Gateway or the
    	// real thing, then is wrapped inside more complicated JSON and you only want the BODY
    	// in most cases.
        JsonNode node = Jackson.fromJsonString(incoming.toString(), JsonNode.class);
        
        if (node.has("body")) {
        	node = Jackson.fromJsonString(node.get("body").asText(), JsonNode.class);
        }
        
        
        
        
        //I think this is where we will start forming a project/parsing the info
//    	double arg1 = 0, arg2 = 0;
//    	String projectName = "";
    	
    	String projectName = node.get("projectName").asText();
    	boolean error = false;
//		try {
//    		arg1 = Double.parseDouble(param);
//    	} catch (NumberFormatException nfe) {
//    		try {
//    			arg1 = getDoubleFromBucket(param);
//    		} catch (Exception e) {
//    			logger.log("Unable to parse:" + param + " as arg1"); 
//    			error = true;
//    		}
//    	}
    	
    	
        PrintWriter pw = new PrintWriter(output);

        int statusCode;
		if (error) {
			statusCode = 400;
		} else {
			//Return project name in JSON string
	    	statusCode = 200;
		}
    	
		// Needed for CORS integration...
		String response = "{ \n" + 
				         "  \"isBase64Encoded\" : false, \n" +
				         "  \"statusCode\"      : " + statusCode + ", \n" +
				         "  \"headers\" : { \n " +
		                 "     \"Access-Control-Allow-Origin\" : \"*\", \n" + 
				         "     \"Access-Control-Allow-Method\"  : \"GET,POST,OPTIONS\" \n" + 
		                 "  }, \n" +
				         "  \"body\" : \"" + "{ \\\"projectName\\\" : \\\"" + projectName + "\\\" }" + "\" \n" +
				         "}";
		
        // write out.
        pw.print(response);
        pw.close();
    }
}