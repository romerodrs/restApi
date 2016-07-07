package com.api.ws.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

@Entity
@Table(name = "Users")
@Audited
@AuditTable("Users_hist")
public class Users {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="user_id")
    private long userId;
	@Column(name="user_name")
    private String userName;
	@Column(name="password")
    private String password;
	
	public Users(){};
	
    public Users(long id, String userName, String pasword){
    	this.userId = id;
    	this.userName = userName;
    	this.password = pasword;
    }
    public long getUserId() {
		return userId;
	}
	public String getUserName() {
		return userName;
	}
	public String getUserPassword() {
		return password;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setUserPassword(String userPassword) {
		this.password = userPassword;
	}
	@Override
	public String toString() {
		return "Users [userId=" + userId + ", userName=" + userName
				+ ", userPassword=" + password + "]";
	}
    
}
