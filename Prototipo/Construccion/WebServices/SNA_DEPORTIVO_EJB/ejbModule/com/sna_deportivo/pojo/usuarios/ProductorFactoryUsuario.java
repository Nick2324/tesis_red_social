package com.sna_deportivo.pojo.usuarios;

import com.sna_deportivo.utils.gr.FactoryObjectSNSDeportivo;
import com.sna_deportivo.utils.gr.ProductorSNSDeportivo;

public class ProductorFactoryUsuario implements ProductorSNSDeportivo{

	public ProductorFactoryUsuario(){}
	
	@Override
	public FactoryObjectSNSDeportivo producirFacObjetoSNS(String objetoAManejar) {
		if(Usuario.class.getSimpleName().equals(objetoAManejar)){
			return new FactoryUsuario();
		}else{
			return null;
		}
	}

}
