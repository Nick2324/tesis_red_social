package com.sna_deportivo.pojo.evento;

import com.sna_deportivo.pojo.evento.excepciones.ProductorFactoryExcepcion;
import com.sna_deportivo.pojo.json.JsonObject;
import com.sna_deportivo.pojo.json.excepciones.ExcepcionJsonDeserializacion;
import com.sna_deportivo.utils.BDUtils;
import com.sna_deportivo.utils.Entidades;
import com.sna_deportivo.utils.excepciones.BDException;

//*!*!*!*!*! MIRAR COMO MANEJAR RELACIONES SOBRE ARREGLOS, HACERLO GENERICO
//*!*!*!*!*! PERO AL MENOS YA TENEMOS LA IDEA MAS GENERAL, AHORA A AMPLIAR

public abstract class DAOEvento {
	
	private Evento evento;
	protected String eventoManejado;
	
	protected DAOEvento(){
		this.eventoManejado = ConstantesEventos.EVENTOGENERICO;
	}
	
	protected DAOEvento(Evento e){
		this.evento = e;
	}	
	
	public void setEvento(Evento e){
		this.evento = e;
	}
	
	public Evento getEvento(){
		return this.evento;
	}
	
	public Evento getEventoDB(){
		Evento evento = null;
		try {
			evento = new ProductorFactory().
						 getEventosFactory(this.eventoManejado).
						 crearEvento();
			String where = BDUtils.condicionWhere(this.evento,"evento");
			if(where != null && where.length() > 4){
				StringBuilder query = new StringBuilder("MATCH (evento:");
				query.append(Entidades.EVENTODEPORTIVO);
				query.append(") ");
				query.append(where);
				try {
					evento.deserializarJson(
							(JsonObject)BDUtils.ejecutarQuery(
									query.toString())[0]);
				} catch (BDException e) {
					e.printStackTrace();
				}catch (ExcepcionJsonDeserializacion e) {
					e.printStackTrace();
				}
				
			}
		
		} catch (ProductorFactoryExcepcion e1) {
			e1.printStackTrace();
		}
		
		return evento;
	
	}
	
	private String generalUpdate(Evento e){
		String set = BDUtils.producirSET(e,"evento");
		StringBuilder query = new StringBuilder("MATCH (evento:");
		query.append(Entidades.EVENTODEPORTIVO);
		query.append("{id:");
		query.append(this.evento.getId());
		query.append("}) ");
		query.append(set);
		return query.toString();
	}
	
	public boolean updateEventoDB(){
		try {
			BDUtils.ejecutarQuery(this.generalUpdate(this.evento));
			return true;
		} catch (BDException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean updateEventoDB(Evento evento){
		try {
			BDUtils.ejecutarQuery(this.generalUpdate(evento));
			return true;
		} catch (BDException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean deleteEventoDB(){
		Evento e = null;
		try {
			e = new ProductorFactory().
					       getEventosFactory(this.eventoManejado).
					       crearEvento();
			e.setId(this.evento.getId());
			e.setActivo(false);
		} catch (ProductorFactoryExcepcion e1) {
			e1.printStackTrace();
		}
		
		if(e != null)
			return this.updateEventoDB(e);
		else
			return false;
		
	}
	
	public Evento crearEventoDB(){
		StringBuilder query = new StringBuilder("CREATE (evento:");
		//Generar UUID
		this.evento.setId("1");
		query.append(this.evento.toString());
		query.append(")");
		return this.evento;		
	}
	
	public String getEventoManejado(){
		return this.eventoManejado;
	}
	
}
