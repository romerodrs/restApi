package com.api.ws.oozie.job;

import org.apache.oozie.client.OozieClient;
//import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by DLRR
 */
public class OozieJob {
	
	//@JsonProperty("oozieJobId")
	private String oozieJobId;
	//@JsonProperty("status")
	private OozieClient oozieClient;

	public String getOozieJobId() {
		return oozieJobId;
	}

	public void setOozieJobId(String oozieJobId) {
		this.oozieJobId = oozieJobId;
	}

	public OozieClient getOozieClient(){
		return oozieClient;
	}
	public void setOozieClient(OozieClient oozieClient) {
		this.oozieClient = oozieClient;	
	}


}
