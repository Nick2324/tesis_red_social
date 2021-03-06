package com.sna_deportivo.pojo.evento;

import java.util.ArrayList;

import com.sna_deportivo.pojo.deportes.DAODeporte;
import com.sna_deportivo.pojo.deportes.Deporte;
import com.sna_deportivo.pojo.deportes.ProductorFactoryDeporte;
import com.sna_deportivo.pojo.entidadesEstaticas.Genero;
import com.sna_deportivo.pojo.entidadesEstaticas.GeneroDAO;
import com.sna_deportivo.pojo.entidadesEstaticas.ProductorFactoryGenerales;
import com.sna_deportivo.pojo.usuarios.DAOUsuario;
import com.sna_deportivo.pojo.usuarios.ProductorFactoryUsuario;
import com.sna_deportivo.pojo.usuarios.Usuario;
import com.sna_deportivo.pojo.ubicacion.ProductorFactoryUbicacion;
import com.sna_deportivo.pojo.ubicacion.Ubicacion;
import com.sna_deportivo.utils.bd.BDUtils;
import com.sna_deportivo.utils.bd.Entidades;
import com.sna_deportivo.utils.bd.RelacionSNS;
import com.sna_deportivo.utils.bd.Relaciones;
import com.sna_deportivo.utils.bd.excepciones.BDException;
import com.sna_deportivo.utils.gr.FactoryObjectSNSDeportivo;
import com.sna_deportivo.utils.gr.ObjectSNSDeportivo;
import com.sna_deportivo.utils.gr.ObjectSNSDeportivoDAO;
import com.sna_deportivo.utils.gr.excepciones.ProductorFactoryExcepcion;
import com.sna_deportivo.utils.json.JsonObject;
import com.sna_deportivo.utils.json.JsonUtils;
import com.sna_deportivo.utils.json.excepciones.ExcepcionJsonDeserializacion;

public class DeporteEventoDAO extends ObjectSNSDeportivoDAO{

	public DeporteEventoDAO(){
		super();
	}
	
	@Override
	protected void setUpDAOGeneral() {
		this.factoryOSNS = new FactoryDeporteEvento();
		this.identificadorQueries = "deporteEvento";
		this.tipoObjetoSNS = Entidades.DEPORTEEVENTO;
	}

	@Override
	public void encontrarObjetoManejado()  
			throws BDException,ProductorFactoryExcepcion,ProductorFactoryExcepcion{
		if(this.objectSNSDeportivo != null){
			DeporteEvento de = (DeporteEvento)
					this.objectSNSDeportivo;
			StringBuilder query = new StringBuilder();
			query.append("MATCH ");
			query.append(this.producirNodoMatchNoJson());
			query.append(",");
			
			//Encontrando objeto por cada uno de los nodos
			//que se unen a el
			RelacionSNS relacion = null;
			if(de.getDeporte() != null){
				relacion = new RelacionSNS(Relaciones.DESCRIPCIONEVENTO,
										   "relacionDeporte",
										   RelacionSNS.DIRECCION_ENTRADA,
										   de.getDeporte());
			}
			
			if(de.getEvento() != null){
				relacion = new RelacionSNS(Relaciones.DESCRIPCIONEVENTO,
						   "relacionEvento",
						   RelacionSNS.DIRECCION_ENTRADA,
						   de.getEvento());
				//Patrones para evento
				query = this.integrarQueryRelacion(query, 
								relacion, 
								new ProductorFactoryEvento());

			}
			
			if(de.getGenero() != null){
				relacion = new RelacionSNS(Relaciones.DESCRIPCIONEVENTO,
						   "relacionGenero",
						   RelacionSNS.DIRECCION_ENTRADA,
						   de.getGenero());
				//Patrones para genero
				query = this.integrarQueryRelacion(query, 
								relacion,
								new ProductorFactoryGenerales());
			}
			
			if(de.getInvitaciones() != null &&
			   de.getInvitaciones().size() > 0){
				relacion = new RelacionSNS(Relaciones.INVITADOAPARTICIPAR,
						   "relacionInvitacion",
						   RelacionSNS.DIRECCION_ENTRADA,
						   de.getInvitaciones());
				//Patrones para invitaciones
				query = this.integrarQueryRelacion(query, 
								relacion,
								new ProductorFactoryUsuario());
			}
			
			if(de.getSolicitudes() != null &&
			   de.getSolicitudes().size() > 0){
				relacion = new RelacionSNS(Relaciones.SOLICITAPARTICIPAR,
						   "relacionSolicitud",
						   RelacionSNS.DIRECCION_ENTRADA,
						   de.getSolicitudes());
				//Patrones para solicitudes
				query = this.integrarQueryRelacion(query, 
								relacion, 
								new ProductorFactoryUsuario());
			}
			
			if(de.getParticipantes() != null &&
			   de.getParticipantes().size() > 0){
				relacion = new RelacionSNS(Relaciones.PARTICIPANTEEVENTO,
						   "relacionParticipante",
						   RelacionSNS.DIRECCION_ENTRADA,
						   de.getParticipantes());
				//Patrones para participantes
				query = this.integrarQueryRelacion(query, 
								relacion,
								new ProductorFactoryUsuario());
			}
			
			query = new StringBuilder(query.substring(0, query.length() - 1));
			query.append(" RETURN ");
			query.append(this.identificadorQueries);
			
			try{
				Object[] resultado = BDUtils.ejecutarQueryREST(query.toString());
				if(resultado.length == 1){
					Object[] datos =
							((JsonObject)BDUtils.obtenerRestRegistro(resultado[0])).
							getPropiedades().
							get("data");
					if(datos.length == 1){
						try {
							de.deserializarJson((JsonObject)datos[0]);
							this.setObjetcSNSDeportivo(de);
						} catch (ExcepcionJsonDeserializacion e) {
							e.printStackTrace();
						}
					}else{
						throw new BDException();
					}
				}else{
					throw new BDException();
				}
			}catch(BDException e){
				e.printStackTrace();
				throw e;
			}
			
		}
	}
	
