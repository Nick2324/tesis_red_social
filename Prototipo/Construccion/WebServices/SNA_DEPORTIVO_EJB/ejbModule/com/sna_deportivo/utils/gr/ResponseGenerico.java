package com.sna_deportivo.utils.gr;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResponseGenerico {
	
	private String caracterAceptacion;
	private String mensajeRespuesta;
	private String datosExtra;
	
	public ResponseGenerico(){}
	
	public ResponseGenerico(String caracterAceptacion,
							String mensajeRespuesta){
		this.caracterAceptacion = caracterAceptacion;
		this.mensajeRespuesta = mensajeRespuesta;
	}
	
	public ResponseGenerico(String caracterAceptacion,
							String mensajeRespuesta,
							String datosExtra){
		this.caracterAceptacion = caracterAceptacion;
		this.mensajeRespuesta = mensajeRespuesta;
		this.datosExtra = datosExtra;
	}

	
	public String getCaracterAceptacion() {
		return caracterAceptacion;
	}
	public void setCaracterAceptacion(String caracterAceptacion) {
		this.caracterAceptacion = caracterAceptacion;
	}
	public String getMensajeRespuesta() {
		return mensajeRespuesta;
	}
	public void setMensajeRespuesta(String mesnajeRespuesta) {
		this.mensajeRespuesta = mesnajeRespuesta;
	}
	public String getDatosExtra() {
		return datosExtra;
	}
	public void setDatosExtra(String datosExtra) {
		this.datosExtra = datosExtra;
	}
	
}
