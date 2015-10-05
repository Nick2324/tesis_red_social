package com.sna_deportivo.pojo.ubicacion;

import java.util.ArrayList;
import java.util.Map;

public class Pais {
	
	private int id;
	private String nombre;
	private ArrayList<Ciudad> ciudades; 
	private float latitud;
	private float longitud;
	
	public Pais() {
	}
	
	public Pais(Map<String, Object[]> objeto) {
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
	public ArrayList<Ciudad> getCiudades() {
		return ciudades;
	}
	public void setCiudades(ArrayList<Ciudad> ciudades) {
		this.ciudades = ciudades;
	}
	

}