	@Override
	public ObjectSNSDeportivo crearObjetoSNS() throws ProductorFactoryExcepcion{
		final DeporteEvento de = (DeporteEvento)super.objectSNSDeportivo;
		de.setId(BDUtils.generarNumeradorEntidad(this.tipoObjetoSNS));
		super.objectSNSDeportivo = de;
		StringBuilder query = new StringBuilder("CREATE (");
		query.append(this.identificadorQueries);
		query.append(":");
		query.append(this.tipoObjetoSNS);
		query.append(de.stringJson());
		query.append(") RETURN "+this.identificadorQueries);
		BDUtils.ejecutarQueryREST(query.toString());
		RelacionSNS relacionEventoNAria = 
				new RelacionSNS(Relaciones.DESCRIPCIONEVENTO,
								"descripcionEvento",
								RelacionSNS.DIRECCION_ENTRADA);
		if(de.getEvento() != null){
			relacionEventoNAria.setObjetoRelacion(de.getEvento());
			super.crearRelacion(relacionEventoNAria, 
								new ProductorFactoryEvento());
		}
		
		this.crearDeporte();
		this.crearGenero();
		this.crearUbicacion();
		
		return this.objectSNSDeportivo;
	}
	
	public String crearInvitaciones() throws BDException,ProductorFactoryExcepcion{
		String resultado = null;
		if(this.objectSNSDeportivo != null){
			DeporteEvento de = (DeporteEvento)this.objectSNSDeportivo;
			if(de.getInvitaciones() != null &&
			   de.getInvitaciones().size() > 0){
				RelacionSNS relacionACrear =
						new RelacionSNS(Relaciones.INVITADOAPARTICIPAR,
								"invitacionParticipar",
								RelacionSNS.DIRECCION_ENTRADA,
								de.getInvitaciones());
				ArrayList<Boolean> existeRelacion =
						this.verificarExistenciaRelacion(relacionACrear, 
								new ProductorFactoryUsuario());
				if(existeRelacion != null && 
				   existeRelacion.size() > 0 &&
				   !existeRelacion.get(0)){
					if(!super.crearRelacion(relacionACrear, 
							new ProductorFactoryUsuario())){
						throw new BDException();
					}
				}
			}
		}
		return resultado;
	}
	
