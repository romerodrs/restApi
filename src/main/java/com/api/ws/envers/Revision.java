package com.api.ws.envers;


import java.util.Date;

import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

import javax.persistence.Entity;
/**
 * Created by geci on 29/06/2016.
 */
@Entity
@RevisionEntity(CustomRevisionListener.class)
public class Revision extends DefaultRevisionEntity {

	private static final long serialVersionUID = 1L;
	private String userName;
	private String remoteIp;
	private Date date;

	public Date getDate(){
		return date;
	}
	public void setDate(Date d){
		this.date = d;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getRemoteIp() {
		return remoteIp;
	}

	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}

}

