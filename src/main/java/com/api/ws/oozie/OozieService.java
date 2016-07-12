package com.api.ws.oozie;

import java.util.concurrent.Future;

import org.apache.oozie.client.OozieClientException;

import com.api.ws.oozie.job.OozieJob;
/**
 * Created by DLRR
 */
public interface OozieService {
	public static String statusRUNNING = "RUNNING";
	public static String statusSUCCEDED = "SUCCEEDED";
	public static String statusKILLED = "KILLED";
	public static String statusFAILED = "FAILED";
	public static String statusPREP = "PREP";
	
	public OozieJob executeOozieJob() throws OozieClientException;
	public String oozieJobStatus(String jobId) throws OozieClientException;
	public Future<String> updateOozieJobStatus(OozieJob oozieJob)throws OozieClientException, InterruptedException; 
}
