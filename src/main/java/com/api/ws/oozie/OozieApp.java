package com.api.ws.oozie;

import java.util.Properties;

import org.apache.oozie.client.OozieClient;
import org.apache.oozie.client.WorkflowJob;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "oozie")
@PropertySource("classpath:oozie.properties")
public class OozieApp {

	private String url;
	private String namenode;
	private String jobtracker;
	private String queuename;
	private String libpath;
	private String useSystemLibpath;
	private String wfRerunFailnodes;
	private String projectRoot;
	private String appPath;
	private String oozieclientAppPath;
	private String inputdir;
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
	        //	executionLog.append(conf.toString()).append(" ");
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

	public String getUrl() {
		return url;
	}

	public String getNamenode() {
		return namenode;
	}

	public String getJobtracker() {
		return jobtracker;
	}

	public String getQueuename() {
		return queuename;
	}

	public String getLibpath() {
		return libpath;
	}

	public String getUseSystemLibpath() {
		return useSystemLibpath;
	}

	public String getWfRerunFailnodes() {
		return wfRerunFailnodes;
	}

	public String getProjectRoot() {
		return projectRoot;
	}

	public String getAppPath() {
		return appPath;
	}

	public String getOozieclientAppPath() {
		return oozieclientAppPath;
	}

	public String getInputdir() {
		return inputdir;
	}

	public String getOutputdir() {
		return outputdir;
	}

	public Properties getConf() {
		return conf;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setNamenode(String namenode) {
		this.namenode = namenode;
	}

	public void setJobtracker(String jobtracker) {
		this.jobtracker = jobtracker;
	}

	public void setQueuename(String queuename) {
		this.queuename = queuename;
	}

	public void setLibpath(String libpath) {
		this.libpath = libpath;
	}

	public void setUseSystemLibpath(String useSystemLibpath) {
		this.useSystemLibpath = useSystemLibpath;
	}

	public void setWfRerunFailnodes(String wfRerunFailnodes) {
		this.wfRerunFailnodes = wfRerunFailnodes;
	}

	public void setProjectRoot(String projectRoot) {
		this.projectRoot = projectRoot;
	}

	public void setAppPath(String appPath) {
		this.appPath = appPath;
	}

	public void setOozieclientAppPath(String oozieclientAppPath) {
		this.oozieclientAppPath = oozieclientAppPath;
	}

	public void setInputdir(String inputdir) {
		this.inputdir = inputdir;
	}

	public void setOutputdir(String outputdir) {
		this.outputdir = outputdir;
	}

	public void setConf(Properties conf) {
		this.conf = conf;
	}

	
}