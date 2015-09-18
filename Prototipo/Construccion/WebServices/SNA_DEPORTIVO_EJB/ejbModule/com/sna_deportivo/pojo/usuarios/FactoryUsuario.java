package com.sna_deportivo.pojo.usuarios;

import com.sna_deportivo.utils.gr.FactoryObjectSNSDeportivo;
import com.sna_deportivo.utils.gr.ObjectSNSDeportivo;
import com.sna_deportivo.utils.gr.ObjectSNSDeportivoDAO;

public class FactoryUsuario implements FactoryObjectSNSDeportivo {

	public DAOUsuario crearDAOUsuario(){
		return new DAOUsuario();
	}
	
	public Usuario crearUsuario(){
		return new Usuario();
	}
	
	@Override
	public ObjectSNSDeportivo getObjetoSNS() {
		return this.crearUsuario();
	}

	@Override
	public ObjectSNSDeportivoDAO getObjetoSNSDAO() {
		return this.crearDAOUsuario();
	}

}
