package com.api.ws.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Users")
public class Users {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="user_id")
    private int userId;
	@Column(name="user_name")
    private String userName;
	@Column(name="password")
    private String password;
	
	public Users(){};
	
    public Users(int id, String userName, String pasword){
    	this.userId = id;
    	this.userName = userName;
    	this.password = pasword;
    }
    public int getUserId() {
		return userId;
	}
	public String getUserName() {
		return userName;
	}
	public String getUserPassword() {
		return password;
	}
	public void setUserId(int userId) {
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
