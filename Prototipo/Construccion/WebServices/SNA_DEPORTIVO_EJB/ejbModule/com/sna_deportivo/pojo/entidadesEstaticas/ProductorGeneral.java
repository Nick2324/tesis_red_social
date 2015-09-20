package com.sna_deportivo.pojo.entidadesEstaticas;

import com.sna_deportivo.utils.gr.excepciones.ProductorFactoryExcepcion;
import com.sna_deportivo.utils.gr.FactoryObjectSNSDeportivo;

public class ProductorGeneral {

	
	
	public ProductorGeneral(){}
	
	public FactoryObjectSNSDeportivo getFactorySegunTipo(String tipo){
		if(tipo.equals(ConstantesEntidadesGenerales.GENERO.getServicio())){
			return new FactoryGenero();
		}
		throw new ProductorFactoryExcepcion();
	}
	
}
