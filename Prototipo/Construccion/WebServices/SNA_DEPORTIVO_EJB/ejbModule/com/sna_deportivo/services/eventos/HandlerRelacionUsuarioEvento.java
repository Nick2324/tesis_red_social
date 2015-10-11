package com.sna_deportivo.services.eventos;

import java.util.ArrayList;

import com.sna_deportivo.pojo.evento.ConstantesEventos;
import com.sna_deportivo.pojo.evento.DeporteEvento;
import com.sna_deportivo.pojo.evento.DeporteEventoDAO;
import com.sna_deportivo.pojo.evento.ProductorFactoryEvento;
import com.sna_deportivo.pojo.usuarios.ConstantesUsuarios;
import com.sna_deportivo.pojo.usuarios.ProductorFactoryUsuario;
import com.sna_deportivo.utils.bd.excepciones.BDException;
import com.sna_deportivo.utils.gr.ObjectSNSDeportivo;
import com.sna_deportivo.utils.json.JsonUtils;
import com.sna_deportivo.utils.json.excepciones.ExcepcionJsonDeserializacion;

public abstract class HandlerRelacionUsuarioEvento {
	
	private HandlerRelacionUsuarioEvento handler;
	private String aManejar;
	protected ArrayList<ObjectSNSDeportivo> arregloUsuarios;
	protected ArrayList<ObjectSNSDeportivo> arregloEventos;
	protected DeporteEventoDAO ded;
	
	protected HandlerRelacionUsuarioEvento(String aManejar){
		this.aManejar = aManejar;
		this.ded = new DeporteEventoDAO();
	}
	
	public void setHandler(HandlerRelacionUsuarioEvento handler){
		this.handler = handler;
	}
	
	public HandlerRelacionUsuarioEvento getHandler(){
		return this.handler;
	}
	
	private boolean esManejado(String aManejar){
		return this.aManejar.equals(aManejar);
	}
	
	protected abstract DeporteEvento prepararDeporteEvento(String tipoEvento);
	
	protected abstract DeporteEvento prepararDeporteEventoObtencion(String tipoEvento);
	
	protected abstract String manejarCreacion()throws BDException;
	
	protected abstract String manejarObtencion() 
			throws BDException, ExcepcionJsonDeserializacion ;
	
	public String manejarObtencion(String aManejar,
								   String tipoEvento,
								   String idEvento,
								   String body){
		String retorno = null;
		if(this.esManejado(aManejar)){
			try {
				this.arregloEventos =
				JsonUtils.convertirMensajeJsonAObjectSNS(body, 
						ConstantesEventos.ELEMENTO_MENSAJE_SERVICIO_EVE, 
						new ProductorFactoryEvento());
				if(arregloEventos != null &&
				   arregloEventos.size() == 1){
					DeporteEvento de = this.prepararDeporteEventoObtencion(tipoEvento);
					ded.setObjetcSNSDeportivo(de);
					retorno = this.manejarObtencion();
				}
			} catch (ExcepcionJsonDeserializacion e) {
			e.printStackTrace();
			}
		}else{
			if(this.handler != null){
				retorno = this.handler.manejarObtencion(
						  aManejar,
						  tipoEvento,
						  idEvento,
						  body);
			}
		}
		return retorno;
	}
	
	public String manejarCreacion(String aManejar,
								  String tipoEvento,
								  String idEvento,
								  String body) 
						throws BDException, ExcepcionJsonDeserializacion {
		String retorno = null;
		if(this.esManejado(aManejar)){
			try {
				this.arregloUsuarios =
				JsonUtils.convertirMensajeJsonAObjectSNS(
				body, 
				ConstantesUsuarios.ELEMENTO_MENSAJE_SERVICIO_USU, 
				new ProductorFactoryUsuario());
				this.arregloEventos =
				JsonUtils.convertirMensajeJsonAObjectSNS(body, 
				ConstantesEventos.ELEMENTO_MENSAJE_SERVICIO_EVE, 
				new ProductorFactoryEvento());
				if(arregloUsuarios != null &&
					arregloUsuarios.size() == 1 &&
					arregloEventos != null &&
					arregloEventos.size() == 1){
					DeporteEvento de = this.prepararDeporteEvento(tipoEvento);
					ded.setObjetcSNSDeportivo(de);
					retorno = this.manejarCreacion();
				}
			} catch (ExcepcionJsonDeserializacion e) {
				e.printStackTrace();
				throw e;
			}
		}else{
			if(this.handler != null){
				retorno = this.handler.manejarCreacion(
						  aManejar,
						  tipoEvento,
						  idEvento,
						  body);
			}
		}
		return retorno;
	}
	
}
