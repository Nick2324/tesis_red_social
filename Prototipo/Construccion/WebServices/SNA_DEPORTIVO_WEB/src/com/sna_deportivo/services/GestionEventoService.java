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

import com.sna_deportivo.pojo.evento.Evento;
import com.sna_deportivo.services.eventos.CrearEvento;
import com.sna_deportivo.services.eventos.GestionEvento;

@Path("eventos/")	
public class GestionEventoService {

	private GestionEvento gestionEvento;
	
	public GestionEventoService(){
		this.gestionEvento = new GestionEvento();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{tipoEvento}")
	public String consultarEventos(@PathParam("tipoEvento") String tipoEvento,
							 	   Evento evento){
		try{
			return gestionEvento.consultarEventos(tipoEvento,
										   		  evento);
		}catch(WebApplicationException e){
			e.printStackTrace();
			throw e;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{tipoEvento}")
	public String crearEvento(@PathParam("tipoEvento") String tipoEvento,
							   String datosEvento){
		try{
			return new CrearEvento().crearEvento(tipoEvento, datosEvento);
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

