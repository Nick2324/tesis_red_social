package com.sna_deportivo.pojo.deportes;

import com.sna_deportivo.utils.gr.FactoryObjectSNSDeportivo;
import com.sna_deportivo.utils.gr.ProductorSNSDeportivo;

public class ProductorFactoryDeporte implements ProductorSNSDeportivo{

	public ProductorFactoryDeporte(){}
	
	@Override
	public FactoryObjectSNSDeportivo producirFacObjetoSNS(String objetoAManejar) {
		if(Deporte.class.getSimpleName().equals(objetoAManejar)){
			return new FactoryDeporte();
		}else{
			return null;
		}
	}

}
