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
import javax.ws.rs.core.Response;

import com.sna_deportivo.pojo.evento.Evento;
import com.sna_deportivo.pojo.evento.ProductorFactoryEvento;
import com.sna_deportivo.services.eventos.ActualizarEvento;
import com.sna_deportivo.services.eventos.ConsultarEvento;
import com.sna_deportivo.services.eventos.CrearEvento;
import com.sna_deportivo.services.eventos.GestionEvento;
import com.sna_deportivo.services.eventos.ObtenerDeporteEvento;
import com.sna_deportivo.services.eventos.ObtenerGeneroEvento;
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
		}catch(Exception e){
			e.printStackTrace();
			throw new WebApplicationException(500);
		}
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{tipoEvento}/{id}")
	public ResponseGenerico actualizarEvento(@PathParam("tipoEvento") String tipoEvento,
											 @PathParam("id") String idEvento,
											 String body){
		try{
			new ActualizarEvento().actualizarEvento(tipoEvento,
													idEvento,
													body);
		}catch(WebApplicationException e){
			throw e;
		}catch(Exception e){
			e.printStackTrace();
			throw new WebApplicationException(500);
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
			if(evento == null || evento.getId() == null){
				evento = new ProductorFactoryEvento().
						getEventosFactory(tipoEvento).
						crearEvento();
				evento.setId(id);
			}
			this.gestionEvento.desactivarEvento(tipoEvento, evento);
		}catch(WebApplicationException e){
			throw e;
		}catch(Exception e){
			e.printStackTrace();
			throw new WebApplicationException(500);
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
			return new ConsultarEvento().consultarUbicacionEvento(
					tipoEvento, evento);
		}catch(WebApplicationException e){
			e.printStackTrace();
			throw e;
		}catch(Exception e){
			e.printStackTrace();
			throw new WebApplicationException(
					Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{tipoEvento}/{id}/ubicaciones")
	public ResponseGenerico crearUbicacionesEvento(@PathParam("tipoEvento") String tipoEvento,
										 @PathParam("id") String id,
										 String body){
		try{
			return new ResponseGenerico(
					new Integer(Response.Status.CREATED.getStatusCode()).toString(),
					"Ubicacion creada con exito",
					new ActualizarEvento().actualizarEvento(tipoEvento, id, body));
		}catch(WebApplicationException e){
			throw e;
		}catch(Exception e){
			e.printStackTrace();
			throw new WebApplicationException(
					Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
		}
		
	}
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{tipoEvento}/{id}/ubicaciones/{idUbicacion}")
	public ResponseGenerico eliminarUbicacionesEvento(@PathParam("tipoEvento") String tipoEvento,
										    @PathParam("id") String id,
										    @PathParam("idUbicacion") String idUbicacion,
										    String body){
		try{
			
		}catch(WebApplicationException e){
			throw e;
		}

		return null;
	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{tipoEvento}/{id}/invitaciones")
	public String consultarInvitadosEvento(@PathParam("tipoEvento") String tipoEvento,
										   @PathParam("id") String id,
										   String body){
		try{
			return this.gestionRelacionUsuarioEvento.getHandler().
					manejarObtencion(
					   RelacionEventoUsuario.RELACION_INVITACION,
					   tipoEvento,id,body);
		}catch(WebApplicationException e){
			throw e;
		}catch(Exception e){
			e.printStackTrace();
			throw new WebApplicationException(500);
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{tipoEvento}/{id}/invitaciones")
	public ResponseGenerico crearInvitacionEvento(@PathParam("tipoEvento") String tipoEvento,
										 @PathParam("id") String idEvento,
										 String body){
		try{
			return new ResponseGenerico("200","Invitacion creada con exito",
					this.gestionRelacionUsuarioEvento.getHandler().
					manejarCreacion(RelacionEventoUsuario.RELACION_INVITACION,
									tipoEvento,idEvento,body));
		}catch(WebApplicationException e){
			throw e;
		}catch(Exception e){
			e.printStackTrace();
			throw new WebApplicationException(500);
		}
	}
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{tipoEvento}/{idEvento}/invitaciones/{idInvitado}")
	public ResponseGenerico eliminarInvitacionEvento(@PathParam("tipoEvento") String tipoEvento,
										   		     @PathParam("idEvento") String idEvento,
										   			 @PathParam("idInvitado") String idInvitado,
										   			 String body){
		try{
			return new ResponseGenerico("200","Invitado eliminado con exito",
					this.gestionRelacionUsuarioEvento.
					getHandler().
					manejarEliminacion(RelacionEventoUsuario.RELACION_INVITACION, 
							tipoEvento, idEvento, idInvitado, body));
		}catch(WebApplicationException e){
			throw e;
		}catch(Exception e){
			e.printStackTrace();
			throw new WebApplicationException(500);
		}

	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{tipoEvento}/{id}/participantes")
	public String consultarParticipantesEvento(@PathParam("tipoEvento") String tipoEvento,
											   @PathParam("id") String id,
											   String body){
		try{
			return this.gestionRelacionUsuarioEvento.getHandler().
					manejarObtencion(
					   RelacionEventoUsuario.RELACION_PARTICIPANTE,
					   tipoEvento,id,body);
		}catch(WebApplicationException e){
			throw e;
		}catch(Exception e){
			e.printStackTrace();
			throw new WebApplicationException(500);
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{tipoEvento}/{id}/participantes")
	public ResponseGenerico crearParticipante(@PathParam("tipoEvento") String tipoEvento,
									@PathParam("id") String idEvento,
									//Por solicitud o por invitacion
									@QueryParam("tipoParticipante") String tipoParticipante,
									String body){
		try{
			return new ResponseGenerico("204","Participante creado con exito",
					this.gestionRelacionUsuarioEvento.getHandler().
					manejarCreacion(RelacionEventoUsuario.RELACION_PARTICIPANTE,
									tipoEvento,idEvento,body));
		}catch(WebApplicationException e){
			throw e;
		}catch(Exception e){
			e.printStackTrace();
			throw new WebApplicationException(500);
		}
		
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
			this.gestionRelacionUsuarioEvento.
				getHandler().
				manejarEliminacion(RelacionEventoUsuario.RELACION_PARTICIPANTE, 
						tipoEvento, idEvento, idParticipante, body);
		}catch(WebApplicationException e){
			throw e;
		}catch(Exception e){
			e.printStackTrace();
			throw new WebApplicationException(500);
		}

		return new ResponseGenerico("200","Participante eliminado con exito");
	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{tipoEvento}/{id}/participantes/solicitudes")
	public String consultarSolicitudesParticipante(@PathParam("tipoEvento") String tipoEvento,
											 	   @PathParam("id") String idEvento,
											 	   String body){
		try{
			return this.gestionRelacionUsuarioEvento.getHandler().
					manejarObtencion(
					   RelacionEventoUsuario.RELACION_SOLICITUD,
					   tipoEvento,idEvento,body);
		}catch(WebApplicationException e){
			throw e;
		}catch(Exception e){
			e.printStackTrace();
			throw new WebApplicationException(500);
		}
	}


	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{tipoEvento}/{id}/participantes/solicitudes")
	public ResponseGenerico crearSolicitudParticipante(@PathParam("tipoEvento") String tipoEvento,
											 @PathParam("id") String idEvento,
											 String body){
		try{
			return new ResponseGenerico("200","Solicitud creada con exito",
					this.gestionRelacionUsuarioEvento.getHandler().
					manejarCreacion(RelacionEventoUsuario.RELACION_SOLICITUD,
									tipoEvento,idEvento,body));
		}catch(WebApplicationException e){
			e.printStackTrace();
			throw e;
		}catch(Exception e){
			e.printStackTrace();
			throw new WebApplicationException(500);
		}
	}
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{tipoEvento}/{idEvento}/participantes/solicitudes/{idParticipante}")
	public ResponseGenerico eliminarSolicitudEvento(@PathParam("tipoEvento") String tipoEvento,
										   			@PathParam("idEvento") String idEvento,
										   			@PathParam("idParticipante") String idSolicitud,
										   			String body){
		try{
			return new ResponseGenerico("200","Solicitud eliminada con exito",
					this.gestionRelacionUsuarioEvento.
						getHandler().
						manejarEliminacion(RelacionEventoUsuario.RELACION_SOLICITUD,
								tipoEvento, idEvento, idSolicitud, body));
		}catch(WebApplicationException e){
			throw e;
		}catch(Exception e){
			e.printStackTrace();
			throw new WebApplicationException(500);
		}
		
	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{tipoEvento}/{id}/deportes")
	public ResponseGenerico obtenerDeporteEvento(@PathParam("tipoEvento") String tipoEvento,
												 @PathParam("id") String idEvento,
												 String body){
		try{
			return new ResponseGenerico("200","Deporte de evento enviado",
					new ObtenerDeporteEvento().obtenerDeporteEvento(
							idEvento, tipoEvento, body));
		}catch(WebApplicationException e){
			throw e;
		}catch(Exception e){
			e.printStackTrace();
			throw new WebApplicationException(500);
		}
	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{tipoEvento}/{id}/generos")
	public ResponseGenerico obtenerGeneroEvento(@PathParam("tipoEvento") String tipoEvento,
												 @PathParam("id") String idEvento,
												 String body){
		try{
			return new ResponseGenerico("200","Deporte de evento enviado",
					new ObtenerGeneroEvento().obtenerGeneroEvento(
							idEvento, tipoEvento, body));
		}catch(WebApplicationException e){
			throw e;
		}catch(Exception e){
			e.printStackTrace();
			throw new WebApplicationException(500);
		}
	}
	
}

