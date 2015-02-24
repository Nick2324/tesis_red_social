package com.sna_deportivo.services;

import com.sna_deportivo.pojo.ResponseGenerico;
import com.sna_deportivo.pojo.Usuario;
import com.sna_deportivo.utils.Utils;

public class GestionUsuario {
	
	public String crearUsuario(){
		return "Usuario creado satisfactoriamente.";
	}
	public ResponseGenerico verificarUsuario(String usuario,String pass){
		
		Utils.servidorActivo();
		
		ResponseGenerico response = new ResponseGenerico();
		response.setCaracterAceptacion("B");
		response.setMesnajeRespuesta("Usuario - " + usuario + " -- pass - " + pass);
		return response;
	}
	public Usuario datosUsuario(String user){
		return new Usuario();
	}

	
}
