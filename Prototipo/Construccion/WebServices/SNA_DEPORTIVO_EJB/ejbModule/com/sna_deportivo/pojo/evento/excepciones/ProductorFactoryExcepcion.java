package com.sna_deportivo.pojo.evento.excepciones;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class ProductorFactoryExcepcion extends WebApplicationException{
	
	public ProductorFactoryExcepcion() {
		super(Response.Status.NOT_FOUND);
	}
	
}
