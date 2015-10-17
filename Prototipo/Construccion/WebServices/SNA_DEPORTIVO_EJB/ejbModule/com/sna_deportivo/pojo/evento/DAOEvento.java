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

public class DAOEvento extends ObjectSNSDeportivoDAO{
	
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
		super.factoryOSNS = new EventosFactory();
		super.identificadorQueries = "eventoGeneral";
	}
	
	public void setEvento(Evento e){
		this.evento = e;
	}
	
	public Evento getEvento(){
		return this.evento;
	}
	
	public Evento[] getEventosDB() throws ProductorFactoryExcepcion,
		BDException,ExcepcionJsonDeserializacion{
		Evento[] eventos = null;
		String where = BDUtils.condicionWhere(this.evento,"evento");
		if(where != null ||
		   this.evento == null ||
		   this.evento.stringJson().equals("{}")){
			StringBuilder query = new StringBuilder("MATCH (evento:");
			query.append(Entidades.EVENTODEPORTIVO);
			query.append(")");
			ObjectSNSDeportivoDAO daoUsuario = null;
			if(this.evento != null && 
			   this.evento.getCreador() != null){
				daoUsuario = 
						new ProductorFactoryUsuario().
						producirFacObjetoSNS(
								this.evento.getCreador().getClass().getSimpleName()).
						getObjetoSNSDAO();
				daoUsuario.setObjetcSNSDeportivo(this.evento.getCreador());
				RelacionSNS relacion = 
						new RelacionSNS(Relaciones.CREAEVENTO,
						     		   "creaEvento",
						     		   RelacionSNS.DIRECCION_ENTRADA,
									   daoUsuario.getObjetcSNSDeportivo());
				query.append(relacion.stringJson());
				query.append(daoUsuario.producirNodoMatch());
			}
			if(where != null){
				query.append(where);
			}
			query.append(" RETURN evento");
			if(this.evento != null && 
			   this.evento.getCreador() != null){
				query.append("," + daoUsuario.getIdentificadorQueries());
			}
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
	
	private String generalUpdate(Evento e) throws ExcepcionJsonDeserializacion{
		String set = BDUtils.producirSET(e,"evento");
		StringBuilder query = new StringBuilder("MATCH (evento:");
		query.append(Entidades.EVENTODEPORTIVO);
		query.append("{id:");
		query.append("'"+this.evento.getId()+"'");
		query.append("}) ");
		query.append(set);
		return query.toString();
	}
	
	public void updateEventoDB() throws BDException,ExcepcionJsonDeserializacion{
		try {
			BDUtils.ejecutarQueryREST(
					this.generalUpdate(this.evento));
		} catch (BDException e) {
			throw e;
		}
	}
	
	public void updateEventoDB(Evento evento) throws BDException,ExcepcionJsonDeserializacion{
		try {
			BDUtils.ejecutarQueryREST(this.generalUpdate(evento));
		} catch (BDException e) {
			throw e;
		}
	}
	
	public void deleteEventoDB() throws BDException,
					ProductorFactoryExcepcion,ExcepcionJsonDeserializacion{
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
	
	public Evento crearEventoDB() throws BDException, ProductorFactoryExcepcion{
		StringBuilder query = new StringBuilder("CREATE (evento:"+Entidades.EVENTODEPORTIVO);
		this.evento.setId(""+BDUtils.generarNumeradorEntidad(Entidades.EVENTODEPORTIVO));
		query.append(this.evento.toString());
		query.append(") RETURN evento");
		BDUtils.ejecutarQueryREST(query.toString());
		if(this.evento.getCreador() != null){
			RelacionSNS relacionCreaEvento = 
					new RelacionSNS(Relaciones.CREAEVENTO,
									"creaEvento",
									RelacionSNS.DIRECCION_ENTRADA,
									evento.getCreador());
			super.objectSNSDeportivo = this.evento;
			super.crearRelacion(relacionCreaEvento, 
								new ProductorFactoryUsuario());
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

	@Override
	public void encontrarObjetoManejado() 
			throws BDException, ProductorFactoryExcepcion, ProductorFactoryExcepcion {
		// TODO Auto-generated method stub
		
	}
	
}
