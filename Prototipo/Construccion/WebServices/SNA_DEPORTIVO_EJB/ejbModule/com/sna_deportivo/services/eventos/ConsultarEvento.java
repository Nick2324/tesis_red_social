package com.sna_deportivo.services.eventos;

import com.sna_deportivo.pojo.evento.DeporteEvento;
import com.sna_deportivo.pojo.evento.DeporteEventoDAO;
import com.sna_deportivo.pojo.evento.Evento;
import com.sna_deportivo.pojo.ubicacion.Ubicacion;
import com.sna_deportivo.utils.gr.excepciones.ProductorFactoryExcepcion;
import com.sna_deportivo.utils.json.excepciones.ExcepcionJsonDeserializacion;

public class ConsultarEvento {

	public ConsultarEvento(){}
	
	public String consultarUbicacionEvento(String tipoEvento,
										   Evento evento) throws ExcepcionJsonDeserializacion, ProductorFactoryExcepcion{
		String retorno = "{}";
		if(evento != null){
			DeporteEventoDAO ded = new DeporteEventoDAO();
			DeporteEvento de = new DeporteEvento();
			de.setEvento(evento);
			ded.setObjetcSNSDeportivo(de);
			ded.encontrarObjetoManejado();
			de = (DeporteEvento)ded.getObjetcSNSDeportivo();
			de.setUbicacion(new Ubicacion());
			ded.setObjetcSNSDeportivo(de);
			retorno = ded.obtenerUbicacionEvento();
		}
		return retorno;
	}
	
}