	public String crearSolicitudes() throws BDException,ProductorFactoryExcepcion{
		String resultado = null;
		if(this.objectSNSDeportivo != null){
			DeporteEvento de = (DeporteEvento)this.objectSNSDeportivo;
			if(de.getSolicitudes() != null &&
			   de.getSolicitudes().size() > 0){
				RelacionSNS relacionACrear =
						new RelacionSNS(Relaciones.SOLICITAPARTICIPAR,
								"solicitudParticipar",
								RelacionSNS.DIRECCION_ENTRADA,
								de.getSolicitudes());
				ArrayList<Boolean> existeRelacion =
						this.verificarExistenciaRelacion(relacionACrear, 
								new ProductorFactoryUsuario());
				if(existeRelacion != null && 
				   existeRelacion.size() > 0 &&
				   !existeRelacion.get(0)){
					if(!super.crearRelacion(relacionACrear, 
							new ProductorFactoryUsuario())){
						throw new BDException();
					}
				}
			}
		}
		return resultado;
	}
	
	public String crearParticipantes() throws BDException,ProductorFactoryExcepcion{
		String resultado = null;
		if(this.objectSNSDeportivo != null){
			DeporteEvento de = (DeporteEvento)this.objectSNSDeportivo;
			try {
				if(de.getParticipantes() != null &&
				   de.getParticipantes().size() > 0){
					RelacionSNS relacionACrear =
							new RelacionSNS(Relaciones.PARTICIPANTEEVENTO,
											"crearParticipante",
											RelacionSNS.DIRECCION_ENTRADA,
											de.getParticipantes());
					ArrayList<Boolean> existeRelacion =
							super.verificarExistenciaRelacion(relacionACrear, 
									new ProductorFactoryUsuario());
					if(existeRelacion != null &&
					   existeRelacion.size() > 0 &&
					   !existeRelacion.get(0)){
						de.setSolicitudes(de.getParticipantes());
						de.setInvitaciones(de.getParticipantes());
						this.objectSNSDeportivo = de;
						this.eliminarInvitaciones();
						this.eliminarSolicitudes();
						de.setInvitaciones(null);
						de.setSolicitudes(null);
						this.objectSNSDeportivo = de;
						if(!super.crearRelacion(relacionACrear, 
								new ProductorFactoryUsuario())){
							throw new BDException();
						}
					}
				}
			} catch (ExcepcionJsonDeserializacion e) {
				e.printStackTrace();
			}
		}
		return resultado;
	}
	
	public String getInvitadosEvento()
			throws BDException,ExcepcionJsonDeserializacion{
		String retorno = "[]";
		try {
			if(this.objectSNSDeportivo != null){
					RelacionSNS relacionParticipantes = 
							new RelacionSNS(Relaciones.INVITADOAPARTICIPAR,
										"invitadoEvento",
										RelacionSNS.DIRECCION_ENTRADA);
				//COMO ABSTRAER?
				ObjectSNSDeportivoDAO dao = new ProductorFactoryUsuario().
						producirFacObjetoSNS(Usuario.class.getSimpleName()).getObjetoSNSDAO();
					
				//Formando query
				StringBuilder query = new StringBuilder();
				query.append("MATCH ");
				query.append(this.producirNodoMatch());
				query.append(relacionParticipantes.stringJson());
				query.append(dao.producirNodoMatchNoJson());
				query.append(" RETURN ");
				query.append(dao.getIdentificadorQueries());
				
				//Ejecutando query
				Object[] resultado = BDUtils.ejecutarQueryREST(query.toString());
				if(resultado != null && resultado.length > 0 &&
				   BDUtils.obtenerRestRegistro(resultado[0]) != null){
					ObjectSNSDeportivo[] invitados = new ObjectSNSDeportivo[resultado.length];
					for(int i = 0; i < resultado.length; i++){
						JsonObject datos = 
								(JsonObject)BDUtils.obtenerRestRegistro(resultado[i]).
								getPropiedades().get("data")[0];
						invitados[i] = dao.getFactoryOSNS().getObjetoSNS();
						try {
							invitados[i].deserializarJson(datos);
						} catch (ExcepcionJsonDeserializacion e) {
							e.printStackTrace();
							throw e;
						}
					}
					retorno = JsonUtils.arrayObjectSNSToStringJson(invitados);
				}
			}
		} catch (BDException e) {
			e.printStackTrace();
			throw e;
		}
		return retorno;
	}
	
