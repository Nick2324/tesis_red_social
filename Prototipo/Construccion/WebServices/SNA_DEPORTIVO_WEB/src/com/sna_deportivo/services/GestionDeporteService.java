package com.sna_deportivo.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import com.sna_deportivo.pojo.deportes.Deporte;
import com.sna_deportivo.services.deporte.GestionDeporte;

@Path("deportes/")
public class GestionDeporteService {
	
	private GestionDeporte gestionDeporte;
	
	public GestionDeporteService(){
		this.gestionDeporte = new GestionDeporte();
	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String consultarDeportes(Deporte deporte){
		try{
			return this.gestionDeporte.consultarDeportes(deporte);
		}catch(WebApplicationException e){
			e.printStackTrace();
			throw e;
		}catch(Exception e){
			e.printStackTrace();
			throw new WebApplicationException(500);
		}
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Deporte crearDeporte(Deporte deporte){
		try{
			return this.gestionDeporte.crearDeporte(deporte);
		}catch(WebApplicationException e){
			e.printStackTrace();
			throw e;
		}
	}
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	public void desactivarDeporte(Deporte deporte){
		try{
			this.gestionDeporte.desactivarDeporte(deporte);
		}catch(WebApplicationException e){
			e.printStackTrace();
			throw e;
		}catch(Exception e){
			e.printStackTrace();
			throw new WebApplicationException(500);
		}
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void actualizarDeporte(Deporte deporte){
		try{
			this.gestionDeporte.actualizarDeporte(deporte);
		}catch(WebApplicationException e){
			e.printStackTrace();
			throw e;
		}catch(Exception e){
			e.printStackTrace();
			throw new WebApplicationException(500);
		}
	}
	
}
