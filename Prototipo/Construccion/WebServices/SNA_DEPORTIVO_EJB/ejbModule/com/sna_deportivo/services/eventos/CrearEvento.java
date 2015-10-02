package com.sna_deportivo.services.eventos;

import java.util.ArrayList;

import com.sna_deportivo.pojo.deportes.ConstantesDeportes;
import com.sna_deportivo.pojo.deportes.Deporte;
import com.sna_deportivo.pojo.deportes.ProductorFactoryDeporte;
import com.sna_deportivo.pojo.entidadesEstaticas.ConstantesEntidadesGenerales;
import com.sna_deportivo.pojo.entidadesEstaticas.Genero;
import com.sna_deportivo.pojo.entidadesEstaticas.ProductorFactoryGenerales;
import com.sna_deportivo.pojo.evento.ConstantesEventos;
import com.sna_deportivo.pojo.evento.DeporteEvento;
import com.sna_deportivo.pojo.evento.DeporteEventoDAO;
import com.sna_deportivo.pojo.evento.Evento;
import com.sna_deportivo.pojo.evento.ProductorFactoryEvento;
import com.sna_deportivo.pojo.ubicaciones.Ubicacion;
import com.sna_deportivo.pojo.usuarios.ConstantesUsuarios;
import com.sna_deportivo.pojo.usuarios.ProductorFactoryUsuario;
import com.sna_deportivo.pojo.usuarios.Usuario;
import com.sna_deportivo.utils.gr.ObjectSNSDeportivo;
import com.sna_deportivo.utils.json.JsonUtils;
import com.sna_deportivo.utils.json.excepciones.ExcepcionJsonDeserializacion;

public class CrearEvento {

	private ArrayList<ObjectSNSDeportivo> eventos;
	private ArrayList<ObjectSNSDeportivo> usuarios;
	private ArrayList<ObjectSNSDeportivo> deportes;
	private ArrayList<ObjectSNSDeportivo> generos;
	private ArrayList<ObjectSNSDeportivo> ubicaciones;
	private GestionEvento gestionEvento;

	public CrearEvento(){
		this.gestionEvento = new GestionEvento();
	}
	
	public String crearEvento(String tipoEvento,String jsonString){
		String retornoEntidadesEventoCreado = null;
		ProductorFactoryEvento productorEventos = new ProductorFactoryEvento();
		ProductorFactoryUsuario productorUsuario = new ProductorFactoryUsuario();
		ProductorFactoryDeporte productorDeporte = new ProductorFactoryDeporte();
		ProductorFactoryGenerales productorGenerales = new ProductorFactoryGenerales();
		try {
			this.usuarios = JsonUtils.convertirMensajeJsonAObjectSNS(
					jsonString,
					ConstantesUsuarios.ELEMENTO_MENSAJE_SERVICIO_USU,
					productorUsuario);
			this.eventos = JsonUtils.convertirMensajeJsonAObjectSNS(
					jsonString,
					ConstantesEventos.ELEMENTO_MENSAJE_SERVICIO_EVE,
					productorEventos);
			this.deportes = JsonUtils.convertirMensajeJsonAObjectSNS(
					jsonString,
					ConstantesDeportes.ELEMENTO_MENSAJE_SERVICIO_DEP,
					productorDeporte);
			this.generos = JsonUtils.convertirMensajeJsonAObjectSNS(
					jsonString,
					ConstantesEntidadesGenerales.ELEMENTO_MENSAJE_SERVICIO_GEN,
					productorGenerales);
			/*this.ubicaciones = JsonUtils.convertirMensajeJsonAObjectSNS(
					jsonString,
					ConstantesEntidadesGenerales.ELEMENTO_MENSAJE_SERVICIO_GEN,
					productorGenerales);*/
			if(this.usuarios.size() == 1 &&
			   this.eventos.size() == 1 &&
			   this.generos.size() == 1 &&
			   this.deportes.size() == 1){
				Evento e = (Evento)this.eventos.get(0);
				e.setCreador((Usuario)this.usuarios.get(0));
				e = this.gestionEvento.crearEvento(tipoEvento,e);
				DeporteEvento dd = new DeporteEvento();
				dd.setEvento(e);
				dd.setGenero((Genero)this.generos.get(0));
				//dd.setUbicacion((Ubicacion)this.ubicaciones.get(0));
				dd.setDeporte((Deporte)this.deportes.get(0));
				DeporteEventoDAO edd = new DeporteEventoDAO();
				edd.setObjetcSNSDeportivo(dd);
				edd.crearObjetoSNS();
				retornoEntidadesEventoCreado = e.stringJson();
			}
		} catch (ExcepcionJsonDeserializacion e) {
			e.printStackTrace();
		}
		
		return retornoEntidadesEventoCreado;
	
	}
	
}
