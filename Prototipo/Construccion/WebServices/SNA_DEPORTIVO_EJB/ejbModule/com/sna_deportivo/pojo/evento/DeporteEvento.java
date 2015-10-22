package com.sna_deportivo.pojo.evento;

import com.sna_deportivo.pojo.deportes.Deporte;
import com.sna_deportivo.pojo.entidadesEstaticas.Genero;
import com.sna_deportivo.pojo.ubicacion.Ubicacion;
import com.sna_deportivo.utils.gr.ObjectSNSDeportivo;
import com.sna_deportivo.utils.gr.excepciones.AtributoInexistenteException;
import com.sna_deportivo.utils.json.JsonUtils;

import java.util.ArrayList;

public class DeporteEvento extends ObjectSNSDeportivo{
	
	private Integer id;
	private Evento evento;
	private Genero genero;
	private Ubicacion ubicacion;
	private Deporte deporte;
	private ArrayList<ObjectSNSDeportivo> participantes;
	private ArrayList<ObjectSNSDeportivo> invitaciones;
	private ArrayList<ObjectSNSDeportivo> solicitudes;
	
	public DeporteEvento(){}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}
	
	public void setDeporte(Deporte deporte) {
		this.deporte = deporte;
	}
	
	public void setGenero(Genero genero) {
		this.genero = genero;
	}
	
	public void setUbicacion(Ubicacion ubicacion) {
		this.ubicacion = ubicacion;
	}
	
	public void setParticipantes(ArrayList<ObjectSNSDeportivo> participantes) {
		this.participantes = participantes;
	}
	
	public void setSolicitudes(ArrayList<ObjectSNSDeportivo> solicitudes) {
		this.solicitudes = solicitudes;
	}
	
	public void setInvitaciones(ArrayList<ObjectSNSDeportivo> invitaciones) {
		this.invitaciones = invitaciones;
	}
	
	public Integer getId() {
		return id;
	}
	
	public Evento getEvento() {
		return evento;
	}
	
	public Deporte getDeporte() {
		return deporte;
	}
	
	public Genero getGenero() {
		return genero;
	}
	
	public Ubicacion getUbicacion() {
		return ubicacion;
	}
	
	public ArrayList<ObjectSNSDeportivo> getParticipantes() {
		return participantes;
	}
	
	public ArrayList<ObjectSNSDeportivo> getInvitaciones() {
		return invitaciones;
	}
	
	public ArrayList<ObjectSNSDeportivo> getSolicitudes() {
		return solicitudes;
	}
	
	@Override
	public String stringJson() {
		
		return "{"+JsonUtils.propiedadNulaTDPrimitivo(
				"id", this.id, null,-1)+"}";
	}

	@Override
	public ObjectSNSDeportivo setNullObject() {
		this.id = null;
		this.evento = null;
		this.genero = null;
		this.ubicacion = null;
		this.deporte = null;
		return this;
	}

	@Override
	protected String retornarToString() {
		return this.stringJson();
	}

	@Override
	protected boolean esAtributo(String atributo) {
		if(atributo != null){
			if(atributo.equals("id")){
				return true;
			}
		}
		return false;
	}

	@Override
	protected boolean setAtributo(String atributo, Object[] valor) {
		if(atributo.equals("id")){
			this.setId((((String)valor[0]).equals("null"))?null:
				Integer.parseInt((String)valor[0]));
			return true;
		}
		return false;
	}

	@Override
	protected String get(String atributo) throws AtributoInexistenteException {
		if(this.esAtributo(atributo)){
			if(atributo.equals("id")){
				return this.getId().toString();
			}
		}
		return null;
	}

	@Override
	public Class<?> getTipoDatoPropiedad(String propiedad) {
		throw new UnsupportedOperationException();
	}

}
