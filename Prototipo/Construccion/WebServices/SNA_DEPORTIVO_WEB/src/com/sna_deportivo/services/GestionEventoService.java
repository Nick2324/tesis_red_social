package com.sna_deportivo.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import com.sna_deportivo.pojo.evento.Evento;
import com.sna_deportivo.pojo.evento.ProductorFactoryEvento;
import com.sna_deportivo.services.eventos.CrearEvento;
import com.sna_deportivo.services.eventos.GestionEvento;
import com.sna_deportivo.services.eventos.RelacionEventoUsuario;
import com.sna_deportivo.utils.gr.ResponseGenerico;

@Path("eventos/")	
public class GestionEventoService {

	private GestionEvento gestionEvento;
	private RelacionEventoUsuario gestionRelacionUsuarioEvento;
	
	public GestionEventoService(){
		this.gestionEvento = new GestionEvento();
		this.gestionRelacionUsuarioEvento =
				new RelacionEventoUsuario();
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
			throw new WebApplicationException();
		}
		
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
			//DELETE supuestamente no soporta Body Message
			if(evento == null || evento.getId() == null){
				evento = new ProductorFactoryEvento().
						getEventosFactory(tipoEvento).
						crearEvento();
				evento.setId(id);
			}
			this.gestionEvento.desactivarEvento(tipoEvento, evento);
		}catch(WebApplicationException e){
			throw e;
		}
		
		return new ResponseGenerico("200","Evento cancelado con exito");
		
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
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{tipoEvento}/{id}/invitados")
	public String crearInvitacionEvento(@PathParam("tipoEvento") String tipoEvento,
										 @PathParam("id") String idEvento,
										 String body){
		try{
			return this.gestionRelacionUsuarioEvento.getHandler().
					manejarCreacion(RelacionEventoUsuario.RELACION_INVITACION,
									tipoEvento,idEvento,body);
		}catch(Exception e){
			throw new WebApplicationException();
		}
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

		return new ResponseGenerico("200","Invitado eliminado con exito");
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
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{tipoEvento}/{id}/participantes")
	public String crearParticipante(@PathParam("tipoEvento") String tipoEvento,
									@PathParam("id") String idEvento,
									//Por solicitud o por invitacion
									@QueryParam("tipoParticipante") String tipoParticipante,
									String body){
		return this.gestionRelacionUsuarioEvento.getHandler().
				manejarCreacion(RelacionEventoUsuario.RELACION_PARTICIPANTE,
								tipoEvento,idEvento,body);
	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{tipoEvento}/{id}/participantes/solicitudes")
	public String consultarSolicitudesParticipante(@PathParam("tipoEvento") String tipoEvento,
											 	   @PathParam("id") String idEvento,
											 	   String body){
		return null;
	}


	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{tipoEvento}/{id}/participantes/solicitudes")
	public String crearSolicitudParticipante(@PathParam("tipoEvento") String tipoEvento,
											 @PathParam("id") String idEvento,
											 String body){
		return this.gestionRelacionUsuarioEvento.getHandler().
				manejarCreacion(RelacionEventoUsuario.RELACION_SOLICITUD,
								tipoEvento,idEvento,body);
	}
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{tipoEvento}/{idEvento}/participantes/{idParticipante}")
	public ResponseGenerico eliminarParticipanteEvento(@PathParam("tipoEvento") String tipoEvento,
										   			   @PathParam("idEvento") String idEvento,
										   			   @PathParam("idParticipante") String idParticipante,
										   			   String body){
		try{
			
		}catch(WebApplicationException e){
			throw e;
		}

		return new ResponseGenerico("","Participante eliminado con exito");
	}
	
}

