package com.sna_deportivo.pojo.evento;

import com.sna_deportivo.pojo.deportes.Deporte;
import com.sna_deportivo.pojo.deportes.ProductorFactoryDeporte;
import com.sna_deportivo.pojo.entidadesEstaticas.Genero;
import com.sna_deportivo.pojo.entidadesEstaticas.ProductorFactoryGenerales;
import com.sna_deportivo.pojo.usuarios.ProductorFactoryUsuario;
import com.sna_deportivo.pojo.usuarios.Usuario;
import com.sna_deportivo.utils.bd.BDUtils;
import com.sna_deportivo.utils.bd.Entidades;
import com.sna_deportivo.utils.bd.RelacionSNS;
import com.sna_deportivo.utils.bd.Relaciones;
import com.sna_deportivo.utils.bd.excepciones.BDException;
import com.sna_deportivo.utils.gr.ObjectSNSDeportivo;
import com.sna_deportivo.utils.gr.ObjectSNSDeportivoDAO;
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
			throws BDException{
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
	public ObjectSNSDeportivo crearObjetoSNS() {
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
		if(de.getDeporte() != null){
			relacionEventoNAria.setObjetoRelacion(de.getDeporte());
			super.crearRelacion(relacionEventoNAria, 
					new ProductorFactoryDeporte());
		}
		
		if(de.getUbicacion() != null){
			relacionEventoNAria.setObjetoRelacion(de.getUbicacion());
			super.crearRelacion(relacionEventoNAria, 
					null);
		}
		if(de.getGenero() != null){
			relacionEventoNAria.setObjetoRelacion(de.getGenero());
			super.crearRelacion(relacionEventoNAria, 
								new ProductorFactoryGenerales());
		}
		
		return this.objectSNSDeportivo;
	}
	
	public String crearInvitaciones() throws BDException{
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
				if(!super.crearRelacion(relacionACrear, 
						new ProductorFactoryUsuario())){
					throw new BDException();
				}
			}
		}
		return resultado;
	}
	
	public String crearSolicitudes() throws BDException{
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
				if(!super.crearRelacion(relacionACrear, 
						new ProductorFactoryUsuario())){
					throw new BDException();
				}
			}
		}
		return resultado;
	}
	
	public String crearParticipantes() throws BDException{
		String resultado = null;
		if(this.objectSNSDeportivo != null){
			DeporteEvento de = (DeporteEvento)this.objectSNSDeportivo;
			if(de.getParticipantes() != null &&
			   de.getParticipantes().size() > 0){
				RelacionSNS relacionACrear =
						new RelacionSNS(Relaciones.PARTICIPANTEEVENTO,
								"crearParticipante",
								RelacionSNS.DIRECCION_ENTRADA,
								de.getParticipantes());
				if(!super.crearRelacion(relacionACrear, 
						new ProductorFactoryUsuario())){
					throw new BDException();
				}
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
				if(resultado != null && resultado.length > 0){
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
					System.out.println("tengo resultados");
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
		
		System.out.println("retorno total "+retorno);
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
			throws BDException,ExcepcionJsonDeserializacion{
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
			throws BDException,ExcepcionJsonDeserializacion{
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
			throws BDException,ExcepcionJsonDeserializacion{
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
	
	public boolean eliminarDeporte() throws BDException{
		if(this.objectSNSDeportivo != null){
			RelacionSNS aEliminar = new RelacionSNS(Relaciones.DESCRIPCIONEVENTO,
					"deporteEvento",
					RelacionSNS.DIRECCION_ENTRADA,
					((DeporteEvento)this.objectSNSDeportivo).
					getDeporte());
			return this.eliminarRelacion(aEliminar,
					new ProductorFactoryDeporte());
		}
		return true;
	}
	
	public boolean eliminarGenero() throws BDException{
		if(this.objectSNSDeportivo != null){
			RelacionSNS aEliminar = new RelacionSNS(Relaciones.DESCRIPCIONEVENTO,
					"deporteEvento",
					RelacionSNS.DIRECCION_ENTRADA,
					((DeporteEvento)this.objectSNSDeportivo).
					getGenero());
			return this.eliminarRelacion(aEliminar,
					new ProductorFactoryGenerales());
		}
		return true;
	}
	
	public boolean eliminarUbicacion() throws BDException{
		if(this.objectSNSDeportivo != null){
			RelacionSNS aEliminar = new RelacionSNS(Relaciones.DESCRIPCIONEVENTO,
					"deporteEvento",
					RelacionSNS.DIRECCION_ENTRADA,
					((DeporteEvento)this.objectSNSDeportivo).
					getUbicacion());
			/*return this.eliminarRelacion(aEliminar,
					new ProductorFactoryUbicacion());*/
		}
		return true;
	}
	
	public boolean actualizarDeporteEvento() throws BDException{
		//Ampliar a todo el evento, con invitaciones, participantes, etc.
		if(this.objectSNSDeportivo != null){
			((DeporteEvento)this.objectSNSDeportivo).
				setEvento(null);
			if(this.eliminarDeporte() && 
			   this.eliminarGenero() &&
			   this.eliminarUbicacion()){
				this.crearObjetoSNS();
			}else{
				return false;
			}
		}
		return true;
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
			throws BDException,ExcepcionJsonDeserializacion{
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
		} catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return retorno;
	}

}