	public String getSolicitudesEvento()
			throws BDException,ExcepcionJsonDeserializacion{
		String retorno = "[]";
		try {
			if(this.objectSNSDeportivo != null){
				RelacionSNS relacionParticipantes = 
						new RelacionSNS(Relaciones.SOLICITAPARTICIPAR,
										"solicitanteParticipaEvento",
										RelacionSNS.DIRECCION_ENTRADA);
				//COMO ABSTRAER?
				ObjectSNSDeportivoDAO dao = new ProductorFactoryUsuario().
						producirFacObjetoSNS(Usuario.class.getSimpleName()).getObjetoSNSDAO();
				
				//Formando query
				StringBuilder query = new StringBuilder();
				query.append("MATCH ");
				query.append(this.producirNodoMatch());
				query.append(relacionParticipantes.stringJson());
				query.append(dao.producirNodoMatchNoJson());
				query.append(" RETURN ");
				query.append(dao.getIdentificadorQueries());
					
				//Ejecutando query				
				Object[] resultado = BDUtils.ejecutarQueryREST(query.toString());
				if(resultado != null && resultado.length > 0){
					ObjectSNSDeportivo[] solicitudes = new ObjectSNSDeportivo[resultado.length];
					for(int i = 0; i < resultado.length; i++){
						JsonObject datos = 
								(JsonObject)BDUtils.obtenerRestRegistro(resultado[i]).
								getPropiedades().get("data")[0];
						solicitudes[i] = dao.getFactoryOSNS().getObjetoSNS();

						try {
							solicitudes[i].deserializarJson(datos);
						} catch (ExcepcionJsonDeserializacion e) {
							e.printStackTrace();
							throw e;
						}
					}
					retorno = JsonUtils.arrayObjectSNSToStringJson(solicitudes);
				}
			}
		} catch (BDException e) {
			e.printStackTrace();
			throw e;
		}
		
		return retorno;
	
	}
	
	public String getParticipantesEvento() 
			throws BDException,ExcepcionJsonDeserializacion{
		String retorno = "[]";
		try {
			if(this.objectSNSDeportivo != null){
				RelacionSNS relacionParticipantes = 
						new RelacionSNS(Relaciones.PARTICIPANTEEVENTO,
										"participaEvento",
										RelacionSNS.DIRECCION_ENTRADA);
				//COMO ABSTRAER?
				ObjectSNSDeportivoDAO dao = new ProductorFactoryUsuario().
						producirFacObjetoSNS(Usuario.class.getSimpleName()).getObjetoSNSDAO();
					
				//Formando query
				StringBuilder query = new StringBuilder();
				query.append("MATCH ");
				query.append(this.producirNodoMatch());
				query.append(relacionParticipantes.stringJson());
				query.append(dao.producirNodoMatchNoJson());
				query.append(" RETURN ");
				query.append(dao.getIdentificadorQueries());
					
				//Ejecutando query
				Object[] resultado = BDUtils.ejecutarQueryREST(query.toString());
				if(resultado != null && resultado.length > 0){
					ObjectSNSDeportivo[] participantes = new ObjectSNSDeportivo[resultado.length];
					for(int i = 0; i < resultado.length; i++){
						JsonObject datos = 
								(JsonObject)BDUtils.obtenerRestRegistro(resultado[i]).
								getPropiedades().get("data")[0];
						participantes[i] = dao.getFactoryOSNS().getObjetoSNS();
						try {
							participantes[i].deserializarJson(datos);
						} catch (ExcepcionJsonDeserializacion e) {
							e.printStackTrace();
							throw e;
						}
					}
					retorno = JsonUtils.arrayObjectSNSToStringJson(participantes);
				}
			}
		} catch (BDException e) {
			e.printStackTrace();
			throw e;
		}
		return retorno;
	}
	
