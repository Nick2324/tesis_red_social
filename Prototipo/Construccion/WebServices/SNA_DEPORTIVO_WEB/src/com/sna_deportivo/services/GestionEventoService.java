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
import com.sna_deportivo.utils.gr.ResponseGenerico;

@Path("eventos/")	
public class GestionEventoService {

	private GestionEvento gestionEvento;
	
	public GestionEventoService(){
		this.gestionEvento = new GestionEvento();
	}
	
	//revisar
	
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
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{tipoEvento}/{id}")
	public ResponseGenerico actualizarEventoCompleto(@PathParam("tipoEvento") String tipoEvento,
								 		 @PathParam("id") String idEvento,
								 		 String datosEvento){
		try{
			//this.gestionEvento.actualizarEvento(tipoEvento, evento);
		}catch(WebApplicationException e){
			throw e;
		}
		
		return new ResponseGenerico("201","Evento actualizado");
		
	}
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{tipoEvento}/{id}")
	public ResponseGenerico cancelarEvento(@PathParam("tipoEvento") String tipoEvento,
										   @PathParam("id") String id,
							   				Evento evento){
		try{
			this.gestionEvento.desactivarEvento(tipoEvento, evento);
		}catch(WebApplicationException e){
			throw e;
		}
		
		return new ResponseGenerico("","Evento cancelado con exito");
		
	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{tipoEvento}/{id}/participantes")
	public String consultarParticipantesEvento(@PathParam("tipoEvento") String tipoEvento,
											   @PathParam("id") String id,
											   Evento evento){
		try{
			
		}catch(WebApplicationException e){
			throw e;
		}

		return null;
	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{tipoEvento}/{id}/invitados")
	public String consultarInvitadosEvento(@PathParam("tipoEvento") String tipoEvento,
										   @PathParam("id") String id,
										   Evento evento){
		try{
			
		}catch(WebApplicationException e){
			throw e;
		}

		return null;
	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{tipoEvento}/{id}/ubicaciones")
	public String consultarUbicacionesEvento(@PathParam("tipoEvento") String tipoEvento,
										     @PathParam("id") String id,
										     Evento evento){
		try{
			
		}catch(WebApplicationException e){
			throw e;
		}

		return null;
	}
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{tipoEvento}/{idEvento}/participantes/{idParticipante}")
	public ResponseGenerico eliminarParticipanteEvento(@PathParam("tipoEvento") String tipoEvento,
										   			   @PathParam("idEvento") String idEvento,
										   			   @PathParam("idParticipante") String idParticipante,
										   			   String datosEliminaParticipante){
		try{
			
		}catch(WebApplicationException e){
			throw e;
		}

		return new ResponseGenerico("","Participante eliminado con exito");
	}

	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{tipoEvento}/{idEvento}/invitados/{idInvitado}")
	public ResponseGenerico eliminarInvitacionEvento(@PathParam("tipoEvento") String tipoEvento,
										   		     @PathParam("idEvento") String idEvento,
										   			 @PathParam("idInvitado") String idInvitado,
										   			 String datosEliminaInvitado){
		try{
			
		}catch(WebApplicationException e){
			throw e;
		}

		return new ResponseGenerico("","Invitado eliminado con exito");
	}
	
}

