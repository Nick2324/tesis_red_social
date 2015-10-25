package com.sna_deportivo.services;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;

import com.sna_deportivo.pojo.deportes.DeportePracticadoUbicacion;
import com.sna_deportivo.pojo.evento.DAOEvento;
import com.sna_deportivo.pojo.evento.DEPracticaDeportiva;
import com.sna_deportivo.pojo.evento.Evento;
import com.sna_deportivo.pojo.evento.PracticaDeportiva;
import com.sna_deportivo.pojo.evento.TiposEventos;
import com.sna_deportivo.pojo.ubicacion.Ciudad;
import com.sna_deportivo.pojo.ubicacion.EventoLocalizado;
import com.sna_deportivo.pojo.ubicacion.LugarPractica;
import com.sna_deportivo.pojo.ubicacion.Pais;
import com.sna_deportivo.pojo.ubicacion.Ubicacion;
import com.sna_deportivo.utils.gr.ResponseGenerico;
import com.sna_deportivo.utils.gr.excepciones.ProductorFactoryExcepcion;
import com.sna_deportivo.utils.json.JsonUtils;
import com.sna_deportivo.utils.json.excepciones.ExcepcionJsonDeserializacion;
import com.sna_deportivo.services.ubicaciones.GestionUbicacion;
import com.sna_deportivo.utils.bd.excepciones.BDException;

@Path("GestionUbicacionService/")
public class GestionUbicacionService {

	private GestionUbicacion servicio;
	private GestionEventoService servicioEvento;

	public GestionUbicacionService() {
		servicio = new GestionUbicacion();
		servicioEvento = new GestionEventoService();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obtenerPaises")
	public Pais[] obtenerPaises(){
		Pais[] paises;
		try{
			paises = servicio.obtenerPaises();
		}catch (BDException e){
			paises = null;
		}
		return paises;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obtenerCiudades")
	public Ciudad[] obtenerCiudades(@QueryParam("idPais") int idPais){
		Ciudad[] ciudades;
		try{
			Pais pais = new Pais();
			pais.setId(idPais);
			ciudades = servicio.obtenerCiudadesPais(pais);
		}catch (BDException e){
			ciudades = null;
		}
		return ciudades;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obtenerLugaresPracticaCiudad")
	public LugarPractica[] obtenerLugaresPractica(@QueryParam("idCiudad") int idCiudad){
		LugarPractica[] lugares;
		try{
			Ciudad ciudad = new Ciudad();
			ciudad.setId(idCiudad);
			lugares = servicio.obtenerLugaresPractica(ciudad);
		}catch (BDException e){
			lugares = null;
		}
		return lugares;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obtenerLugaresPractica")
	public LugarPractica[] obtenerLugaresPractica(){
		LugarPractica[] lugares;
		try{
			lugares = servicio.obtenerLugaresPractica();
			for(LugarPractica lugar : lugares){
				lugar.setDeportesPracticados(servicio.obtenerDeportesPracticadosLugar(lugar));
			}
		}catch (BDException e){
			lugares = null;
		}
		return lugares;
	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obtenerEventosLocalizados")
	public EventoLocalizado[] obtenerEventosLocalizados(){
		EventoLocalizado[] retorno = null;
		Evento[] eventos;
		DAOEvento de = new DEPracticaDeportiva();
		de.setEvento(new Evento());
		try {
			eventos = de.getEventosDB();
			if(eventos != null){
				retorno = new EventoLocalizado[eventos.length];
				for(int i=0;i<retorno.length;i++){
					retorno[i] = new EventoLocalizado();
					retorno[i].setEvento(eventos[i]);
					retorno[i].setUbicacion(servicio.obtenerUbicacionEvento(eventos[i]));
				}
			}
		} catch (BDException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProductorFactoryExcepcion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExcepcionJsonDeserializacion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return retorno;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("crearLugarPractica")
	public ResponseGenerico crearLugarPractica(Ubicacion ubicacion){
		ResponseGenerico response = new ResponseGenerico();
		
		try{
			Pais pais = ubicacion.getPais();
			Ciudad ciudad = ubicacion.getCiudad();
			LugarPractica lugar = ubicacion.getLugar();
			response = servicio.crearLugarPractica(pais, ciudad, lugar);
		
		}catch (BDException e){
			response.setCaracterAceptacion("M");
			response.setMensajeRespuesta("Ha ocurrido un error con la base de datos.");
		}
		
		return response;
		
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("asignarDeportesPracticadosLugar")
	public ResponseGenerico asignarDeportesPracticadosLugar(DeportePracticadoUbicacion[] deportes){
		
		ResponseGenerico response = new ResponseGenerico();
		
		try{
			for(DeportePracticadoUbicacion deporte : deportes){
				servicio.asociarDeporteLugar(deporte);
			}
		}catch (BDException e){
			response.setCaracterAceptacion("M");
			response.setMensajeRespuesta("Ha ocurrido un error con la base de datos.");
		}
		return response;
	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("ubicaciones")
	public Ubicacion[] obtenerUbicaciones(Ubicacion body){
		try{
			return this.servicio.obtenerUbicaciones(body);
		}catch(WebApplicationException e){
			e.printStackTrace();
			throw e;
		}catch(Exception e){
			e.printStackTrace();
			throw new WebApplicationException(Response.Status.
					INTERNAL_SERVER_ERROR.getStatusCode());
		}
	}
}
