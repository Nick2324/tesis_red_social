package com.sna_deportivo.pojo.evento;

import java.util.Date;

import com.sna_deportivo.pojo.general.ObjectSNSDeportivo;
import com.sna_deportivo.pojo.json.JsonObject;
import com.sna_deportivo.pojo.json.JsonSerializable;
import com.sna_deportivo.pojo.json.excepciones.ExcepcionJsonDeserializacion;
import com.sna_deportivo.utils.FechaSNS;
import com.sna_deportivo.utils.TiempoSNS;
import com.sna_deportivo.utils.Utils;

/**
 * 
 * Clase representante de eventos en la red social deportiva
 * 
 * @author Nicolas Mauricio Garcia Garzon
 * @version 1.0
 *
 */

public abstract class Evento implements ObjectSNSDeportivo,JsonSerializable {

	private String id;
	private String nombre;
	private String descripcion;
	private FechaSNS fechaCreacion;
	private FechaSNS fechaInicio;
	private FechaSNS fechaFinal;
	private TiempoSNS horaInicio;
	private TiempoSNS horaFinal;
	private Integer numMaxParticipantes;
	private Integer rangoMaxEdad;
	private Integer rangoMinEdad;
	private Boolean activo;

	/**
	 * 
	 * Constructor por defecto
	 * 
	 */
	public Evento(){
		this.fechaCreacion = new FechaSNS();
		this.fechaInicio = new FechaSNS();
		this.fechaFinal = new FechaSNS();
		this.horaInicio = new TiempoSNS();
		this.horaFinal = new TiempoSNS();
	}
	
	/**
	 * 
	 * Pone hora final del evento
	 * 
	 * @param horaFinal Hora final del evento
	 */
	public void setHoraFinal(String horaFinal){
		this.horaFinal.setHora(horaFinal);
	}
	
	/**
	 * 
	 * Pone la hora inicial del evento
	 * 
	 * @param horaInicio Hora inicial del evento
	 */
	public void setHoraInicio(String horaInicio){
		this.horaInicio.setHora(horaInicio);
	}
	
	/**
	 * 
	 * Pone de estado activo del evento
	 * 
	 * @param activo Valor para el estado activo del evento
	 */
	public void setActivo(boolean activo){
		this.activo = activo;
	}
	
	/**
	 * 
	 * Pone la fecha final del evento
	 * 
	 * @param fechaFinal Fecha final del evento
	 */
	public void setFechaFinal(String fechaFinal){
		this.fechaFinal.setFecha(fechaFinal);
	}
	
	/**
	 * 
	 * Pone la fecha inicial del evento
	 * 
	 * @param fechaInicio Fecha inicial del evento
	 */
	public void setFechaInicio(String fechaInicio){
		this.fechaInicio.setFecha(fechaInicio);
	}
	
	/**
	 * 
	 * Pone la fecha de creacion del evento
	 * 
	 * @param fechaCreacion Fecha de creacion del evento
	 */
	public void setFechaCreacion(String fechaCreacion){
		this.fechaCreacion.setFecha(fechaCreacion);
	}
	
	/**
	 * 
	 * Pone la descripcion del evento
	 * 
	 * @param descripcion Descripcion del evento
	 */
	public void setDescripcion(String descripcion){
		this.descripcion = descripcion;
	}
	
	/**
	 * 
	 * Pone el nombre del evento
	 * 
	 * @param nombre Nombre del evento
	 */
	public void setNombre(String nombre){
		this.nombre = nombre;
	}
	
	/**
	 * 
	 * Pone el identificador del evento
	 * 
	 * @param id Identificador del evento
	 */
	public void setId(String id){
		this.id = id;
	}
	
	/**
	 * Pone el numero maximo de participantes del evento
	 * @param numMaxParticipantes Numero maximo de participantes
	 */
	public void setNumMaxParticipantes(int numMaxParticipantes){
		this.numMaxParticipantes = numMaxParticipantes;
	}
	
	/**
	 * Pone el rango minimo de edad del evento
	 * @param rangoMinEdad rango minimo de edad
	 */
	public void setRangoMinEdad(int rangoMinEdad){
		this.rangoMinEdad = rangoMinEdad;
	}
	
	/**
	 * Pone el rango maximo de edad del evento
	 * @param rangoMaxEdad rango maximo de edad
	 */
	public void setRangoMaxEdad(int rangoMaxEdad){
		this.rangoMaxEdad = rangoMaxEdad;
	}
	
	/**
	 * Retorna el numero maximo de participantes del evento
	 * @return Numero maximo de participantes
	 */
	public Integer getNumMaxParticipantes(){
		return this.numMaxParticipantes;
	}
	
	/**
	 * Retorna el rango maximo de edad del evento
	 * @return Rango maximo de edad
	 */
	public Integer getRangoMaxEdad(){
		return this.rangoMaxEdad;
	}
	
