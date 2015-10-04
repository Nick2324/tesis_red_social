package com.sna_deportivo.pojo.evento;

import com.sna_deportivo.pojo.usuarios.Usuario;
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
	private Usuario creador;
	
	/**
	 * 
	 * Constructor por defecto
	 * 
	 */
	public Evento(){}
	
	public void setCreador(Usuario creador) {
		this.creador = creador;
	}
	
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
	
	public Usuario getCreador() {
		return creador;
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
		boolean retorno = true;
		if(obj instanceof Evento){
			Evento e = (Evento)obj;
			if(retorno && !StringUtils.compararStrings(this.id,e.getId())){
				retorno = false;
			}
			if(retorno && !StringUtils.compararStrings(e.getNombre(),
										this.nombre)){
				retorno = false;
			}
			if(retorno && !StringUtils.compararStrings(e.getDescripcion(),
										this.descripcion)){
				   retorno = false;
			}
			if(retorno && !StringUtils.compararStrings(e.getFechaCreacion(),
										this.getFechaCreacion())){
				retorno = false;
			}
			if(retorno && !StringUtils.compararStrings(e.getFechaInicio(),
										this.getFechaInicio())){
				retorno = false;
			}
			if(retorno && !StringUtils.compararStrings(e.getFechaFinal(),
										this.getFechaFinal())){
				retorno = false;
			}
			if(retorno && !StringUtils.compararStrings(e.getHoraInicio(),
										this.getHoraInicio())){
				retorno = false;
			}
			if(retorno && !StringUtils.compararStrings(e.getHoraFinal(),
										this.getHoraFinal())){
				retorno = false;
			}
			if(retorno && e.getNumMaxParticipantes() != this.numMaxParticipantes){
				retorno = false;
			}
			if(retorno && e.getRangoMaxEdad() != this.rangoMaxEdad){
				retorno = false;
			}
			if(retorno && e.getRangoMinEdad() != this.rangoMinEdad){
				retorno = false;
			}
			if(retorno && e.getActivo() != this.activo){
				retorno = false;
			}
		}else{
			retorno = false;
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
		Object[] propiedades = new Object[]{
				this.id,
				this.nombre,
				this.descripcion,
				this.fechaCreacion,
				this.fechaInicio,
				this.fechaFinal,
				this.horaInicio,
				this.horaFinal,
				this.numMaxParticipantes,
				this.rangoMaxEdad,
				this.rangoMinEdad,
				this.activo
		};
		return "{"+
				JsonUtils.propiedadNula("id", this.id, propiedades,0)+
				JsonUtils.propiedadNula("nombre",this.nombre,propiedades,1)+
				JsonUtils.propiedadNula("descripcion",this.descripcion,propiedades,2)+
				JsonUtils.propiedadNula("fechaCreacion",this.fechaCreacion,propiedades,3)+
				JsonUtils.propiedadNula("fechaInicio",this.fechaInicio,propiedades,4)+
				JsonUtils.propiedadNula("fechaFinal",this.fechaFinal,propiedades,5)+
				JsonUtils.propiedadNula("horaInicio",this.horaInicio,propiedades,6)+
				JsonUtils.propiedadNula("horaFinal",this.horaFinal,propiedades,7)+
				JsonUtils.propiedadNulaTDPrimitivo("numMaxParticipantes", 
						this.numMaxParticipantes, propiedades,8)+
				JsonUtils.propiedadNulaTDPrimitivo("rangoMaxEdad", 
						this.rangoMaxEdad, propiedades,9)+
				JsonUtils.propiedadNulaTDPrimitivo("rangoMinEdad", 
						this.rangoMinEdad, propiedades,10)+
				JsonUtils.propiedadNulaTDPrimitivo("activo", 
						this.activo, propiedades,11)
				+"}";
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
			this.setId(JsonUtils.propiedadNulaBackwards((String)valor[0]));
			asignado = true;
		}else if(atributo.equals("nombre")){
			this.setNombre(JsonUtils.propiedadNulaBackwards((String)valor[0]));
			asignado = true;
		}else if(atributo.equals("descripcion")){
			this.setDescripcion(JsonUtils.propiedadNulaBackwards((String)valor[0]));
			asignado = true;
		}else if(atributo.equals("fechaCreacion")){
			this.setFechaCreacion(JsonUtils.propiedadNulaBackwards((String)valor[0]));
			asignado = true;
		}else if(atributo.equals("fechaInicio")){
			this.setFechaInicio(JsonUtils.propiedadNulaBackwards((String)valor[0]));
			asignado = true;
		}else if(atributo.equals("fechaFinal")){
			this.setFechaFinal(JsonUtils.propiedadNulaBackwards((String)valor[0]));
			asignado = true;
		}else if(atributo.equals("horaInicio")){
			this.setHoraInicio(JsonUtils.propiedadNulaBackwards((String)valor[0]));
			asignado = true;
		}else if(atributo.equals("horaFinal")){
			this.setHoraFinal(JsonUtils.propiedadNulaBackwards((String)valor[0]));
			asignado = true;
		}else if(atributo.equals("numMaxParticipantes")){
			this.setNumMaxParticipantes((Integer)JsonUtils.
					propiedadNulaBackwardsTDPrimi((String)valor[0],Integer.class));
			asignado = true;
		}else if(atributo.equals("rangoMaxEdad")){
			this.setRangoMaxEdad((Integer)JsonUtils.
					propiedadNulaBackwardsTDPrimi((String)valor[0],Integer.class));
			asignado = true;
		}else if(atributo.equals("rangoMinEdad")){
			this.setRangoMinEdad((Integer)JsonUtils.
					propiedadNulaBackwardsTDPrimi((String)valor[0],Integer.class));
			asignado = true;
		}else if(atributo.equals("activo")){
			this.setActivo((Boolean)JsonUtils.
					propiedadNulaBackwardsTDPrimi((String)valor[0],Boolean.class));
			asignado = true;
		}
		
		return asignado;
		
	}
	
	@Override
	public ObjectSNSDeportivo setNullObject() {
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
		return this;
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
