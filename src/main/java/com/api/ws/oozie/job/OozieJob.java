package com.api.ws.oozie.job;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.oozie.client.OozieClient;
import org.codehaus.jackson.annotate.JsonProperty;

import com.api.ws.oozie.OozieServiceImpl;


public class OozieJob {
	
	@JsonProperty("oozieJobId")
	private String oozieJobId;
	@JsonProperty("status")
	private String status;
	
	public static String statusRUNNING = "RUNNING";
	public static String statusSUCCEDED = "SUCCEEDED";
	public static String statusKILLED = "KILLED";
	public static String statusFAILED = "FAILED";
	public static String statusPREP = "PREP";

	private static Logger logger = Logger.getLogger(OozieJob.class);
	
	public OozieJob(OozieServiceImpl oozieService, OozieClient oozieClient){
		logger.info("[Webservice Ozzie] Starting execution...");
		Properties prop = oozieService.getProperties();
    	try{
	        this.setOozieJobId(oozieClient.run(prop));
	        logger.info("[Webservice Ozzie] Workflow " + this.getOozieJobId() + " submitted...");
	        this.setStatus(statusRUNNING);		        
	    }catch(Exception e){
	    	logger.info("[Webservice Ozzie] " + this.toString() + " " + e.getLocalizedMessage());
	    	this.setStatus(statusFAILED);
	    }
	}
	
	public String getOozieJobId() {
		return oozieJobId;
	}

	public void setOozieJobId(String oozieJobId) {
		this.oozieJobId = oozieJobId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


}
