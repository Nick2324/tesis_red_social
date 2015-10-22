package com.sna_deportivo.services.eventos;

import java.util.ArrayList;

import com.sna_deportivo.pojo.deportes.ConstantesDeportes;
import com.sna_deportivo.pojo.deportes.Deporte;
import com.sna_deportivo.pojo.deportes.ProductorFactoryDeporte;
import com.sna_deportivo.pojo.entidadesEstaticas.ConstantesEntidadesGenerales;
import com.sna_deportivo.pojo.entidadesEstaticas.Genero;
import com.sna_deportivo.pojo.entidadesEstaticas.ProductorFactoryGenerales;
import com.sna_deportivo.pojo.evento.ConstantesEventos;
import com.sna_deportivo.pojo.evento.DAOEvento;
import com.sna_deportivo.pojo.evento.DeporteEvento;
import com.sna_deportivo.pojo.evento.DeporteEventoDAO;
import com.sna_deportivo.pojo.evento.Evento;
import com.sna_deportivo.pojo.evento.ProductorFactoryEvento;
import com.sna_deportivo.pojo.ubicacion.ConstantesUbicaciones;
import com.sna_deportivo.pojo.ubicacion.ProductorFactoryUbicacion;
import com.sna_deportivo.pojo.ubicacion.Ubicacion;
import com.sna_deportivo.utils.bd.excepciones.BDException;
import com.sna_deportivo.utils.gr.ObjectSNSDeportivo;
import com.sna_deportivo.utils.gr.excepciones.ProductorFactoryExcepcion;
import com.sna_deportivo.utils.json.JsonUtils;
import com.sna_deportivo.utils.json.excepciones.ExcepcionJsonDeserializacion;

public class ActualizarEvento {

	public String actualizarEvento(String tipoEvento,
								   String idEvento,
								   String body) 
						throws ExcepcionJsonDeserializacion,BDException,ProductorFactoryExcepcion{
		String retorno = null;
		try {
			ArrayList<ObjectSNSDeportivo> eventos =
					JsonUtils.convertirMensajeJsonAObjectSNS(body, 
							ConstantesEventos.ELEMENTO_MENSAJE_SERVICIO_EVE, 
							new ProductorFactoryEvento());
			ArrayList<ObjectSNSDeportivo> deportes =
					JsonUtils.convertirMensajeJsonAObjectSNS(body, 
							ConstantesDeportes.ELEMENTO_MENSAJE_SERVICIO_DEP, 
							new ProductorFactoryDeporte());
			ArrayList<ObjectSNSDeportivo> generos = 
					JsonUtils.convertirMensajeJsonAObjectSNS(body, 
							ConstantesEntidadesGenerales.ELEMENTO_MENSAJE_SERVICIO_GEN, 
							new ProductorFactoryGenerales());
			ArrayList<ObjectSNSDeportivo> ubicaciones =
					JsonUtils.convertirMensajeJsonAObjectSNS(body, 
							ConstantesUbicaciones.ELEMENTO_MENSAJE_SERVICIO_UBI, 
							new ProductorFactoryUbicacion());
			DeporteEvento ed = new DeporteEvento();
			DeporteEventoDAO ded = new DeporteEventoDAO();
			//Como no he implementado lo de solo algunos atributos y no todos...
			if(idEvento != null){
				//*****************************************************
				Evento evento = new ProductorFactoryEvento().
						getEventosFactory(tipoEvento).crearEvento();
				evento.setId(idEvento);
				DAOEvento deve = new ProductorFactoryEvento().
						getEventosFactory(tipoEvento).crearDAOEvento();
				deve.setEvento(evento);
				Evento[] eventosEncontrados = deve.getEventosDB();
				if(eventosEncontrados != null && eventosEncontrados.length == 1){
					ed.setEvento(eventosEncontrados[0]);
					ded.setObjetcSNSDeportivo(ed);
					ded.encontrarObjetoManejado();
					ed = (DeporteEvento)ded.getObjetcSNSDeportivo();
				//*****************************************************
					if(eventos != null && eventos.size() == 1){
						//Actualiza datos del evento primero
						GestionEvento servicioEvento = new GestionEvento();
						servicioEvento.actualizarEvento(tipoEvento, 
								(Evento)eventos.get(0));
						ed.setEvento((Evento)eventos.get(0));
					}
					if(deportes != null && deportes.size() == 1){
						ed.setDeporte((Deporte)deportes.get(0));
					}
					if(generos != null && generos.size() == 1){
						ed.setGenero((Genero)generos.get(0));
					}
					if(ubicaciones != null && ubicaciones.size() == 1){
						ed.setUbicacion((Ubicacion)ubicaciones.get(0));
					}
					ded.setObjetcSNSDeportivo(ed);
					if(!ded.actualizarDeporteEvento()){
						throw new BDException();
					}
				}
			}
		} catch (ExcepcionJsonDeserializacion e) {
			e.printStackTrace();
			throw e;
		} catch(BDException e){
			e.printStackTrace();
			throw e;
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return retorno;
	}
	
}
