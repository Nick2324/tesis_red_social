package com.sna_deportivo.filters;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;

@Provider
@PreMatching
public class OverrideMethodFilter implements ContainerRequestFilter{

	@Override
	public void filter(ContainerRequestContext contexto) throws IOException {
		String metodoReal = contexto.getMethod();
		String metodoSobreescrito = contexto.getHeaderString("X-HTTP-Method-Override");
		if(metodoSobreescrito != null && !metodoSobreescrito.equals(metodoReal)){
			contexto.setMethod(metodoSobreescrito);
		}
	}

}
