package com.sna_deportivo.pojo.deportes;

import com.sna_deportivo.utils.gr.ObjectSNSDeportivo;
import com.sna_deportivo.utils.gr.StringUtils;
import com.sna_deportivo.utils.gr.excepciones.AtributoInexistenteException;
import com.sna_deportivo.utils.json.JsonObject;
import com.sna_deportivo.utils.json.JsonUtils;

public class Deporte extends ObjectSNSDeportivo {
	
	private Integer id;
	private String nombre;
	private String descripcion;
	private String fechaCreacion;
	private String historia;
	private Boolean esOlimpico;
	
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
	protected String retornarToString(){
		return this.stringJson();
	}
	
	@Override
	public String stringJson() {
		return "{id:"+ ((this.id!=null)?this.id.toString():"null") +","
				+ "nombre:"+ JsonUtils.propiedadNula(
						StringUtils.codificar(this.nombre)) +","
				+ "descripcion:"+ JsonUtils.propiedadNula(
						StringUtils.codificar(this.descripcion)) +","
				+ "fechaCreacion:"+JsonUtils.propiedadNula(
						StringUtils.codificar(this.fechaCreacion)) +","
				+ "historia:"+JsonUtils.propiedadNula(
						StringUtils.codificar(this.historia))+","
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
	public boolean equals(Object obj){
		boolean igual = true;
		if(obj instanceof Deporte){
			Deporte deporte = (Deporte)obj;
			if(this.id != deporte.getId()){
				igual = false;
			}
			if(!((this.nombre != null &&
			   this.nombre.equals(deporte.getNombre())) ||
			   (this.nombre == null && deporte.getNombre() == null))){
				igual = false;
			}
			if(!((this.descripcion != null &&
			     this.descripcion.equals(deporte.getDescripcion())) ||
			    (this.descripcion == null && deporte.getDescripcion() == null))){
				igual = false;
			}
			if(!((this.fechaCreacion != null &&
				  this.fechaCreacion.equals(deporte.getFechaCreacion())) ||
				 (this.fechaCreacion == null && deporte.getFechaCreacion() == null))){
				igual = false;
			}
			if(!((this.historia != null &&
			      this.historia.equals(deporte.getHistoria())) ||
				 (this.historia == null && deporte.getHistoria() == null))){
				igual = false;
			}
			if(this.esOlimpico != deporte.getEsOlimpico()){
				igual = false;
			}
		}else{
			igual = false;
		}
		return igual;
	}

	@Override
	protected String get(String atributo) throws AtributoInexistenteException{
		if(this.esAtributo(atributo)){
			if(atributo.equals("id")){
				return this.getId().toString();
			}else if(atributo.equals("nombre")){
				return this.getNombre();
			}else if(atributo.equals("descripcion")){
				return this.getDescripcion();
			}else if(atributo.equals("fechaCreacion")){
				return this.getFechaCreacion();
			}else if(atributo.equals("historia")){
				return this.getHistoria();
			}else if(atributo.equals("esOlimpico")){
				return this.getEsOlimpico().toString();
			}
		}
		throw new AtributoInexistenteException();
	}
	
}
