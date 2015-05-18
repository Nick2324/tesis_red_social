package com.sna_deportivo.pojo.evento;

import java.lang.reflect.Field;
import java.util.Map;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;

import com.sna_deportivo.utils.Constantes;
import com.sna_deportivo.utils.Entidades;
//import com.sna_deportivo.utils.UtilDB;
import com.sna_deportivo.utils.Utils;

public abstract class DAOEvento {
	
	private Evento evento;
	
	protected DAOEvento(){}
	
	protected DAOEvento(Evento e){
		this.evento = e;
	}
	
	public void setEvento(Evento e){
		this.evento = e;
	}
	
	public Evento getEvento(){
		return this.evento;
	}
	
	public Evento getEventoDB(){
		Evento evento = null;
		if(this.evento != null){
			String respuesta;
			String query = "MATCH (n:"+Entidades.EVENTODEPORTIVO +") WHERE " +
					"n.nombre = " + this.evento.getNombre() +
					" RETURN n";
					//" AND n.id = " + this.evento.getId() +
					//sin descripcion
					//" AND n.descripcion "
			//Futura idea
			/*for(Field campo:Evento.class.getDeclaredFields()){
				if(UtilDB.esCampoBD(campo))
					query += campo.getName() + " = ";
			}*/
			String payload = "{\"statements\":[{\"statement\":\"" + query + "\"}]}";
			ResteasyClient cliente = Utils.obtenerCliente();
			WebTarget target = cliente.target(Constantes.SERVER_ROOT_URI)
					.path("transaction/commit");
			Response result = target
					.request()
					.accept(MediaType.APPLICATION_JSON + "; charset=UTF-8")
					.post(Entity.entity(payload, MediaType.APPLICATION_JSON),
							Response.class);
			if (result.getStatus() == 200) {
				respuesta = result.readEntity(String.class);
				if (respuesta.startsWith("{"))
					respuesta = respuesta.substring(1,
							respuesta.length() - 1);
			}		
		}
		
		evento = new PracticaDeportiva();
		evento.setId("1");
		evento.setNombre("Evento1");
		
		return evento;
	}
	
	public boolean updateEventoDB(){
		return true;
	}
	
	public boolean updateEventoDB(String[] propiedades){
		for(String propiedad:propiedades){
			
		}
		return true;
	}
	
	public boolean deleteEventoDB(){
		return true;
	}
	
	public boolean crearEventoDB(){
		return true;
	}
	
}
