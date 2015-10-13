package com.sna_deportivo.services.eventos;

import java.util.ArrayList;

import com.sna_deportivo.pojo.evento.ConstantesEventos;
import com.sna_deportivo.pojo.evento.DeporteEvento;
import com.sna_deportivo.pojo.evento.DeporteEventoDAO;
import com.sna_deportivo.pojo.evento.Evento;
import com.sna_deportivo.pojo.evento.ProductorFactoryEvento;
import com.sna_deportivo.utils.bd.excepciones.BDException;
import com.sna_deportivo.utils.gr.ObjectSNSDeportivo;
import com.sna_deportivo.utils.json.JsonUtils;
import com.sna_deportivo.utils.json.excepciones.ExcepcionJsonDeserializacion;

public class ObtenerDeporteEvento {
	
	public String obtenerDeporteEvento(String idEvento,
									  String tipoEvento,
									  String body) 
						throws BDException,ExcepcionJsonDeserializacion{
		String retorno = "{}";
		if(body != null && idEvento != null && tipoEvento != null){
			ArrayList<ObjectSNSDeportivo> eventos =
			JsonUtils.convertirMensajeJsonAObjectSNS(body, 
				ConstantesEventos.ELEMENTO_MENSAJE_SERVICIO_EVE, 
				new ProductorFactoryEvento());
			if(eventos != null && eventos.size() == 1){
				DeporteEventoDAO ded = new DeporteEventoDAO();
				DeporteEvento de = new DeporteEvento();
				de.setEvento((Evento)eventos.get(0));
				ded.setObjetcSNSDeportivo(de);
				try {
					retorno = ded.obtenerDeporteEvento();
				} catch (BDException e) {
					e.printStackTrace();
					throw e;
				} catch (ExcepcionJsonDeserializacion e) {
					e.printStackTrace();
					throw e;
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		return retorno;
	}
}
