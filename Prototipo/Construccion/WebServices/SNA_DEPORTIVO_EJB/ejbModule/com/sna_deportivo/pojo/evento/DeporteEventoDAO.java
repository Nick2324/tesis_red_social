package com.sna_deportivo.pojo.evento;

import java.util.ArrayList;

import com.sna_deportivo.pojo.deportes.ProductorFactoryDeporte;
import com.sna_deportivo.pojo.entidadesEstaticas.ProductorFactoryGenerales;
import com.sna_deportivo.utils.bd.BDUtils;
import com.sna_deportivo.utils.bd.Entidades;
import com.sna_deportivo.utils.bd.RelacionSNS;
import com.sna_deportivo.utils.bd.Relaciones;
import com.sna_deportivo.utils.gr.ObjectSNSDeportivo;
import com.sna_deportivo.utils.gr.ObjectSNSDeportivoDAO;
import com.sna_deportivo.utils.json.JsonUtils;
import com.sna_deportivo.utils.json.excepciones.ExcepcionJsonDeserializacion;

public class DeporteEventoDAO extends ObjectSNSDeportivoDAO{

	public DeporteEventoDAO(){
		super();
	}
	
	@Override
	protected void setUpDAOGeneral() {
		this.factoryOSNS = new FactoryDeporteEvento();
		this.identificadorQueries = "deporteEvento";
		this.tipoObjetoSNS = Entidades.DEPORTEEVENTO;
	}

	@Override
	public ObjectSNSDeportivo crearObjetoSNS() {
		final DeporteEvento de = (DeporteEvento)super.objectSNSDeportivo;
		de.setId(BDUtils.generarNumeradorEntidad(this.tipoObjetoSNS));
		super.objectSNSDeportivo = de;
		StringBuilder query = new StringBuilder("CREATE (");
		query.append(this.identificadorQueries);
		query.append(":");
		query.append(this.tipoObjetoSNS);
		query.append(de.stringJson());
		query.append(") RETURN "+this.identificadorQueries);
		BDUtils.ejecutarQueryREST(query.toString());
		RelacionSNS relacionEventoNAria = 
				new RelacionSNS(Relaciones.DESCRIPCIONEVENTO,
								"descripcionEvento",
								RelacionSNS.DIRECCION_ENTRADA);
		if(de.getEvento() != null){
			relacionEventoNAria.setObjetoRelacion(de.getEvento());
			super.crearRelacion(relacionEventoNAria, 
								new ProductorFactoryEvento());
		}
		if(de.getDeporte() != null){
			relacionEventoNAria.setObjetoRelacion(de.getDeporte());
			super.crearRelacion(relacionEventoNAria, 
					new ProductorFactoryDeporte());
		}
		
		if(de.getUbicacion() != null){
			relacionEventoNAria.setObjetoRelacion(de.getUbicacion());
			super.crearRelacion(relacionEventoNAria, 
					null);
		}
		if(de.getGenero() != null){
			relacionEventoNAria.setObjetoRelacion(de.getGenero());
			super.crearRelacion(relacionEventoNAria, 
								new ProductorFactoryGenerales());
		}
		
		return this.objectSNSDeportivo;
	}
	
	public String getParticipantesEvento(String tipoEvento,
										 String evento){
		String retorno = "[]";
		try {
			Evento e = new ProductorFactoryEvento().getEventosFactory(tipoEvento).crearEvento();
			e.deserializarJson(JsonUtils.JsonStringToObject(evento));
			DeporteEvento de = (DeporteEvento)this.factoryOSNS.getObjetoSNS();
			de.setEvento(e);
			RelacionSNS relacionParticipantes = 
					new RelacionSNS(Relaciones.PARTICIPANTEEVENTO,
									"participaEvento",
									RelacionSNS.DIRECCION_ENTRADA,
									de.getParticipantes());
		} catch (ExcepcionJsonDeserializacion e1) {
			e1.printStackTrace();
		}
		return retorno;
	}

}
