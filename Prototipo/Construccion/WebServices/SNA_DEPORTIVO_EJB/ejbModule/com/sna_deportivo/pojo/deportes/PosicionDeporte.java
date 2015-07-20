package com.sna_deportivo.pojo.deportes;

import com.sna_deportivo.utils.json.JsonObject;

public class PosicionDeporte {
	
	private int id;
	private String nombre;
	private String descripcion;

	public PosicionDeporte(JsonObject object) {
		this.id = Integer.valueOf((String)object.getPropiedades().get("id")[0]);
		this.nombre = (String)object.getPropiedades().get("nombre")[0];
		this.descripcion = (String)object.getPropiedades().get("descripcion")[0];
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

}
