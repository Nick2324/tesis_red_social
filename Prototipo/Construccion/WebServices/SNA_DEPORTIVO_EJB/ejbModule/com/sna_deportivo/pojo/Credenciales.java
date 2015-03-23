package com.sna_deportivo.pojo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Credenciales {
	
	private String user;
	private String password;
	
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
