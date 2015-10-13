package com.sna_deportivo.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import com.sna_deportivo.services.general.GestionGenerales;

@Path("generales/")
public class GestionGeneralesService {

	private GestionGenerales servicio;
	
	public GestionGeneralesService(){
		this.servicio = new GestionGenerales();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{general}")
	public String getEntidadGeneral(@PathParam("general") String tipoGeneral){
		try{
			return this.servicio.getEntidadGeneral(tipoGeneral);
		}catch(WebApplicationException e){
			throw e;
		}catch(Exception e){
			e.printStackTrace();
			throw new WebApplicationException(500);
		}
	}
	
}
