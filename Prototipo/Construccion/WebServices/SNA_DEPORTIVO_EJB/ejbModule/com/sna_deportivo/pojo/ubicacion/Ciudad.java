package com.sna_deportivo.pojo.ubicacion;

import java.util.ArrayList;
import java.util.Map;

public class Ciudad {
	
	private int id;
	private String nombre;
	private ArrayList<LugarPractica> ubicaciones;
	private float latitud;
	private float longitud;
	public Ciudad(Map<String, Object[]> objeto) {
		id = Integer.valueOf((String) objeto.get("id")[0]);
		nombre = (String) objeto.get("nombre")[0];
		String[] coordenadas = ((String) objeto.get("coordenada")[0]).split(",");
		latitud = Float.valueOf(coordenadas[0]);
		longitud  = Float.valueOf(coordenadas[1]);
	}
	public Ciudad() {
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
	public ArrayList<LugarPractica> getUbicaciones() {
		return ubicaciones;
	}
	public void setUbicaciones(ArrayList<LugarPractica> ubicaciones) {
		this.ubicaciones = ubicaciones;
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
	
}
