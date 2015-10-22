package com.sna_deportivo.pojo.ubicacion;

import java.util.Map;

import com.sna_deportivo.pojo.deportes.Deporte;

public class LugarPractica {
	
	private int id;
	private String nombre;
	private float latitud;
	private float longitud;
	
	private Deporte[] deportesPracticados;
	
	public LugarPractica() {
	}
	public LugarPractica(Map<String, Object[]> objeto) {
		id = Integer.valueOf((String) objeto.get("id")[0]);
		nombre = (String) objeto.get("nombre")[0];
		String[] coordenadas = ((String) objeto.get("coordenada")[0]).split(",");
		latitud = Float.valueOf(coordenadas[0]);
		longitud  = Float.valueOf(coordenadas[1]);
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
	public float getLatitud() {
		return latitud;
	}
	public void setLatitud(float latitud) {
		this.latitud = latitud;
	}
	public float getLongitud() {
		return longitud;
	}
	public void setLongitud(float longitud) {
		this.longitud = longitud;
	}
	
	public Deporte[] getDeportesPracticados() {
		return deportesPracticados;
	}
	
	public void setDeportesPracticados(Deporte[] deportesPracticados) {
		this.deportesPracticados = deportesPracticados;
	}
	
	@Override
	public String toString() {
		StringBuilder retorno = new StringBuilder("");
		retorno.append("{\"id\": " + id + ",");
		retorno.append("\"nombre\": \"" + nombre + "\",");
		retorno.append("\"coordenada\": \"" + latitud + "," + longitud + "\"}");
		return retorno.toString();
	}
	
}