	public String eliminarSolicitudes()
			throws BDException,ExcepcionJsonDeserializacion,ProductorFactoryExcepcion{
		String retorno = null;
		if(this.objectSNSDeportivo != null){
			DeporteEvento de = (DeporteEvento)this.objectSNSDeportivo;
			if(de.getSolicitudes() != null &&
			   de.getSolicitudes().size() > 0){
				RelacionSNS relacionACrear =
						new RelacionSNS(Relaciones.SOLICITAPARTICIPAR,
								"solicitudParticipar",
								RelacionSNS.DIRECCION_ENTRADA,
								de.getSolicitudes());
				if(!super.eliminarRelacion(relacionACrear, 
						new ProductorFactoryUsuario())){
					throw new BDException();
				}
			}
		}
		return retorno;
	}
	
	public String eliminarParticipantes()
			throws BDException,ExcepcionJsonDeserializacion,ProductorFactoryExcepcion{
		String retorno = null;
		if(this.objectSNSDeportivo != null){
			DeporteEvento de = (DeporteEvento)this.objectSNSDeportivo;
			if(de.getParticipantes() != null &&
			   de.getParticipantes().size() > 0){
				RelacionSNS relacionACrear =
						new RelacionSNS(Relaciones.PARTICIPANTEEVENTO,
								"relacionParticipante",
								RelacionSNS.DIRECCION_ENTRADA,
								de.getParticipantes());
				if(!super.eliminarRelacion(relacionACrear, 
						new ProductorFactoryUsuario())){
					throw new BDException();
				}
			}
		}
		return retorno;
	}
	
	public String eliminarInvitaciones()
			throws BDException,ExcepcionJsonDeserializacion,ProductorFactoryExcepcion{
		String retorno = null;
		if(this.objectSNSDeportivo != null){
			DeporteEvento de = (DeporteEvento)this.objectSNSDeportivo;
			if(de.getInvitaciones() != null &&
			   de.getInvitaciones().size() > 0){
				RelacionSNS relacionACrear =
						new RelacionSNS(Relaciones.INVITADOAPARTICIPAR,
								"invitacionParticipar",
								RelacionSNS.DIRECCION_ENTRADA,
								de.getInvitaciones());
				if(!super.eliminarRelacion(relacionACrear, 
						new ProductorFactoryUsuario())){
					throw new BDException();
				}
			}
		}
		return retorno;
	}
	
	public boolean eliminarDeporte() throws BDException,ProductorFactoryExcepcion{
		if(this.objectSNSDeportivo != null){
			RelacionSNS aEliminar = new RelacionSNS(Relaciones.DESCRIPCIONEVENTO,
					"deporteEventoRelacion",
					RelacionSNS.DIRECCION_ENTRADA,
					new Deporte());
			return this.eliminarRelacion(aEliminar,
					new ProductorFactoryDeporte());
		}
		return true;
	}
	
	public boolean eliminarGenero() throws BDException,ProductorFactoryExcepcion{
		if(this.objectSNSDeportivo != null){
			RelacionSNS aEliminar = new RelacionSNS(Relaciones.DESCRIPCIONEVENTO,
					"generoEventoRelacion",
					RelacionSNS.DIRECCION_ENTRADA,
					new Genero());
			return this.eliminarRelacion(aEliminar,
					new ProductorFactoryGenerales());
		}
		return true;
	}
	
	public boolean eliminarUbicacion() throws BDException, ProductorFactoryExcepcion{
		if(this.objectSNSDeportivo != null){
			RelacionSNS aEliminar = new RelacionSNS(Relaciones.DESCRIPCIONEVENTO,
					"ubicacionEventoRelacion",
					RelacionSNS.DIRECCION_ENTRADA,
					new Ubicacion());
			return this.eliminarRelacion(aEliminar,
					new ProductorFactoryUbicacion());
		}
		return true;
	}
	
