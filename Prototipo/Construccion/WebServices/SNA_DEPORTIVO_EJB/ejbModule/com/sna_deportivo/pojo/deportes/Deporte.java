package com.sna_deportivo.pojo.deportes;

import com.sna_deportivo.utils.gr.ObjectSNSDeportivo;
import com.sna_deportivo.utils.gr.StringUtils;
import com.sna_deportivo.utils.json.JsonObject;
import com.sna_deportivo.utils.json.JsonSerializable;
import com.sna_deportivo.utils.json.JsonUtils;
import com.sna_deportivo.utils.json.excepciones.ExcepcionJsonDeserializacion;

public class Deporte implements ObjectSNSDeportivo, JsonSerializable{
	
	private Integer id;
	private String nombre;
	private String descripcion;
	private String fechaCreacion;
	private String historia;
	private Boolean esOlimpico;
	private String aRetornar;
	
	public Deporte(){}

	public Deporte(JsonObject object) {
		id = Integer.valueOf((String) object.getPropiedades().get("id")[0]);
		nombre = (String) object.getPropiedades().get("nombre")[0];
		descripcion = (String) object.getPropiedades().get("descripcion")[0];
		fechaCreacion = (String) object.getPropiedades().get("fechaCreacion")[0];
		historia = (String) object.getPropiedades().get("historia")[0];
		esOlimpico = ((String) object.getPropiedades().get("nombre")[0]).equals("true");
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public Boolean getEsOlimpico() {
		return esOlimpico;
	}

	public void setEsOlimpico(Boolean esOlimpico) {
		this.esOlimpico = esOlimpico;
	}

	@Override
	public String toString(){
		if(this.aRetornar != null && 
				aRetornar.equals("nombre"))
			return this.getNombre();
		return this.stringJson();
	}
	
	@Override
	public String stringJson() {
		return "{id:"+ this.id.toString() +","
				+ "nombre:"+ JsonUtils.propiedadNula(this.nombre) +","
				+ "descripcion:"+ JsonUtils.propiedadNula(this.descripcion) +","
				+ "fechaCreacion:"+JsonUtils.propiedadNula(this.fechaCreacion) +","
				+ "historia:"+JsonUtils.propiedadNula(this.historia)+","
				+ "esOlimpico:"+this.esOlimpico+"}";
	}

	@Override
	public void setNullObject() {
		this.setId(null);
		this.setNombre(null);
		this.setDescripcion(null);
		this.setFechaCreacion(null);
		this.setHistoria(null);
		this.setEsOlimpico(null);
	}

	@Override
	public JsonObject serializarJson() {
		return JsonUtils.JsonStringToObject(this.stringJson());
	}

	@Override
	public void deserializarJson(JsonObject json) throws ExcepcionJsonDeserializacion {
		this.setNullObject();
		for(String propiedad:json.getPropiedades().keySet()){
			if(!(this.esAtributo(propiedad) && 
				 this.setAtributo(propiedad,json.getPropiedades().get(propiedad))))
				throw new ExcepcionJsonDeserializacion();
		}
	}
	
	protected boolean setAtributo(String atributo, Object[] valor){
		boolean asignado = false;
		if(atributo.equals("id")){
			this.setId((((String)valor[0]).equals("null"))?null:
				Integer.parseInt((String)valor[0]));
			asignado = true;
		}else if(atributo.equals("nombre")){
			this.setNombre((String)valor[0]);
			asignado = true;
		}else if(atributo.equals("descripcion")){
			this.setDescripcion(StringUtils.decodificar((String)valor[0]));
			asignado = true;
		}else if(atributo.equals("fechaCreacion")){
			this.setFechaCreacion((String)valor[0]);
			asignado = true;
		}else if(atributo.equals("historia")){
			this.setHistoria(StringUtils.decodificar((String)valor[0]));
			asignado = true;
		}else if(atributo.equals("esOlimpico")){
			this.setEsOlimpico((((String)valor[0]).equals("null"))?null:
							Boolean.parseBoolean((String)valor[0]));
			asignado = true;
		}
		
		return asignado;
		
	}
	
	protected boolean esAtributo(String atributo){
		if(atributo.equals("id") ||
		   atributo.equals("nombre") ||
		   atributo.equals("descripcion") ||
		   atributo.equals("fechaCreacion") ||
		   atributo.equals("historia") ||
		   atributo.equals("esOlimpico"))
		   return true;
		
		return false;
	
	}

	@Override
	public void retornoToString(String aRetornar) {
		this.aRetornar = aRetornar;
	}

}
