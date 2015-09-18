package com.sna_deportivo.pojo.evento;

import com.sna_deportivo.utils.bd.FechaSNS;
import com.sna_deportivo.utils.bd.TiempoSNS;
import com.sna_deportivo.utils.gr.ObjectSNSDeportivo;
import com.sna_deportivo.utils.gr.StringUtils;
import com.sna_deportivo.utils.gr.excepciones.AtributoInexistenteException;
import com.sna_deportivo.utils.json.JsonUtils;

/**
 * 
 * Clase representante de eventos en la red social deportiva
 * 
 * @author Nicolas Mauricio Garcia Garzon
 * @version 1.0
 *
 */

public class Evento extends ObjectSNSDeportivo {

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
	public Evento(){}
	
	/**
	 * 
	 * Pone hora final del evento
	 * 
	 * @param horaFinal Hora final del evento
	 */
	public void setHoraFinal(String horaFinal){
		if(horaFinal != null){
			if(this.horaFinal == null)
				this.horaFinal = new TiempoSNS();
			this.horaFinal.setHora(horaFinal);
		}else{
			this.horaFinal = null;
		}
	}
	
	/**
	 * 
	 * Pone la hora inicial del evento
	 * 
	 * @param horaInicio Hora inicial del evento
	 */
	public void setHoraInicio(String horaInicio){
		if(horaInicio != null){
			if(this.horaInicio == null)
				this.horaInicio = new TiempoSNS();
			this.horaInicio.setHora(horaInicio);
		}else{
			this.horaInicio = null;
		}
	}
	
	/**
	 * 
	 * Pone de estado activo del evento
	 * 
	 * @param activo Valor para el estado activo del evento
	 */
	public void setActivo(Boolean activo){
		this.activo = activo;
	}
	
	/**
	 * 
	 * Pone la fecha final del evento
	 * 
	 * @param fechaFinal Fecha final del evento
	 */
	public void setFechaFinal(String fechaFinal){
		if(fechaFinal != null){
			if(this.fechaFinal == null)
				this.fechaFinal = new FechaSNS();
			this.fechaFinal.setFecha(fechaFinal);
		}else{
			this.fechaFinal = null;
		}
	}
	
	/**
	 * 
	 * Pone la fecha inicial del evento
	 * 
	 * @param fechaInicio Fecha inicial del evento
	 */
	public void setFechaInicio(String fechaInicio){
		if(fechaInicio != null){
			if(this.fechaInicio == null)
				this.fechaInicio = new FechaSNS();
			this.fechaInicio.setFecha(fechaInicio);
		}else{
			this.fechaInicio = null;
		}
	}
	
	/**
	 * 
	 * Pone la fecha de creacion del evento
	 * 
	 * @param fechaCreacion Fecha de creacion del evento
	 */
	public void setFechaCreacion(String fechaCreacion){
		if(fechaCreacion != null){
			if(this.fechaCreacion == null)
				this.fechaCreacion = new FechaSNS();
			this.fechaCreacion.setFecha(fechaCreacion);
		}else{
			this.fechaCreacion = null;
		}
	}
	
	/**
	 * 
	 * Pone la descripcion del evento
	 * 
	 * @param descripcion Descripcion del evento
	 */
	public void setDescripcion(String descripcion){
		this.descripcion = StringUtils.codificar(descripcion);
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
	public void setNumMaxParticipantes(Integer numMaxParticipantes){
		this.numMaxParticipantes = numMaxParticipantes;
	}
	
	/**
	 * Pone el rango minimo de edad del evento
	 * @param rangoMinEdad rango minimo de edad
	 */
	public void setRangoMinEdad(Integer rangoMinEdad){
		this.rangoMinEdad = rangoMinEdad;
	}
	
	/**
	 * Pone el rango maximo de edad del evento
	 * @param rangoMaxEdad rango maximo de edad
	 */
	public void setRangoMaxEdad(Integer rangoMaxEdad){
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
		if(this.horaFinal != null)
			return this.horaFinal.toString();
		else
			return null;
	}
	
	/**
	 * 
	 * Obtiene la hora inicial del evento
	 * 
	 * @return Hora inicial del evento
	 */
	
	public String getHoraInicio(){
		if(this.horaInicio != null)
			return this.horaInicio.toString();
		else 
			return null;
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
		if(this.fechaFinal != null)
			return this.fechaFinal.toString();
		else
			return null;
	}
	
	/**
	 * 
	 * Obtiene la fecha de inicio del evento
	 * 
	 * @return Fecha inicial del evento
	 */
	
	public String getFechaInicio(){
		if(this.fechaInicio != null)
			return this.fechaInicio.toString();
		else
			return null;
	}
	
	/**
	 * 
	 * Obtiene la fecha de creacion del evento
	 * 
	 * @return Fecha de creacion del evento
	 */
	
	public String getFechaCreacion(){
		if(this.fechaCreacion != null)
			return this.fechaCreacion.toString();
		else 
			return null;
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
			   if(e.getId() != this.id){
				   retorno = false;
			   }
			if(retorno && e.getNombre() != null)
				   if(e.getNombre() != this.nombre){
					   retorno = false;
				   }
			if(retorno && e.getDescripcion() != null)
			   if(e.getDescripcion() != this.descripcion){
				   retorno = false;
			   }
			if(retorno && e.getFechaCreacion() != null)
			   if(!e.getFechaCreacion().toString().equals(this.fechaCreacion.toString())){
				   retorno = false;
			   }
			if(retorno && e.getFechaInicio() != null)
			   if(!e.getFechaInicio().toString().equals(this.fechaInicio.toString())){
				   retorno = false;
			   }
			if(retorno && e.getFechaFinal() != null)
			   if(!e.getFechaFinal().toString().equals(this.fechaFinal.toString())){
				   retorno = false;
			   }
			if(retorno && e.getHoraInicio() != null)
				if(!e.getHoraInicio().toString().equals(this.horaInicio.toString())){
					retorno = false;
				}
			if(retorno && e.getHoraFinal() != null)
				if(!e.getHoraFinal().toString().equals(this.horaFinal.toString())){
				    retorno = false;
				}
			if(retorno && e.getNumMaxParticipantes() != null)
				if (e.getNumMaxParticipantes() != this.numMaxParticipantes){
					retorno = false;
				}
			if(retorno && e.getRangoMaxEdad() != null)
				if (e.getRangoMaxEdad() != this.rangoMaxEdad){
					retorno = false;
				}
			if(retorno && e.getRangoMinEdad() != null)
				if (e.getRangoMinEdad() != this.rangoMinEdad){
					retorno = false;
				}
			if(retorno && e.getActivo() != null)
				if (e.getActivo() != this.activo){
					retorno = false;
				}
		}
		
		return retorno;
	}
	
