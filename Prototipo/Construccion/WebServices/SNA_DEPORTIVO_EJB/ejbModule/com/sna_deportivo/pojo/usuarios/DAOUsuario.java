package com.sna_deportivo.pojo.usuarios;

import com.sna_deportivo.utils.bd.Entidades;
import com.sna_deportivo.utils.bd.excepciones.BDException;
import com.sna_deportivo.utils.gr.ObjectSNSDeportivoDAO;

public class DAOUsuario extends ObjectSNSDeportivoDAO{
	
	public DAOUsuario(){
		this.setUpDAOUsuarioGeneral();
	}
	
	public DAOUsuario(Usuario usuario){
		super.objectSNSDeportivo = usuario;
		this.setUpDAOUsuarioGeneral();
	}
	
	public Usuario[] getUsuariosDB() throws BDException{

		return (Usuario[])super.getObjetoSNSDeportivoDB();
	
	}
	
	private void setUpDAOUsuarioGeneral(){
		super.factoryOSNS = new FactoryUsuario();
		super.tipoObjetoSNS = Entidades.USUARIO;
		super.identificadorQueries = "usuario";
	}
	
}
