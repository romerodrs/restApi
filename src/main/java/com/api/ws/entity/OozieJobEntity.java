package com.api.ws.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

/**
 * Created by DLRR
 */
@Entity
@Table(name = "oozie_job")
@Audited
@AuditTable("oozie_job_hist")
public class OozieJobEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="job_id")
    private long jobId;
	@Column(name="job_status")
    private String jobStatus;
	@Column(name="oozie_job_id")
    private String oozieJobId;
	
	public OozieJobEntity(){};
	
    public OozieJobEntity(long jobId, String jobStatus){
    	this.jobId = jobId;
    	this.jobStatus = jobStatus;
    }

	public long getJobId() {
		return jobId;
	}

	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobId(long jobId) {
		this.jobId = jobId;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	public String getOozieJobId() {
		return oozieJobId;
	}

	public void setOozieJobId(String oozieJobId) {
		this.oozieJobId = oozieJobId;
	}

	@Override
	public String toString() {
		return "OozieJobEntity [jobId=" + jobId + ", jobStatus=" + jobStatus
				+ ", oozieJobId=" + oozieJobId + "]";
	}

}
