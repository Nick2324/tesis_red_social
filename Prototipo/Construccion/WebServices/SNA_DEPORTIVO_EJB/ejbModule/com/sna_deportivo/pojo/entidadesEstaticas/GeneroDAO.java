package com.sna_deportivo.pojo.entidadesEstaticas;

import com.sna_deportivo.utils.bd.Entidades;
import com.sna_deportivo.utils.gr.ObjectSNSDeportivo;
import com.sna_deportivo.utils.gr.ObjectSNSDeportivoDAO;

public class GeneroDAO extends ObjectSNSDeportivoDAO{
	
	public GeneroDAO(){
		super();
	}
	
	public GeneroDAO(Genero genero){
		super();
		super.objectSNSDeportivo = genero;
	}
	
	@Override
	protected void setUpDAOGeneral(){
		super.factoryOSNS = new FactoryGenero();
		super.tipoObjetoSNS = Entidades.GENERO;
		super.identificadorQueries = "genero";
	}
	
	@Override
	public ObjectSNSDeportivo crearObjetoSNS() {
		return null;
	}

	@Override
	public void encontrarObjetoManejado() {
		// TODO Auto-generated method stub
		
	}
	
}
