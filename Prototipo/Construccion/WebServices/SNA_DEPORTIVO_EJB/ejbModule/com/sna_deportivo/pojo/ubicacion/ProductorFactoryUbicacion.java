package com.sna_deportivo.pojo.ubicacion;

import com.sna_deportivo.utils.gr.FactoryObjectSNSDeportivo;
import com.sna_deportivo.utils.gr.ProductorSNSDeportivo;
import com.sna_deportivo.utils.gr.excepciones.ProductorFactoryExcepcion;

public class ProductorFactoryUbicacion implements ProductorSNSDeportivo{

	@Override
	public FactoryObjectSNSDeportivo producirFacObjetoSNS(String objetoAManejar) 
			throws ProductorFactoryExcepcion {
		if(Ubicacion.class.getSimpleName().equals(objetoAManejar)){
			return new FactoryUbicacion();
		}else if(Ciudad.class.getSimpleName().equals(objetoAManejar)){
			return new FactoryCiudad();
		}else if(LugarPractica.class.getSimpleName().equals(objetoAManejar)){
			return new FactoryLugarPractica();
		}else if(Pais.class.getSimpleName().equals(objetoAManejar)){
			return new FactoryPais();
		}else{
			throw new ProductorFactoryExcepcion();
		}
	}

}
