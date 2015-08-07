package com.sna_deportivo.pojo.usuarios;

import com.sna_deportivo.utils.json.JsonObject;

public class Permiso {
	
	private String nombre;
	private String descripcion;
	private String ruta;
	private int consecutivoPermiso;
	
	public Permiso() {}
	
	public Permiso(JsonObject object){
		nombre = (String) object.getPropiedades().get("nombre")[0];
		descripcion = (String) object.getPropiedades().get("descripcion")[0];
		ruta = (String) object.getPropiedades().get("ruta")[0];
		consecutivoPermiso = Integer.valueOf((String) object.getPropiedades().get("consecutivoPermiso")[0]);
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
	public String getRuta() {
		return ruta;
	}
	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	public int getConsecutivoPermiso() {
		return consecutivoPermiso;
	}
	public void setConsecutivoPermiso(int consecutivoPermiso) {
		this.consecutivoPermiso = consecutivoPermiso;
	}
	@Override
	public String toString() {
		StringBuilder object = new StringBuilder();
		object.append("{");
		object.append("nombre:\"" + nombre + "\",");
		object.append("descripcion:\"" + descripcion + "\",");
		object.append("ruta:\"" + ruta + "\",");
		object.append("consecutivoPermiso:" + consecutivoPermiso + ",");
		object.append("}");
		return null;
	}
	
	

}
