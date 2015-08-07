package com.sna_deportivo.pojo.deportes;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DeportePracticadoUsuario {
	
	private DeportePracticado deportePracticado;
	private String user;
	
	public DeportePracticadoUsuario(){}
	
	public DeportePracticado getDeportePracticado() {
		return deportePracticado;
	}
	public void setDeportePracticado(DeportePracticado deportePracticado) {
		this.deportePracticado = deportePracticado;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	
	

}
