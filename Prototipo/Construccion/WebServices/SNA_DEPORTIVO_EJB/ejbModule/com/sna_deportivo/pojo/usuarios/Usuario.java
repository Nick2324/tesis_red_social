package com.sna_deportivo.pojo.usuarios;

import javax.xml.bind.annotation.XmlRootElement;

import com.sna_deportivo.utils.json.JsonObject;

@XmlRootElement
public class Usuario {
	
	private String primerNombre;
	private String segundoNombre;
	private String apellidos;
	private String usuario;
	private String contrasena;
	private String eMail;
	private String numeroContacto;
	private String fechaNacimiento;
	private String fechaRegistro;
	private String sexo;
	private boolean estado;
	private String tipoUsuario;
	
	public Usuario(JsonObject user) {
		primerNombre = (String) user.getPropiedades().get("primerNombre")[0];
		segundoNombre = (String) user.getPropiedades().get("segundoNombre")[0];
		apellidos = (String) user.getPropiedades().get("apellidos")[0];
		usuario = (String) user.getPropiedades().get("usuario")[0];
		contrasena = (String) user.getPropiedades().get("contrasena")[0];
		eMail = (String) user.getPropiedades().get("eMail")[0];
		numeroContacto = (String) user.getPropiedades().get("numeroContacto")[0];
		fechaNacimiento = (String) user.getPropiedades().get("fechaNacimiento")[0];
		fechaRegistro = (String) user.getPropiedades().get("fechaRegistro")[0];
		sexo = (String) user.getPropiedades().get("sexo")[0];
		estado = ((String)user.getPropiedades().get("estado")[0]).toLowerCase() == "true"?true:false;
	}
	
	public Usuario() {
		// TODO Auto-generated constructor stub
	}

	public String getPrimerNombre() {
		return primerNombre;
	}
	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}
	public String getSegundoNombre() {
		return segundoNombre;
	}
	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getContrasena() {
		return contrasena;
	}
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	public String geteMail() {
		return eMail;
	}
	public void seteMail(String eMail) {
		this.eMail = eMail;
	}
	public String getNumeroContacto() {
		return numeroContacto;
	}
	public void setNumeroContacto(String numeroContacto) {
		this.numeroContacto = numeroContacto;
	}
	public String getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public String getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public boolean isEstado() {
		return estado;
	}
	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	public String getTipoUsuario() {
		return tipoUsuario;
	}
	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}
	
	@Override
	public String toString() {
		StringBuilder retorno = new StringBuilder("");
		retorno.append("{\"primerNombre\": \"" + primerNombre + "\",");
		retorno.append("\"segundoNombre\": \"" + segundoNombre + "\",");
		retorno.append("\"apellidos\": \"" + apellidos + "\",");
		retorno.append("\"usuario\": \"" + usuario + "\",");
		retorno.append("\"contrasena\": \"" + contrasena + "\",");
		retorno.append("\"eMail\": \"" + eMail + "\",");
		retorno.append("\"numeroContacto\": \"" + numeroContacto + "\",");
		retorno.append("\"fechaNacimiento\": \"" + fechaNacimiento + "\",");
		retorno.append("\"fechaRegistro\": \"" + fechaRegistro + "\",");
		retorno.append("\"sexo\": \"" + sexo + "\",");
		retorno.append("\"estado\": \"" + estado + "\"}");
		return retorno.toString();
	}

}
