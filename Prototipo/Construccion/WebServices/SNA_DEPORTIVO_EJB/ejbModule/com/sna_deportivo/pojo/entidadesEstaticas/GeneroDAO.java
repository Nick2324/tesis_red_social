package com.sna_deportivo.pojo.entidadesEstaticas;

import com.sna_deportivo.utils.bd.Entidades;
import com.sna_deportivo.utils.gr.ObjectSNSDeportivoDAO;

public class GeneroDAO extends ObjectSNSDeportivoDAO{
	
	public GeneroDAO(){
		this.setUpDAOUsuarioGeneral();
	}
	
	public GeneroDAO(Genero genero){
		super.objectSNSDeportivo = genero;
		this.setUpDAOUsuarioGeneral();
	}
	
	private void setUpDAOUsuarioGeneral(){
		super.factoryOSNS = new FactoryGenero();
		super.tipoObjetoSNS = Entidades.GENERO;
		super.identificadorQueries = "genero";
	}
	
}
