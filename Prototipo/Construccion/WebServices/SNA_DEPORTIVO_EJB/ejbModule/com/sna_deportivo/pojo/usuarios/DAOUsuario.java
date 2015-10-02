package com.sna_deportivo.pojo.usuarios;

import com.sna_deportivo.utils.bd.Entidades;
import com.sna_deportivo.utils.bd.excepciones.BDException;
import com.sna_deportivo.utils.gr.ObjectSNSDeportivo;
import com.sna_deportivo.utils.gr.ObjectSNSDeportivoDAO;

public class DAOUsuario extends ObjectSNSDeportivoDAO{
	
	public DAOUsuario(){
		this.setUpDAOGeneral();
	}
	
	public DAOUsuario(Usuario usuario){
		super.objectSNSDeportivo = usuario;
		this.setUpDAOGeneral();
	}
	
	public Usuario[] getUsuariosDB() throws BDException{

		return (Usuario[])super.getObjetoSNSDeportivoDB();
	
	}
	
	@Override
	protected void setUpDAOGeneral(){
		super.factoryOSNS = new FactoryUsuario();
		super.tipoObjetoSNS = Entidades.USUARIO;
		super.identificadorQueries = "usuario";
	}
	
	@Override
	public ObjectSNSDeportivo crearObjetoSNS() {
		return null;
	}
	
}
