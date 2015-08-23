package com.sna_deportivo.pojo.deportes;

import com.sna_deportivo.utils.gr.FactoryObjectSNSDeportivo;
import com.sna_deportivo.utils.gr.ObjectSNSDeportivo;

public class FactoryDeporte 
		implements FactoryObjectSNSDeportivo{

	public DAODeporte crearDAODeporte(){
		return new DAODeporte();
	}
	
	public Deporte crearDeporte(){
		return new Deporte();
	}
	
	public ObjectSNSDeportivo getObjetoSNS(){
		return this.crearDeporte();
	}
	
}
