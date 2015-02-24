package com.sna_deportivo.pojo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResponseGenerico {
	
	private String caracterAceptacion;
	private String mesnajeRespuesta;
	
	public String getCaracterAceptacion() {
		return caracterAceptacion;
	}
	public void setCaracterAceptacion(String caracterAceptacion) {
		this.caracterAceptacion = caracterAceptacion;
	}
	public String getMesnajeRespuesta() {
		return mesnajeRespuesta;
	}
	public void setMesnajeRespuesta(String mesnajeRespuesta) {
		this.mesnajeRespuesta = mesnajeRespuesta;
	}
	
}