	public boolean actualizarDeporteEvento() throws BDException,ProductorFactoryExcepcion{
		//Ampliar a todo el evento, con invitaciones, participantes, etc.
		boolean eliminacionesExitosas = false;
		if(this.objectSNSDeportivo != null){
			DeporteEvento de =(DeporteEvento)this.objectSNSDeportivo;
			de.setEvento(null);
			if(de.getDeporte() != null){
				eliminacionesExitosas = this.eliminarDeporte();
			}else{
				eliminacionesExitosas = true;
			}
			if(eliminacionesExitosas){
				if(de.getGenero() != null){
					eliminacionesExitosas = 
							this.eliminarGenero();
				}else{
					eliminacionesExitosas = true;
					
				}
				if(eliminacionesExitosas){
					if(de.getUbicacion() != null){
						eliminacionesExitosas = 
								this.eliminarUbicacion();
					}else{
						eliminacionesExitosas = true;
					}
				}
			}
			if(eliminacionesExitosas){
				this.crearDeporte();
				this.crearGenero();
				this.crearUbicacion();
			}
		}
		return eliminacionesExitosas;
	}
	
	public String obtenerDeporteEvento() 
			throws BDException,ExcepcionJsonDeserializacion{
		String retorno = "{}";
		try{
			if(this.objectSNSDeportivo != null){
				RelacionSNS relacionParticipantes = 
						new RelacionSNS(Relaciones.DESCRIPCIONEVENTO,
										"participaEvento",
										RelacionSNS.DIRECCION_ENTRADA);
				//COMO ABSTRAER?
				ObjectSNSDeportivoDAO dao = new ProductorFactoryDeporte().
						producirFacObjetoSNS(Deporte.class.getSimpleName()).getObjetoSNSDAO();
					
				//Formando query
				StringBuilder query = new StringBuilder();
				query.append("MATCH ");
				query.append(this.producirNodoMatch());
				query.append(relacionParticipantes.stringJson());
				query.append(dao.producirNodoMatchNoJson());
				query.append(" RETURN ");
				query.append(dao.getIdentificadorQueries());
					
				//Ejecutando query
				Object[] resultado = BDUtils.ejecutarQueryREST(query.toString());
				if(resultado != null && resultado.length > 0){
					ObjectSNSDeportivo[] deportes = new ObjectSNSDeportivo[resultado.length];
					for(int i = 0; i < resultado.length; i++){
						JsonObject datos = 
								(JsonObject)BDUtils.obtenerRestRegistro(resultado[i]).
								getPropiedades().get("data")[0];
						deportes[i] = dao.getFactoryOSNS().getObjetoSNS();
						try {
							deportes[i].deserializarJson(datos);
						} catch (ExcepcionJsonDeserializacion e) {
							e.printStackTrace();
							throw e;
						}
					}
					if(deportes.length == 1){
						retorno = deportes[0].stringJson();
					}
				}
			}
		} catch (BDException e) {
			e.printStackTrace();
			throw e;
		}
		
		return retorno;
	}
	
	public String obtenerGeneroEvento()
			throws BDException,ExcepcionJsonDeserializacion,ProductorFactoryExcepcion{
		String retorno = "{}";
		try{
			if(this.objectSNSDeportivo != null){
				RelacionSNS relacionParticipantes = 
						new RelacionSNS(Relaciones.DESCRIPCIONEVENTO,
										"participaEvento",
										RelacionSNS.DIRECCION_ENTRADA);
				//COMO ABSTRAER?
				ObjectSNSDeportivoDAO dao = new ProductorFactoryGenerales().
						producirFacObjetoSNS(Genero.class.getSimpleName()).getObjetoSNSDAO();
					
				//Formando query
				StringBuilder query = new StringBuilder();
				query.append("MATCH ");
				query.append(this.producirNodoMatch());
				query.append(relacionParticipantes.stringJson());
				query.append(dao.producirNodoMatchNoJson());
				query.append(" RETURN ");
				query.append(dao.getIdentificadorQueries());
					
				//Ejecutando query
				Object[] resultado = BDUtils.ejecutarQueryREST(query.toString());
				if(resultado != null && resultado.length > 0){
					ObjectSNSDeportivo[] generos = new ObjectSNSDeportivo[resultado.length];
					for(int i = 0; i < resultado.length; i++){
						JsonObject datos = 
								(JsonObject)BDUtils.obtenerRestRegistro(resultado[i]).
								getPropiedades().get("data")[0];
						generos[i] = dao.getFactoryOSNS().getObjetoSNS();
						try {
							generos[i].deserializarJson(datos);
						} catch (ExcepcionJsonDeserializacion e) {
							e.printStackTrace();
							throw e;
						}
					}
					if(generos.length == 1){
						retorno = generos[0].stringJson();
					}
				}
			}
		} catch (BDException e) {
			e.printStackTrace();
			throw e;
		}catch (ProductorFactoryExcepcion e){
			e.printStackTrace();
			throw e;
		} catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return retorno;
	}
	
