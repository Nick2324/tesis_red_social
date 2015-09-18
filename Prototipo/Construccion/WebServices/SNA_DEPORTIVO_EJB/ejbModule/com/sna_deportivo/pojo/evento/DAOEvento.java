package com.sna_deportivo.pojo.evento;

import com.sna_deportivo.pojo.evento.excepciones.ProductorFactoryExcepcion;
import com.sna_deportivo.utils.bd.BDUtils;
import com.sna_deportivo.utils.bd.Entidades;
import com.sna_deportivo.utils.bd.excepciones.BDException;
import com.sna_deportivo.utils.gr.ObjectSNSDeportivoDAO;
import com.sna_deportivo.utils.json.JsonObject;
import com.sna_deportivo.utils.json.excepciones.ExcepcionJsonDeserializacion;

//!*!*!*!*! Ampliar para devolver el recurso

public abstract class DAOEvento extends ObjectSNSDeportivoDAO{
	
	private Evento evento;
	protected String eventoManejado;
	
	protected DAOEvento(){
		this.eventoManejado = ConstantesEventos.EVENTOGENERICO.getServicio();
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
	
	public Evento[] getEventosDB() throws ProductorFactoryExcepcion,BDException{
		Evento[] eventos = null;
		
		String where = BDUtils.condicionWhere(this.evento,"evento");
		if(where != null ||
		   this.evento == null){
			StringBuilder query = new StringBuilder("MATCH (evento:");
			query.append(Entidades.EVENTODEPORTIVO);
			query.append(")");
			if(where != null)
				query.append(where);
			query.append(" RETURN evento");
			try {
				Object[] resultadoQuery = BDUtils.ejecutarQueryREST(
											query.toString());
				eventos = new Evento[resultadoQuery.length];
				for(int i = 0; i < resultadoQuery.length; i++){
					JsonObject datos = 
							(JsonObject)BDUtils.obtenerRestRegistro(resultadoQuery[i]).
							getPropiedades().get("data")[0];
					eventos[i] = new ProductorFactory().
							 getEventosFactory(this.eventoManejado).
							 crearEvento();
					eventos[i].deserializarJson(datos);
				}
			} catch (BDException e) {
				throw e;
			}catch (ExcepcionJsonDeserializacion e) {
				e.printStackTrace();
			}catch (ProductorFactoryExcepcion e1) {
				throw e1;
			}
			
		}
		
		return eventos;
	
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
	
	public void updateEventoDB() throws BDException{
		try {
			BDUtils.ejecutarQueryREST(
					this.generalUpdate(this.evento));
		} catch (BDException e) {
			throw e;
		}
	}
	
	public void updateEventoDB(Evento evento) throws BDException{
		try {
			BDUtils.ejecutarQueryREST(this.generalUpdate(evento));
		} catch (BDException e) {
			throw e;
		}
	}
	
	public void deleteEventoDB()throws BDException,ProductorFactoryExcepcion{
		Evento evento = null;
		try {
			evento = new ProductorFactory().
					       getEventosFactory(this.eventoManejado).
					       crearEvento();
			evento.setId(this.evento.getId());
			evento.setActivo(false);
		} catch (ProductorFactoryExcepcion e) {
			throw e;
		}
		
		try{
			this.updateEventoDB(evento);
		}catch(BDException e){
			throw e;
		}
		
	}
	
	public Evento crearEventoDB() throws BDException{
		StringBuilder query = new StringBuilder("CREATE (evento:"+Entidades.EVENTODEPORTIVO);
		//Generar UUID
		this.evento.setId("1");
		query.append(this.evento.toString());
		query.append(") RETURN evento");
		try {
			BDUtils.ejecutarQueryREST(query.toString());
		} catch (BDException e) {
			throw e;
		}
		return this.evento;		
	}
	
	public String getEventoManejado(){
		return this.eventoManejado;
	}
	
}