	/**
	 * Retorna JSON del Evento
	 * @return String JSON del Evento
	 */
	@Override
	protected String retornarToString(){
		return this.stringJson();
	}
	
	@Override
	public String stringJson(){
		return "{id:"+this.id+","+
				"nombre:"+JsonUtils.propiedadNula(this.nombre)+","+
				"descripcion:"+JsonUtils.propiedadNula(this.descripcion)+","+
				"fechaCreacion:"+JsonUtils.propiedadNula(this.fechaCreacion)+","+
				"fechaInicio:"+JsonUtils.propiedadNula(this.fechaInicio)+","+
				"fechaFinal:"+JsonUtils.propiedadNula(this.fechaFinal)+","+
				"horaInicio:"+JsonUtils.propiedadNula(this.horaInicio)+","+
				"horaFinal:"+JsonUtils.propiedadNula(this.horaFinal)+","+
				"numMaxParticipantes:"+this.numMaxParticipantes+","+
				"rangoMaxEdad:"+this.rangoMaxEdad+","+
				"rangoMinEdad:"+this.rangoMinEdad +","+
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
	
	protected boolean setAtributo(String atributo,Object[] valor){
		boolean asignado = false;
		if(atributo.equals("id")){
			this.setId((String)valor[0]);
			asignado = true;
		}else if(atributo.equals("nombre")){
			this.setNombre(StringUtils.decodificar((String)valor[0]));
			asignado = true;
		}else if(atributo.equals("descripcion")){
			this.setDescripcion(StringUtils.decodificar((String)valor[0]));
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
			this.setHoraFinal((String)valor[0]);
			asignado = true;
		}else if(atributo.equals("numMaxParticipantes")){
			this.setNumMaxParticipantes((((String)valor[0]).equals("null"))?null:
										Integer.parseInt((String)valor[0]));
			asignado = true;
		}else if(atributo.equals("rangoMaxEdad")){
			this.setRangoMaxEdad((((String)valor[0]).equals("null"))?null:
								 Integer.parseInt((String)valor[0]));
			asignado = true;
		}else if(atributo.equals("rangoMinEdad")){
			this.setRangoMinEdad((((String)valor[0]).equals("null"))?null:
								 Integer.parseInt((String)valor[0]));
			asignado = true;
		}else if(atributo.equals("activo")){
			this.setActivo((((String)valor[0]).equals("null"))?null:
							Boolean.parseBoolean((String)valor[0]));
			asignado = true;
		}
		
		return asignado;
		
	}
	
	@Override
	public void setNullObject() {
		this.setId(null);
		this.setNombre(null);
		this.setDescripcion(null);
		this.setFechaCreacion(null);
		this.setFechaInicio(null);
		this.setFechaFinal(null);
		this.setHoraInicio(null);
		this.setHoraFinal(null);
		this.setNumMaxParticipantes(null);
		this.setRangoMinEdad(null);
		this.setRangoMaxEdad(null);
		this.setActivo(null);
	}

	@Override
	protected String get(String atributo) throws AtributoInexistenteException {
		if(this.esAtributo(atributo)){
			if(atributo.equals("id")){
				return this.getId();
			}else if(atributo.equals("nombre")){
				return this.getNombre();
			}else if(atributo.equals("descripcion")){
				return this.getDescripcion();
			}else if(atributo.equals("fechaCreacion")){
				return this.getFechaCreacion();
			}else if(atributo.equals("fechaInicio")){
				return this.getFechaInicio();
			}else if(atributo.equals("fechaFinal")){
				return this.getFechaFinal();
			}else if(atributo.equals("horaInicio")){
				return this.getHoraInicio();
			}else if(atributo.equals("horaFinal")){
				return this.getHoraFinal();
			}else if(atributo.equals("numMaxParticipantes")){
				return this.getNumMaxParticipantes().toString();
			}else if(atributo.equals("rangoMaxEdad")){
				return this.getRangoMaxEdad().toString();
			}else if(atributo.equals("rangoMinEdad")){
				return this.getRangoMinEdad().toString();
			}else if(atributo.equals("activo")){
				return this.getActivo().toString();
			}
		}
		
		throw new AtributoInexistenteException();
	
	}
	
}
