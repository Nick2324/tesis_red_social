package com.sna_deportivo.pojo.usuarios;

import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Rol {
	
	private String nombre;
	private String descripcion;
	private int consecutivoRol;
	
	public Rol(Map<String,Object[]> objeto) {
		nombre = (String) objeto.get("nombre")[0];
		descripcion = (String) objeto.get("descripcion")[0];
		consecutivoRol = Integer.valueOf(((String) objeto.get("consecutivoRol")[0]));
	}
	
	public Rol() {
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getConsecutivoRol() {
		return consecutivoRol;
	}
	public void setConsecutivoRol(int consecutivoRol) {
		this.consecutivoRol = consecutivoRol;
	}
	
	

}
