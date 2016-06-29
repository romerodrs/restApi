package com.api.ws.oozie;

import java.util.Properties;

import org.apache.oozie.client.OozieClient;
import org.apache.oozie.client.WorkflowJob;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
public class OozieApp {

	@Value("${oozie.client.url}")
	private String url;
	
	@Value("${oozie.client.namenode}")
	private String namenode;
	
	@Value("${oozie.client.jobtracker}")
	private String jobtracker;
	
	@Value("${oozie.client.queuename}")
	private String queuename;
	
	@Value("${oozie.client.libpath}")
	private String libpath;
	
	@Value("${oozie.client.useSystemLibpath}")
	private String useSystemLibpath;
	
	@Value("${oozie.client.wfRerunFailnodes}")
	private String wfRerunFailnodes;
	
	@Value("${oozie.client.projectRoot}")
	private String projectRoot;
	
	@Value("${oozie.client.appPath}")
	private String appPath;
	
	@Value("${oozie.client.oozieclientAppPath}")
	private String oozieclientAppPath;
	
	@Value("${oozie.client.inputdir}")
	private String inputdir;
	
	@Value("${oozie.client.outputdir}")
	private String outputdir;
	
	private  Properties conf;
	
	private void setProperties(OozieClient wc) {
		conf = wc.createConfiguration();
        conf.setProperty("nameNode", namenode);
        conf.setProperty("jobTracker", jobtracker);
        conf.setProperty("queueName", queuename);
        conf.setProperty("oozie.libpath", libpath);
        conf.setProperty("oozie.use.system.libpath", useSystemLibpath);
        conf.setProperty("oozie.wf.rerun.failnodes", wfRerunFailnodes);
        conf.setProperty("oozieProjectRoot", projectRoot);
        conf.setProperty("appPath", appPath);
        
        conf.setProperty(OozieClient.APP_PATH, oozieclientAppPath);
        conf.setProperty("inputDir", inputdir );
        conf.setProperty("outputDir", outputdir);
	}
	
    public String runOozie() {
    	StringBuffer executionLog = new StringBuffer("--- start log ---- \n");	
    	try{
	        OozieClient wc = new OozieClient(url);
	        this.setProperties(wc);
	        try {
	        	//executionLog.append(conf.toString()).append(" ");
	            String jobId = wc.run(conf);
	            executionLog.append("Workflow job, ").append(jobId).append(" submitted \n");
	            while (wc.getJobInfo(jobId).getStatus() == WorkflowJob.Status.RUNNING) {
	            	executionLog.append("Workflow job running ... \n");
	            }
	            executionLog.append("Workflow job completed ... \n" );
	            executionLog.append(wc.getJobInfo(jobId));
	        } catch (Exception e) {
	        	executionLog.append("Errors :").append(e.getLocalizedMessage());
	        }
	    }catch(Exception e){
	    	executionLog.append(this.toString()).append(" " + e.getLocalizedMessage());
	    }
        executionLog.append(" \n -- end log --");
        return executionLog.toString();
    }

	@Override
	public String toString() {
		return "OozieApp [url=" + url + ", namenode=" + namenode
				+ ", jobtracker=" + jobtracker + ", queuename=" + queuename
				+ ", libpath=" + libpath + ", useSystemLibpath="
				+ useSystemLibpath + ", wfRerunFailnodes=" + wfRerunFailnodes
				+ ", projectRoot=" + projectRoot + ", appPath=" + appPath
				+ ", oozieclientAppPath=" + oozieclientAppPath + ", inputdir="
				+ inputdir + ", outputdir=" + outputdir + ", conf=" + conf
				+ "]";
	}

	
}