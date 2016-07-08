package com.api.ws.oozie;

import org.apache.oozie.client.OozieClientException;

public interface OozieService {
	public static String statusRUNNING = "RUNNING";
	public static String statusSUCCEDED = "SUCCEEDED";
	public static String statusKILLED = "KILLED";
	public static String statusFAILED = "FAILED";
	public static String statusPREP = "PREP";
	
	public String executeOozieJob() throws OozieClientException;
	public String oozieJobStatus(String jobId) throws OozieClientException;
}