	public void crearDeporte() throws ProductorFactoryExcepcion{
		if(this.objectSNSDeportivo != null){
			DeporteEvento de = (DeporteEvento)this.objectSNSDeportivo;
			RelacionSNS relacionEventoNAria = 
					new RelacionSNS(Relaciones.DESCRIPCIONEVENTO,
									"descripcionEvento",
									RelacionSNS.DIRECCION_ENTRADA);
			if(de.getDeporte() != null){
				relacionEventoNAria.setObjetoRelacion(de.getDeporte());
				super.crearRelacion(relacionEventoNAria, 
						new ProductorFactoryDeporte());
			}
		}
	}
	
	public void crearGenero() throws ProductorFactoryExcepcion{
		if(this.objectSNSDeportivo != null){
			DeporteEvento de = (DeporteEvento)this.objectSNSDeportivo;
			RelacionSNS relacionEventoNAria = 
					new RelacionSNS(Relaciones.DESCRIPCIONEVENTO,
									"descripcionEvento",
									RelacionSNS.DIRECCION_ENTRADA);
			if(de.getGenero() != null){
				relacionEventoNAria.setObjetoRelacion(de.getGenero());
				super.crearRelacion(relacionEventoNAria, 
									new ProductorFactoryGenerales());
			}
		}
	}
	
	public void crearUbicacion() throws ProductorFactoryExcepcion{
		if(this.objectSNSDeportivo != null){
			DeporteEvento de = (DeporteEvento)this.objectSNSDeportivo;
			RelacionSNS relacionEventoNAria = 
					new RelacionSNS(Relaciones.DESCRIPCIONEVENTO,
									"descripcionEvento",
									RelacionSNS.DIRECCION_ENTRADA);
			if(de.getUbicacion() != null){
				relacionEventoNAria.setObjetoRelacion(de.getUbicacion());
				super.crearRelacion(relacionEventoNAria, 
						new ProductorFactoryUbicacion());
			}
		}
	}
	
