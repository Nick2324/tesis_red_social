package com.sna_deportivo.pojo.evento;

import com.sna_deportivo.pojo.usuarios.ProductorFactoryUsuario;
import com.sna_deportivo.utils.bd.BDUtils;
import com.sna_deportivo.utils.bd.Entidades;
import com.sna_deportivo.utils.bd.RelacionSNS;
import com.sna_deportivo.utils.bd.Relaciones;
import com.sna_deportivo.utils.bd.excepciones.BDException;
import com.sna_deportivo.utils.gr.ObjectSNSDeportivo;
import com.sna_deportivo.utils.gr.ObjectSNSDeportivoDAO;
import com.sna_deportivo.utils.gr.excepciones.ProductorFactoryExcepcion;
import com.sna_deportivo.utils.json.JsonObject;
import com.sna_deportivo.utils.json.excepciones.ExcepcionJsonDeserializacion;

//!*!*!*!*! Ampliar para devolver el recurso

public abstract class DAOEvento extends ObjectSNSDeportivoDAO{
	
	private Evento evento;
	protected String eventoManejado;
	
	protected DAOEvento(){
		super();
		this.eventoManejado = TiposEventos.EVENTOGENERICO.getServicio();
	}
	
	protected DAOEvento(Evento e){
		super();
		this.evento = e;
	}	
	
	@Override
	protected void setUpDAOGeneral() {
		super.tipoObjetoSNS = Entidades.EVENTODEPORTIVO;
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
					eventos[i] = new ProductorFactoryEvento().
							 getEventosFactory(this.eventoManejado).
							 crearEvento();
					eventos[i].deserializarJson(datos);
				}
			} catch (BDException e) {
				eventos = null;
				throw e;
			}catch (ExcepcionJsonDeserializacion e) {
				eventos = null;
				e.printStackTrace();
			}catch (ProductorFactoryExcepcion e1) {
				eventos = null;
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
			evento = new ProductorFactoryEvento().
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
		this.evento.setId(""+BDUtils.generarNumeradorEntidad(Entidades.EVENTODEPORTIVO));
		query.append(this.evento.toString());
		query.append(") RETURN evento");
		BDUtils.ejecutarQueryREST(query.toString());
		if(this.evento.getCreador() != null){
			RelacionSNS relacionCreaEvento = 
					new RelacionSNS(Relaciones.CREAEVENTO,
									"creaEvento",
									evento.getCreador());
			super.objectSNSDeportivo = this.evento;
			super.crearRelacion(relacionCreaEvento, 
								new ProductorFactoryUsuario(),
								RelacionSNS.DIRECCION_ENTRADA);
		}
		
		return this.evento;
		
	}
	
	public String getEventoManejado(){
		return this.eventoManejado;
	}
	
	@Override
	public ObjectSNSDeportivo crearObjetoSNS() {
		return this.crearObjetoSNS();
	}
	
}
