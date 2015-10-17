package com.sna_deportivo.pojo.entidadesEstaticas;

import com.sna_deportivo.utils.gr.excepciones.ProductorFactoryExcepcion;

import com.sna_deportivo.utils.gr.FactoryObjectSNSDeportivo;
import com.sna_deportivo.utils.gr.ProductorSNSDeportivo;

public class ProductorFactoryGenerales implements ProductorSNSDeportivo{
	
	public ProductorFactoryGenerales(){}
	
	public FactoryObjectSNSDeportivo getFactorySegunTipo(String tipo) throws ProductorFactoryExcepcion{
		if(tipo.equals(EntidadesGenerales.GENERO.getServicio())){
			return new FactoryGenero();
		}
		throw new ProductorFactoryExcepcion();
	}

	@Override
	public FactoryObjectSNSDeportivo producirFacObjetoSNS(String objetoAManejar) throws ProductorFactoryExcepcion{
		String aCrear = null;
		for(EntidadesGenerales ceg:EntidadesGenerales.values()){
			if(ceg.getNombreClase().equals(objetoAManejar)){
				aCrear = ceg.getServicio();
				break;
			}
		}
		return this.getFactorySegunTipo(aCrear);
	}
	
}
