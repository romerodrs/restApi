package com.api.ws.oozie;

import java.util.Properties;


import com.api.ws.oozie.job.OozieJob;

public interface OozieService {
	public OozieJob executeOozieJob();
	//public void executeOozieJob(OozieJob oozieJob);
	public Properties getProperties();
}
