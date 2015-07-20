package com.sna_deportivo.pojo.deportes;

import com.sna_deportivo.utils.json.JsonObject;

public class Deporte {
	
	private int id;
	private String nombre;
	private String descripcion;
	private String fechaCreacion;
	private String historia;
	private boolean esOlimpico;

	public Deporte(JsonObject object) {
		id = Integer.valueOf((String) object.getPropiedades().get("id")[0]);
		nombre = (String) object.getPropiedades().get("nombre")[0];
		descripcion = (String) object.getPropiedades().get("descripcion")[0];
		fechaCreacion = (String) object.getPropiedades().get("fechaCreacion")[0];
		historia = (String) object.getPropiedades().get("historia")[0];
		esOlimpico = ((String) object.getPropiedades().get("nombre")[0]).equals("true");
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

	public String getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getHistoria() {
		return historia;
	}

	public void setHistoria(String historia) {
		this.historia = historia;
	}

	public boolean getEsOlimpico() {
		return esOlimpico;
	}

	public void setEsOlimpico(boolean esOlimpico) {
		this.esOlimpico = esOlimpico;
	}

}
