package com.sna_deportivo.services.eventos;


import com.sna_deportivo.pojo.evento.DeporteEvento;
import com.sna_deportivo.pojo.evento.Evento;
import com.sna_deportivo.pojo.evento.ProductorFactoryEvento;
import com.sna_deportivo.utils.bd.excepciones.BDException;
import com.sna_deportivo.utils.gr.excepciones.ProductorFactoryExcepcion;
import com.sna_deportivo.utils.json.excepciones.ExcepcionJsonDeserializacion;

public class RelacionSolicitudEvento extends HandlerRelacionUsuarioEvento{
	
	public RelacionSolicitudEvento(){
		super("solicitud");
	}

	@Override
	protected DeporteEvento prepararDeporteEvento(String tipoEvento) 
			 throws ProductorFactoryExcepcion{
		Evento evento = null;
		evento = new ProductorFactoryEvento().
		   getEventosFactory(tipoEvento).crearEvento();
		evento = (Evento)arregloEventos.get(0);
		DeporteEvento de = new DeporteEvento();
		de.setEvento(evento);
		ded.setObjetcSNSDeportivo(de);
		ded.encontrarObjetoManejado();
		de = (DeporteEvento)ded.getObjetcSNSDeportivo();
		de.setSolicitudes(arregloUsuarios);
		return de;
	}

	@Override
	protected String manejarCreacion() throws BDException,ProductorFactoryExcepcion{
		return ded.crearSolicitudes();
	}

	@Override
	protected DeporteEvento prepararDeporteEventoObtencion(String tipoEvento) 
			 throws ProductorFactoryExcepcion{
		Evento evento = null;
		evento = new ProductorFactoryEvento().
		   getEventosFactory(tipoEvento).crearEvento();
		evento = (Evento)arregloEventos.get(0);
		DeporteEvento de = new DeporteEvento();
		de.setEvento(evento);
		ded.setObjetcSNSDeportivo(de);
		ded.encontrarObjetoManejado();
		de = (DeporteEvento)ded.getObjetcSNSDeportivo();
		return de;
	}

	@Override
	protected String manejarObtencion() 
			throws BDException, ExcepcionJsonDeserializacion {
		return ded.getSolicitudesEvento();
	}

	@Override
	protected DeporteEvento prepararDeporteEventoEliminacion(String tipoEvento) 
			 throws ProductorFactoryExcepcion{
		Evento evento = null;
		evento = new ProductorFactoryEvento().
		   getEventosFactory(tipoEvento).crearEvento();
		evento = (Evento)arregloEventos.get(0);
		DeporteEvento de = new DeporteEvento();
		de.setEvento(evento);
		ded.setObjetcSNSDeportivo(de);
		ded.encontrarObjetoManejado();
		de = (DeporteEvento)ded.getObjetcSNSDeportivo();
		de.setSolicitudes(arregloUsuarios);
		return de;
	}

	@Override
	protected String manejarEliminacion() 
			throws BDException, ExcepcionJsonDeserializacion,ProductorFactoryExcepcion {
		return ded.eliminarSolicitudes();
	}
	
}
