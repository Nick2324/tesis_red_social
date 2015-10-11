package com.sna_deportivo.services.eventos;

public class RelacionEventoUsuario {

	public static String RELACION_INVITACION = "invitacion";
	public static String RELACION_SOLICITUD = "solicitud";
	public static String RELACION_PARTICIPANTE = "participante";

	private HandlerRelacionUsuarioEvento handlerUsuarioEvento;

	
	public RelacionEventoUsuario(){
		this.handlerUsuarioEvento =
				new RelacionPosibleParticipante();
		HandlerRelacionUsuarioEvento h1 =
				new RelacionInvitacionEvento();
		HandlerRelacionUsuarioEvento h2 =
				new RelacionSolicitudEvento();
		h1.setHandler(h2);
		this.handlerUsuarioEvento.setHandler(h1);
	}
	
	public HandlerRelacionUsuarioEvento getHandler(){
		return this.handlerUsuarioEvento;
	}
	
}
