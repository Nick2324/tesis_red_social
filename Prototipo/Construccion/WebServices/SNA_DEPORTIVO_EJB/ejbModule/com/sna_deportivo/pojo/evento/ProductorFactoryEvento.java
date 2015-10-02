package com.sna_deportivo.pojo.evento;

import com.sna_deportivo.utils.gr.FactoryObjectSNSDeportivo;
import com.sna_deportivo.utils.gr.ProductorSNSDeportivo;
import com.sna_deportivo.utils.gr.excepciones.ProductorFactoryExcepcion;

public class ProductorFactoryEvento implements ProductorSNSDeportivo{
	
	public ProductorFactoryEvento(){}
	
	public EventosFactory getEventosFactory(String tipo) throws ProductorFactoryExcepcion{
		if(tipo.equals(TiposEventos.PRACTICADEPORTIVALIBRE.getServicio()))
			return new PracticaDeportivaFactory();
		if(tipo.equals(TiposEventos.EVENTODEPORTIVO.getServicio()))
			return new EventoDeportivoFactory();
		throw new ProductorFactoryExcepcion();
	}

	@Override
	public FactoryObjectSNSDeportivo producirFacObjetoSNS(String objetoAManejar) {
		String aCrear = null;
		for(TiposEventos ce:TiposEventos.values()){
			if(ce.getNombreClase().equals(objetoAManejar)){
				aCrear = ce.getServicio();
				break;
			}
		}
		return this.getEventosFactory(aCrear);
	}
	
}
