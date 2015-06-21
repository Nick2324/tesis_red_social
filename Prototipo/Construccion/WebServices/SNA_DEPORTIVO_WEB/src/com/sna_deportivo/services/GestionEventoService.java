package com.sna_deportivo.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import com.sna_deportivo.pojo.evento.ConstantesEventos;
import com.sna_deportivo.pojo.evento.Evento;
import com.sna_deportivo.services.eventos.GestionEvento;

@Path("eventos/")	
public class GestionEventoService {

	private GestionEvento gestionEvento;
	
	public GestionEventoService(){
		this.gestionEvento = new GestionEvento(); 
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("practicas_libres/")//{tipoEvento}
	public String consultarEventos(/*@PathParam("tipoEvento") String tipoEvento,*/
							Evento evento){
		try{
			return gestionEvento.consultarEventos(ConstantesEventos.PRACTICADEPORTIVALIBRE.getServicio(),//tipoEvento,
										   		  evento);
		}catch(WebApplicationException e){
			throw e;
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{tipoEvento}/")
	public Evento crearEvento(@PathParam("tipoEvento") String tipoEvento,
							   Evento evento){
		try{
			return this.gestionEvento.crearEvento(tipoEvento, evento);
		}catch(WebApplicationException e){
			throw e;
		}
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{tipoEvento}/")
	public void actualizarEvento(@PathParam("tipoEvento") String tipoEvento,
							   Evento evento){
		try{
			this.gestionEvento.actualizarEvento(tipoEvento, evento);
		}catch(WebApplicationException e){
			throw e;
		}
	}
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{tipoEvento}/")
	public void cancelarEvento(@PathParam("tipoEvento") String tipoEvento,
							   Evento evento){
		try{
			this.gestionEvento.desactivarEvento(tipoEvento, evento);
		}catch(WebApplicationException e){
			throw e;
		}
	}
	
}