	/**
	 * Retorna el rango minimo de edad del evento
	 * @return Rango minimo de edad
	 */
	public Integer getRangoMinEdad(){
		return this.rangoMinEdad;
	}
	
	/**
	 * 
	 * Obtiene la hora final del evento
	 * 
	 * @return Hora final del evento
	 */
	public String getHoraFinal(){
		return this.horaFinal.toString();
	}
	
	/**
	 * 
	 * Obtiene la hora inicial del evento
	 * 
	 * @return Hora inicial del evento
	 */
	
	public String getHoraInicio(){
		return this.horaInicio.toString();
	}
	
	/**
	 * 
	 * Obtiene el estado activo del evento
	 * 
	 * @return Estado activo del evento
	 */
	
	public Boolean getActivo(){
		return this.activo;
	}
	
	/**
	 * 
	 * Obtiene la fecha final del evento
	 * 
	 * @return Fecha final del evento
	 */
	
	public String getFechaFinal(){
		return this.fechaFinal.toString();
	}
	
	/**
	 * 
	 * Obtiene la fecha de inicio del evento
	 * 
	 * @return Fecha inicial del evento
	 */
	
	public String getFechaInicio(){
		return this.fechaInicio.toString();
	}
	
	/**
	 * 
	 * Obtiene la fecha de creacion del evento
	 * 
	 * @return Fecha de creacion del evento
	 */
	
	public String getFechaCreacion(){
		return this.fechaCreacion.toString();
	}
	
	/**
	 * 
	 * Obtiene la descripcion del evento
	 * 
	 * @return Descripcion del evento
	 */
	
	public String getDescripcion(){
		return this.descripcion;
	}
	
	/**
	 * 
	 * Obtiene el nombre del evento
	 * 
	 * @return Nombre del evento
	 */
	
	public String getNombre(){
		return this.nombre;
	}
	
	/**
	 * 
	 * Obtiene el identificador del evento
	 * 
	 * @return Identificador del evento
	 */
	
	public String getId(){
		return this.id;
	}
	
	/**
	 * 
	 * Compara un objeto de tipo Evento con el mismo contra su estado actual.
	 * Se ignoran los estados nulos del objeto evento parametro y se comparan solo
	 * aquellos que no lo son.
	 * 
	 * @param obj Evento a comparar
	 * @return Falso si los estados de ambos objetos difieren. Vedadero de otro modo
	 */
	
	@Override
	public boolean equals(Object obj){
		boolean retorno = false;
		if(obj instanceof Evento){
			Evento e = (Evento)obj;
			retorno = true;
			if(e.getId() != null && retorno)
			   if(e.getId() != this.id)
				   retorno = false;
			if(retorno && e.getDescripcion() != null)
			   if(e.getDescripcion() != this.descripcion)
				   retorno = false;
			if(retorno && e.getFechaCreacion() != null)
			   if(e.getFechaCreacion().equals(this.fechaCreacion.toString()))
				   retorno = false;
			if(retorno && e.getFechaInicio() != null)
			   if(e.getFechaInicio().equals(this.fechaInicio.toString()))
				   retorno = false;
			if(retorno && e.getFechaFinal() != null)
			   if(e.getFechaFinal().equals(this.fechaFinal.toString()))
				   retorno = false;
			if(retorno && e.getNombre() != null)
			   if(e.getNombre() != this.nombre)
				   retorno = false;
			if(retorno && e.getHoraInicio() != null)
				if(e.getHoraInicio().equals(this.horaInicio.toString()))
					retorno = false;
			if(retorno && e.getHoraFinal() != null)
				if(e.getHoraFinal().equals(this.horaFinal.toString()))
				    retorno = false;
			if(retorno && e.getActivo() != null)
				if (e.getActivo() != this.activo)
					retorno = false;
			if(retorno && e.getRangoMaxEdad() != null)
				if (e.getRangoMaxEdad() != this.rangoMaxEdad)
					retorno = false;
			if(retorno && e.getRangoMinEdad() != null)
				if (e.getRangoMinEdad() != this.rangoMinEdad)
					retorno = false;
			if(retorno && e.getRangoMaxEdad() != null)
				if (e.getRangoMaxEdad() != this.rangoMaxEdad)
					retorno = false;
		}
		
		return retorno;
	}
	
	/**
	 * Retorna JSON del Evento
	 * @return String JSON del Evento
	 */
	@Override
	public String toString(){
		return "{id:"+this.id+","+
				"nombre:"+this.nombre+","+
				"descripcion:"+this.descripcion+","+
				"fechaCreacion:"+this.fechaCreacion+","+
				"fechaInicio:"+this.fechaInicio+","+
				"fechaFinal:"+this.fechaFinal+","+
				"horaInicio:"+this.horaInicio+","+
				"horaFinal:"+this.horaFinal+","+
				"numMaxParticipantes:"+this.numMaxParticipantes+","+
				"rangoMaxEdad:"+this.rangoMaxEdad+","+
				"rangoMinEdad:"+this.rangoMinEdad+","+
				"activo:"+this.activo+"}";
	}
	