	public String obtenerEventos() throws 
		BDException,ExcepcionJsonDeserializacion,ProductorFactoryExcepcion{
		String eventos = "[]";
		if(this.objectSNSDeportivo != null){
			DeporteEvento de = (DeporteEvento)this.objectSNSDeportivo;
			RelacionSNS relacionIntermedia = null;
			ArrayList<String> patrones = new ArrayList<String>();
			StringBuilder query = new StringBuilder("MATCH ");
			if(de.getParticipantes() != null && 
			   de.getParticipantes().size() > 0){
				relacionIntermedia = 
						new RelacionSNS(Relaciones.PARTICIPANTEEVENTO,
								        "relacionParticipanteEvento",
								        RelacionSNS.DIRECCION_ENTRADA,
								        de.getParticipantes());
				patrones.addAll(relacionIntermedia.stringJsonPatrones(
							new DAOUsuario()));
			}
			if(de.getInvitaciones() != null && 
			   de.getInvitaciones().size() > 0){
				relacionIntermedia = 
						new RelacionSNS(Relaciones.INVITADOAPARTICIPAR,
								        "relacionInvitadoEvento",
								        RelacionSNS.DIRECCION_ENTRADA,
								        de.getInvitaciones());
				patrones.addAll(relacionIntermedia.stringJsonPatrones(
						new DAOUsuario()));
			}
			if(de.getSolicitudes() != null &&
			   de.getSolicitudes().size() > 0){
				relacionIntermedia = 
						new RelacionSNS(Relaciones.SOLICITAPARTICIPAR,
								        "relacionSolicitudesEvento",
								        RelacionSNS.DIRECCION_ENTRADA,
								        de.getSolicitudes());
				patrones.addAll(relacionIntermedia.stringJsonPatrones(
						new DAOUsuario()));
			}
			if(de.getDeporte() != null){
				relacionIntermedia = 
						new RelacionSNS(Relaciones.DESCRIPCIONEVENTO,
								        "relacionDeporteEvento",
								        RelacionSNS.DIRECCION_ENTRADA,
								        de.getDeporte());
				patrones.addAll(relacionIntermedia.stringJsonPatrones(
						new DAODeporte()));
			}
			if(de.getGenero() != null){
				relacionIntermedia = 
						new RelacionSNS(Relaciones.DESCRIPCIONEVENTO,
								        "relacionGeneroEvento",
								        RelacionSNS.DIRECCION_ENTRADA,
								        de.getGenero());
				patrones.addAll(relacionIntermedia.stringJsonPatrones(
						new GeneroDAO()));
				relacionIntermedia.stringJson();
			}
			for(String patron:patrones){
				query.append(this.producirNodoMatch());
				query.append(patron);
				query.append(",");
			}
			if(de.getEvento() != null && patrones.size() > 0){
				relacionIntermedia = 
						new RelacionSNS(Relaciones.DESCRIPCIONEVENTO,
								        "relacionEvento",
								        RelacionSNS.DIRECCION_ENTRADA,
								        de.getEvento());
				EventosFactory factory = (EventosFactory)
						new ProductorFactoryEvento().producirFacObjetoSNS(
						de.getEvento().getClass().getSimpleName());
				DAOEvento deve = factory.crearDAOEvento();
				query.append(this.producirNodoMatch());
				query.append(relacionIntermedia.stringJson());
				query.append(deve.producirNodoMatchNoJson());
				query.append(" RETURN ");
				query.append(deve.getIdentificadorQueries());
				//EJECUTA QUERY
				try{
					Object[] resultado = BDUtils.ejecutarQueryREST(query.toString());
					if(resultado != null && resultado.length > 0){
						Evento[] eventosArray = null;
						if(BDUtils.obtenerRestRegistro(resultado[0]) != null){
							eventosArray = new Evento[resultado.length];
							for(int i = 0; i < resultado.length; i++){
								JsonObject datos = 
										(JsonObject)BDUtils.obtenerRestRegistro(resultado[i]).
										getPropiedades().get("data")[0];
								eventosArray[i] = factory.crearEvento();
								try {
									eventosArray[i].deserializarJson(datos);
								} catch (ExcepcionJsonDeserializacion e) {
									e.printStackTrace();
									throw e;
								}
							}
						}
						eventos = JsonUtils.arrayObjectSNSToStringJson(eventosArray);
					}
				}catch(BDException e){
					e.printStackTrace();
					throw e;
				}
			}
		}
		return eventos;
	}
	
	public String obtenerUbicacionEvento() 
			throws BDException, ProductorFactoryExcepcion, ExcepcionJsonDeserializacion{
		String retorno = "{}";
		if(this.objectSNSDeportivo != null){
			StringBuilder query = new StringBuilder("MATCH ");
			DeporteEvento de = 
					(DeporteEvento)this.objectSNSDeportivo;
			RelacionSNS relacion = new RelacionSNS(
					Relaciones.DESCRIPCIONEVENTO,
					"ubicacionesRelacion",
					RelacionSNS.DIRECCION_ENTRADA,
					de.getUbicacion());
			FactoryObjectSNSDeportivo factory =
					new ProductorFactoryUbicacion().
					producirFacObjetoSNS(de.getUbicacion().
							getClass().getSimpleName());
			ObjectSNSDeportivo objeto = factory.getObjetoSNS();
			query.append(
					this.producirPatronRelacionIndividual(
					relacion, factory, false));
			query.append("RETURN ");
			query.append(factory.getObjetoSNSDAO().getIdentificadorQueries());
			Object[] resultado = BDUtils.ejecutarQueryREST(query.toString());
			if(resultado != null && !resultado[0].equals("")){
				for(int i = 0; i < resultado.length; i++){
					Object[] data =
							BDUtils.obtenerRestRegistro(resultado[i]).getPropiedades().get("data");
					if(data != null && !data[0].equals("")){
						objeto.deserializarJson((JsonObject)data[0]);
					}
				}
				retorno = objeto.stringJson();
			}
		}
		return retorno;
	}

}
