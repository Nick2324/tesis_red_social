package com.sna_deportivo.pojo.usuarios;

import com.sna_deportivo.utils.bd.Entidades;
import com.sna_deportivo.utils.bd.excepciones.BDException;
import com.sna_deportivo.utils.gr.ObjectSNSDeportivoDAO;

public class DAOUsuario extends ObjectSNSDeportivoDAO{

	private Usuario usuario;
	
	public DAOUsuario(){}
	
	public DAOUsuario(Usuario usuario){
		this.usuario = usuario;
		super.objectSNSDeportivo = usuario;
		super.factoryOSNS = new FactoryUsuario();
	}
	
	public Usuario[] getUsuariosDB() throws BDException{

		return (Usuario[])super.getObjetoSNSDeportivoDB(Entidades.USUARIO, "usuario");
	
	}
	
}
