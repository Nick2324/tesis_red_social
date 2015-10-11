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
		ObjectSNSDeportivo[] snsArray =
				super.getObjetoSNSDeportivoDB();
		Usuario[] usuarios = new Usuario[snsArray.length];
		for(int i = 0;i < snsArray.length; i++){
			usuarios[i] = (Usuario)snsArray[i];
		}
		
		return usuarios;
	
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

	@Override
	public void encontrarObjetoManejado() {
		// TODO Auto-generated method stub
		
	}
	
}
