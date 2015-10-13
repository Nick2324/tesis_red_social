package com.sna_deportivo.pojo.usuarios;

import javax.xml.bind.annotation.XmlRootElement;

import com.sna_deportivo.utils.gr.ObjectSNSDeportivo;
import com.sna_deportivo.utils.gr.excepciones.AtributoInexistenteException;
import com.sna_deportivo.utils.json.JsonObject;
import com.sna_deportivo.utils.json.JsonUtils;

@XmlRootElement
public class Usuario extends ObjectSNSDeportivo {
	
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
	private Boolean estado;
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
		estado = ((String)user.getPropiedades().get("estado")[0]).toLowerCase().equals("true")?true:false;
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
	public Boolean isEstado() {
		return estado;
	}
	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
	public String getTipoUsuario() {
		return tipoUsuario;
	}
	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}
	
	@Override
	protected String retornarToString() {
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
	
	protected boolean esAtributo(String atributo){
		if(atributo != null){
			if(atributo.equals("primerNombre")){
				return true;
			}
			if(atributo.equals("segundoNombre")){
				return true;
			}
			if(atributo.equals("apellidos")){
				return true;
			}
			if(atributo.equals("usuario")){
				return true;
			}
			if(atributo.equals("contrasena")){
				return true;
			}
			if(atributo.equals("eMail")){
				return true;
			}
			if(atributo.equals("numeroContacto")){
				return true;
			}
			if(atributo.equals("fechaNacimiento")){
				return true;
			}
			if(atributo.equals("fechaRegistro")){
				return true;
			}
			if(atributo.equals("sexo")){
				return true;
			}
			if(atributo.equals("estado")){
				return true;
			}
			if(atributo.equals("tipoUsuario")){
				return true;
			}
		}
		return false;
	}
	
	protected boolean setAtributo(String atributo,Object[] valor){
		boolean asignado = false;
		if(atributo != null){
			if(atributo.equals("primerNombre")){
				this.setPrimerNombre(JsonUtils.propiedadNulaBackwards((String)valor[0]));
				asignado = true;
			}else if(atributo.equals("segundoNombre")){
				this.setSegundoNombre(JsonUtils.propiedadNulaBackwards((String)valor[0]));
				asignado = true;
			}else if(atributo.equals("apellidos")){
				this.setApellidos(JsonUtils.propiedadNulaBackwards((String)valor[0]));
				asignado = true;
			}else if(atributo.equals("usuario")){
				this.setUsuario(JsonUtils.propiedadNulaBackwards((String)valor[0]));
				asignado = true;
			}else if(atributo.equals("contrasena")){
				this.setContrasena(JsonUtils.propiedadNulaBackwards((String)valor[0]));
				asignado = true;
			}else if(atributo.equals("eMail")){
				this.seteMail(JsonUtils.propiedadNulaBackwards((String)valor[0]));
				asignado = true;
			}else if(atributo.equals("numeroContacto")){
				this.setNumeroContacto(JsonUtils.propiedadNulaBackwards((String)valor[0]));
				asignado = true;
			}else if(atributo.equals("fechaNacimiento")){
				this.setFechaNacimiento(JsonUtils.propiedadNulaBackwards((String)valor[0]));
				asignado = true;
			}else if(atributo.equals("fechaRegistro")){
				this.setFechaRegistro(JsonUtils.propiedadNulaBackwards((String)valor[0]));
				asignado = true;
			}else if(atributo.equals("sexo")){
				this.setSexo(JsonUtils.propiedadNulaBackwards((String)valor[0]));
				asignado = true;
			}else if(atributo.equals("estado")){
				this.setEstado(Boolean.parseBoolean((String)valor[0]));
				asignado = true;
			}else if(atributo.equals("tipoUsuario")){
				this.setTipoUsuario(JsonUtils.propiedadNulaBackwards((String)valor[0]));
				asignado = true;
			}
		}
		return asignado;
	}

	@Override
	public String stringJson() {
		Object[] propiedades = new Object[]{
				this.primerNombre,
				this.segundoNombre,
				this.apellidos,
				this.usuario,
				this.contrasena,
				this.eMail,
				this.numeroContacto,
				this.fechaNacimiento,
				this.fechaRegistro,
				this.sexo,
				this.estado
		};
		return "{"+
				JsonUtils.propiedadNula("primerNombre",this.primerNombre,propiedades,0)+
			    JsonUtils.propiedadNula("segundoNombre",this.segundoNombre,propiedades,1)+
			    JsonUtils.propiedadNula("apellidos",this.apellidos,propiedades,2)+
			    JsonUtils.propiedadNula("usuario",this.usuario,propiedades,3)+
			    JsonUtils.propiedadNula("contrasena",this.contrasena,propiedades,4)+
			    JsonUtils.propiedadNula("eMail",this.eMail,propiedades,5)+
			    JsonUtils.propiedadNula("numeroContacto",this.numeroContacto,propiedades,6)+
			    JsonUtils.propiedadNula("fechaNacimiento",this.fechaNacimiento,propiedades,7)+
			    JsonUtils.propiedadNula("fechaRegistro",this.fechaRegistro,propiedades,8)+
			    JsonUtils.propiedadNula("sexo",this.sexo,propiedades,9)+
			    JsonUtils.propiedadNula("estado", this.estado,propiedades,10)+
			    "}";
		//Y ATRIBUTOS QUE NO ESTAN EN LA BD QUE? TipoUsuario, mas un label que otra cosa
	}

	@Override
	public ObjectSNSDeportivo setNullObject() {
		this.setPrimerNombre(null);
		this.setSegundoNombre(null);
		this.setApellidos(null);
		this.setUsuario(null);
		this.setContrasena(null);
		this.seteMail(null);
		this.setNumeroContacto(null);
		this.setFechaNacimiento(null);
		this.setFechaRegistro(null);
		this.setSexo(null);
		this.setEstado(null);
		this.setTipoUsuario(null);
		return this;
	}

	@Override
	protected String get(String atributo) throws AtributoInexistenteException {
		if(atributo.equals("primerNombre")){
			return this.getPrimerNombre();
		}else if(atributo.equals("segundoNombre")){
			return this.getSegundoNombre();
		}else if(atributo.equals("apellidos")){
			return this.getApellidos();
		}else if(atributo.equals("usuario")){
			return this.getUsuario();
		}else if(atributo.equals("contrasena")){
			return this.getContrasena();
		}else if(atributo.equals("eMail")){
			return this.geteMail();
		}else if(atributo.equals("numeroContacto")){
			return this.getNumeroContacto();
		}else if(atributo.equals("fechaNacimiento")){
			return this.getFechaNacimiento();
		}else if(atributo.equals("fechaRegistro")){
			return this.getFechaRegistro();
		}else if(atributo.equals("sexo")){
			return this.getSexo();
		}else if(atributo.equals("estado")){
			return this.isEstado().toString();
		}else if(atributo.equals("tipoUsuario")){
			return this.getTipoUsuario();
		}
		throw new AtributoInexistenteException();
	}

	@Override
	public Class<?> getTipoDatoPropiedad(String propiedad) {
		// TODO Auto-generated method stub
		return null;
	}

}