	@Override
	public String stringJson(){
		return "{id:"+this.id+","+
				"nombre:"+this.nombre+","+
				"descripcion:"+this.descripcion+","+
				"fechaCreacion:"+this.fechaCreacion+","+
				"fechaInicio:"+this.fechaInicio+","+
				"fechaFinal:"+this.fechaFinal+","+
				"horaInicio:"+this.horaInicio+","+
				"horaFinal:"+this.horaFinal+","+
				"numMaxParticipantes:"+this.numMaxParticipantes+","+
				"rangoMaxEdad:"+this.rangoMaxEdad+","+
				"rangoMinEdad:"+this.rangoMinEdad+","+
				"activo:"+this.activo+"}";
	}
	
	/**
	 * Muestra si la string ingresada es o no un atributo
	 * 
	 * @param atributo String que representa el nombre del atributo
	 * @return boolean True si es atributo o false si no lo es
	 */
	protected boolean esAtributo(String atributo){
		if(atributo.equals("id") ||
		   atributo.equals("nombre")||
		   atributo.equals("descripcion") ||
		   atributo.equals("fechaCreacion")||
		   atributo.equals("fechaInicio")||
		   atributo.equals("fechaFinal")||
		   atributo.equals("horaInicio")||
		   atributo.equals("horaFinal")||
		   atributo.equals("numMaxParticipantes")||
		   atributo.equals("rangoMaxEdad")||
		   atributo.equals("rangoMinEdad")||
		   atributo.equals("activo"))
			return true;
		
		return false;
		
	}
	
	protected boolean setAtributo(String atributo,Object[] valor) throws ExcepcionJsonDeserializacion{
		boolean asignado = false;
		if(atributo.equals("id")){
			this.setId((String)valor[0]);
			asignado = true;
		}else if(atributo.equals("nombre[0]")){
			this.setNombre((String)valor[0]);
			asignado = true;
		}else if(atributo.equals("descripcion")){
			this.setDescripcion((String)valor[0]);
			asignado = true;
		}else if(atributo.equals("fechaCreacion")){
			this.setFechaCreacion((String)valor[0]);
			asignado = true;
		}else if(atributo.equals("fechaInicio")){
			this.setFechaInicio((String)valor[0]);
			asignado = true;
		}else if(atributo.equals("fechaFinal")){
			this.setFechaFinal((String)valor[0]);
			asignado = true;
		}else if(atributo.equals("horaInicio")){
			this.setHoraInicio((String)valor[0]);
			asignado = true;
		}else if(atributo.equals("horaFinal")){
			this.setNumMaxParticipantes(Integer.parseInt((String)valor[0]));
			asignado = true;
		}else if(atributo.equals("numMaxParticipantes")){
			this.setNumMaxParticipantes(Integer.parseInt((String)valor[0]));
			asignado = true;
		}else if(atributo.equals("rangoMaxEdad")){
			this.setRangoMaxEdad(Integer.parseInt((String)valor[0]));
			asignado = true;
		}else if(atributo.equals("rangoMinEdad")){
			this.setRangoMinEdad(Integer.parseInt((String)valor[0]));
			asignado = true;
		}else if(atributo.equals("activo")){
			this.setActivo(Boolean.parseBoolean((String)valor[0]));
			asignado = true;
		}
		
		return asignado;
		
	}
	
	@Override
	public void deserializarJson(JsonObject json) throws ExcepcionJsonDeserializacion {
		for(String propiedad:json.getPropiedades().keySet()){
			if(!(this.esAtributo(propiedad) && 
				 this.setAtributo(propiedad,json.getPropiedades().get(propiedad))))
				throw new ExcepcionJsonDeserializacion();
		}
	}
	
	@Override
	public JsonObject serializarJson() {
		return Utils.JsonStringToObject(
				"{id:"+this.id+","+
				"nombre:"+this.nombre+","+
				"descripcion:"+this.descripcion+","+
				"fechaCreacion:"+this.fechaCreacion+","+
				"fechaInicio:"+this.fechaInicio+","+
				"fechaFinal:"+this.fechaFinal+","+
				"horaInicio:"+this.horaInicio+","+
				"horaFinal:"+this.horaFinal+","+
				"numMaxParticipantes:"+this.numMaxParticipantes+","+
				"rangoMaxEdad:"+this.rangoMaxEdad+","+
				"rangoMinEdad:"+this.rangoMinEdad+","+
				"activo:"+this.activo+"}");
	}
	
}
