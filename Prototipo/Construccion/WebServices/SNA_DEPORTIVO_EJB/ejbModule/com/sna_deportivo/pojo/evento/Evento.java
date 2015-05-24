package com.sna_deportivo.pojo.evento;

import java.util.Date;

/**
 * 
 * Clase representante de eventos en la red social deportiva
 * 
 * @author Nicolas Mauricio Garcia Garzon
 * @version 1.0
 *
 */

public abstract class Evento {

	private String id;
	private String nombre;
	private String descripcion;
	private Date fechaCreacion;
	private Date fechaInicio;
	private Date fechaFinal;
	private Date horaInicio;
	private Date horaFinal;
	private Integer numMaxParticipantes;
	private Integer rangoMaxEdad;
	private Integer rangoMinEdad;
	private Boolean activo;

	/**
	 * 
	 * Constructor por defecto
	 * 
	 */
	public Evento(){}
	
	/**
	 * 
	 * Pone hora final del evento
	 * 
	 * @param horaFinal Hora final del evento
	 */
	public void setHoraFinal(Date horaFinal){
		this.horaFinal = horaFinal;
	}
	
	/**
	 * 
	 * Pone la hora inicial del evento
	 * 
	 * @param horaInicio Hora inicial del evento
	 */
	public void setHoraInicio(Date horaInicio){
		this.horaInicio = horaInicio;
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
	public void setFechaFinal(Date fechaFinal){
		this.fechaFinal = fechaFinal;
	}
	
	/**
	 * 
	 * Pone la fecha inicial del evento
	 * 
	 * @param fechaInicio Fecha inicial del evento
	 */
	public void setFechaInicio(Date fechaInicio){
		this.fechaInicio = fechaInicio;
	}
	
	/**
	 * 
	 * Pone la fecha de creacion del evento
	 * 
	 * @param fechaCreacion Fecha de creacion del evento
	 */
	public void setFechaCreacion(Date fechaCreacion){
		this.fechaCreacion = fechaCreacion;
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
	public Date getHoraFinal(){
		return this.horaFinal;
	}
	
	/**
	 * 
	 * Obtiene la hora inicial del evento
	 * 
	 * @return Hora inicial del evento
	 */
	
	public Date getHoraInicio(){
		return this.horaInicio;
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
	
	public Date getFechaFinal(){
		return this.fechaFinal;
	}
	
	/**
	 * 
	 * Obtiene la fecha de inicio del evento
	 * 
	 * @return Fecha inicial del evento
	 */
	
	public Date getFechaInicio(){
		return this.fechaInicio;
	}
	
	/**
	 * 
	 * Obtiene la fecha de creacion del evento
	 * 
	 * @return Fecha de creacion del evento
	 */
	
	public Date getFechaCreacion(){
		return this.fechaCreacion;
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
			   if(e.getId() == this.id)
				   retorno = true;
			   else
				   retorno = false;
			if(e.getDescripcion() != null && retorno)
			   if(e.getDescripcion() == this.descripcion)
				   retorno = true;
			   else
				   retorno = false;
			if(e.getFechaCreacion() != null && retorno)
			   if(e.getFechaCreacion() == this.fechaCreacion)
				   retorno = true;
			   else
				   retorno = false;
			if(e.getFechaInicio() != null && retorno)
			   if(e.getFechaInicio() == this.fechaInicio)
				   retorno = true;
			   else
				   retorno = false;
			if(e.getFechaFinal() != null && retorno)
			   if(e.getFechaFinal() == this.fechaFinal)
				   retorno = true;
			   else
				   retorno = false;
			if(e.getNombre() != null && retorno)
			   if(e.getNombre() == this.nombre)
				   retorno = true;
			   else
				   retorno = false;
			if(e.getHoraInicio() != null && retorno)
				if(e.getHoraInicio() == this.horaInicio)
					retorno = true;
				else
					retorno = false;
			if(e.getHoraFinal() != null && retorno)
				if(e.getHoraFinal() == this.horaFinal)
					retorno = true;
				else
				    retorno = false;
			if(retorno && e.getActivo() != null)
				if (e.getActivo() == this.activo)
					retorno = true;
				else
					retorno = false;
			if(retorno && e.getRangoMaxEdad() != null)
				if (e.getRangoMaxEdad() == this.rangoMaxEdad)
					retorno = true;
				else
					retorno = false;
			if(retorno && e.getRangoMinEdad() != null)
				if (e.getRangoMinEdad() == this.rangoMinEdad)
					retorno = true;
				else
					retorno = false;
			if(retorno && e.getRangoMaxEdad() != null)
				if (e.getRangoMaxEdad() == this.rangoMaxEdad)
					retorno = true;
				else
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
	
}
