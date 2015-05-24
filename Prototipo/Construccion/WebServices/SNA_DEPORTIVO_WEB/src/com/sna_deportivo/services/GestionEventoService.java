package com.sna_deportivo.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sna_deportivo.pojo.evento.Evento;
import com.sna_deportivo.pojo.evento.EventoDeportivo;
import com.sna_deportivo.services.eventos.entity.GestionEvento;

@Path("eventos/")	
public class GestionEventoService {

	private GestionEvento gestionEvento;
	
	public GestionEventoService(){
		this.gestionEvento = new GestionEvento(); 
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{tipoEvento}/consultarEvento")
	public Evento consultarEvento(@PathParam("tipoEvento") String tipoEvento,
							Evento evento){
		return gestionEvento.consultarEvento(tipoEvento,
									   		 evento);
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{tipoEvento}/crearEvento")
	public boolean crearEvento(@PathParam("tipoEvento") String tipoEvento,
							   Evento evento){
		return this.gestionEvento.crearEvento(tipoEvento, evento);
	}
	
}

