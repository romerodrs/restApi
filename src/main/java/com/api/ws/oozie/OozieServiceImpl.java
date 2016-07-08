package com.api.ws.oozie;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.oozie.client.OozieClient;
import org.apache.oozie.client.OozieClientException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "oozie")
@PropertySource("classpath:oozie.properties")
public class OozieServiceImpl implements OozieService{

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
	private Properties properties;
	
	private static Logger logger = Logger.getLogger(OozieServiceImpl.class);
	
    @Override
    public String executeOozieJob() throws OozieClientException  {
    	logger.info("[Webservice Ozzie] Starting Job execution...");
        OozieClient oozieClient = new OozieClient(url);
        this.setProperties(oozieClient);
        logger.debug("[Webservice Ozzie] " + properties.toString());
        return oozieClient.run(properties);
    }
    
    @Override
    public String oozieJobStatus(String jobId) throws OozieClientException{
    	logger.info("[Webservice Ozzie] Checking Job {"+ jobId +"} status...");
    	OozieClient oozieClient = new OozieClient(url);
    	this.setProperties(oozieClient);
    	logger.debug("[Webservice Ozzie] " + properties.toString());
    	return oozieClient.getStatus(jobId);
    }
    
	private void setProperties(OozieClient wc) {
		properties = wc.createConfiguration();
        properties.setProperty("nameNode", namenode);
        properties.setProperty("jobTracker", jobtracker);
        properties.setProperty("queueName", queuename);
        properties.setProperty("oozie.libpath", libpath);
        properties.setProperty("oozie.use.system.libpath", useSystemLibpath);
        properties.setProperty("oozie.wf.rerun.failnodes", wfRerunFailnodes);
        properties.setProperty("oozieProjectRoot", projectRoot);
        properties.setProperty("appPath", appPath);
        properties.setProperty(OozieClient.APP_PATH, oozieclientAppPath);
        properties.setProperty("inputDir", inputdir );
        properties.setProperty("outputDir", outputdir);
	}

	@Override
	public String toString() {
		return "OozieApp [url=" + url + ", namenode=" + namenode
				+ ", jobtracker=" + jobtracker + ", queuename=" + queuename
				+ ", libpath=" + libpath + ", useSystemLibpath="
				+ useSystemLibpath + ", wfRerunFailnodes=" + wfRerunFailnodes
				+ ", projectRoot=" + projectRoot + ", appPath=" + appPath
				+ ", oozieclientAppPath=" + oozieclientAppPath + ", inputdir="
				+ inputdir + ", outputdir=" + outputdir + ", conf=" + properties
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

	public Properties getProperties() {
		return properties;
	}

	
}