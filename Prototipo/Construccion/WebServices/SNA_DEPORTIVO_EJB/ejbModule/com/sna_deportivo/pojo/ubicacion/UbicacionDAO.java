package com.sna_deportivo.pojo.ubicacion;

import java.util.ArrayList;

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
import com.sna_deportivo.utils.json.excepciones.ExcepcionJsonDeserializacion;

public class UbicacionDAO extends ObjectSNSDeportivoDAO{

	@Override
	public void encontrarObjetoManejado() throws BDException, ProductorFactoryExcepcion, ProductorFactoryExcepcion {
		throw new UnsupportedOperationException();
	}

	@Override
	protected void setUpDAOGeneral() {
		this.identificadorQueries = "ubicacion";
		this.tipoObjetoSNS = Entidades.UBICACION;
		this.factoryOSNS = new FactoryUbicacion();
	}

	@Override
	public ObjectSNSDeportivo crearObjetoSNS() throws ProductorFactoryExcepcion {
		throw new UnsupportedOperationException();
	}
	
	public Ubicacion[] getUbicaciones() 
			throws BDException,ProductorFactoryExcepcion, ExcepcionJsonDeserializacion{
		Ubicacion[] ubicaciones = null;
		if(this.objectSNSDeportivo != null){
				StringBuilder query = null;
				//Ubicacion es nodo n-ario, solo tiene una asociacion
				//Con ciudad, pais y lugar de practica
				Ubicacion ubicacion = 
						(Ubicacion)this.objectSNSDeportivo;
				ArrayList<String> patronesRelaciones = 
						new ArrayList<String>();
				ArrayList<String> columnasReturn = 
						new ArrayList<String>();
				ArrayList<FactoryObjectSNSDeportivo> objetosRetornados =
						new ArrayList<FactoryObjectSNSDeportivo>();
				ArrayList<JsonObject> columnasFetch = 
						new ArrayList<JsonObject>();
				ProductorFactoryUbicacion puf =
						new ProductorFactoryUbicacion();
				RelacionSNS relacion = null;
				FactoryObjectSNSDeportivo factory = null;
				columnasReturn.add(0,this.identificadorQueries);
				//Ciudad
				if(ubicacion.getCiudad() != null){
					Ciudad ciudad = ubicacion.getCiudad();
					ciudad.prenderSwitchConvierteAtribDB();
					factory = puf.producirFacObjetoSNS(
							ciudad.getClass().
							getSimpleName());
					relacion = new RelacionSNS(Relaciones.COMPLETAUBICACION,
											   "completaUbicaCiudad",
											   RelacionSNS.DIRECCION_ENTRADA,
											   ciudad);
					patronesRelaciones.add(
							this.producirPatronRelacionIndividual(
							    relacion, factory, true));
					columnasReturn.add(1,factory.getObjetoSNSDAO().
							getIdentificadorQueries());
					objetosRetornados.add(0,factory);
				}
				//Pais
				if(ubicacion.getPais() != null){
					Pais pais = ubicacion.getPais();
					pais.prenderSwitchConvierteAtribDB();
					factory = puf.producirFacObjetoSNS(
							pais.getClass().
							getSimpleName());
					relacion = new RelacionSNS(Relaciones.COMPLETAUBICACION,
											   "completaUbicaPais",
											   RelacionSNS.DIRECCION_ENTRADA,
											   pais);
					patronesRelaciones.add(
							this.producirPatronRelacionIndividual(
							    relacion, factory, true));
					columnasReturn.add(2,factory.getObjetoSNSDAO().
							getIdentificadorQueries());
					objetosRetornados.add(1,factory);
				}
				//Lugar Practica
				if(ubicacion.getLugar() != null){
					LugarPractica lugar = ubicacion.getLugar();
					lugar.prenderSwitchConvierteAtribDB();
					factory = puf.producirFacObjetoSNS(
							lugar.getClass().
							getSimpleName());
					relacion = new RelacionSNS(Relaciones.COMPLETAUBICACION,
											   "completaUbicaLugar",
											   RelacionSNS.DIRECCION_ENTRADA,
											   lugar);
					patronesRelaciones.add(
							this.producirPatronRelacionIndividual(
							    relacion, factory, true));
					columnasReturn.add(3,factory.getObjetoSNSDAO().
							getIdentificadorQueries());
					objetosRetornados.add(2,factory);
				}
				//Formando query
				if(patronesRelaciones.size() > 0 && 
				   columnasReturn.size() - 1 == patronesRelaciones.size()){
					query = this.devuelveWith();
					query.append(" ");
					query.append(this.devuelveMatch(patronesRelaciones));
					query.append(" ");
					query.append(this.devuelveRetorno(columnasReturn));
				}else{
					query = this.devuelveMatchObj(true);
					query.append(" RETURN ");
					query.append(this.identificadorQueries);
				}
				//Por ahora asumo que fue el mejor caso. Vienen ciudad, lugar y pais
				Object[] resultado = BDUtils.ejecutarQueryREST(query.toString());
				if(resultado != null && !resultado[0].equals("")){
					ubicaciones = new Ubicacion[resultado.length];
					for(int i = 0; i < resultado.length; i++){
						Object[] raw = BDUtils.obtenerRestRegistroCompleto(resultado[i]);
						columnasReturn.clear();
						if(raw != null && !raw[0].equals("")){
							for(int j = 0; j < raw.length; j++){
								Object[] data = ((JsonObject)raw[j]).getPropiedades().
										get("data");
								if(data != null && !data[0].equals("")){
									columnasFetch.add((JsonObject)data[0]);
								}else{
									columnasFetch.add(null);
								}
							}
							//Colocando fetch hacia ubicacion
							JsonObject json = null;
							if(columnasFetch.get(0) != null){
								ubicaciones[i] = (Ubicacion)this.factoryOSNS.getObjetoSNS();
								ubicaciones[i].deserializarJson(columnasFetch.get(0));
								if(columnasFetch.get(1) != null){
									json = producirLatitudLongitud(columnasFetch.get(1));
									Ciudad ciudad = (Ciudad)
											objetosRetornados.get(0).getObjetoSNS();
									ciudad.deserializarJson(json);
									ubicaciones[i].setCiudad(ciudad);
								}
								if(columnasFetch.get(2) != null){
									json = producirLatitudLongitud(columnasFetch.get(2));
									Pais pais = (Pais)
											objetosRetornados.get(1).getObjetoSNS();
									pais.deserializarJson(json);
									ubicaciones[i].setPais(pais);
								}
								if(columnasFetch.get(3) != null){
									LugarPractica lugar = (LugarPractica)
											objetosRetornados.get(2).getObjetoSNS();
									json = producirLatitudLongitud(columnasFetch.get(3));
									lugar.deserializarJson(json);
									ubicaciones[i].setLugar(lugar);
								}
							}
						}
					}
				}
		}else{
			ubicaciones = new Ubicacion[0];
		}
		return ubicaciones;
	}
	
	private JsonObject producirLatitudLongitud(JsonObject json){
		String coordenada;
		String latitud,longitud;
		if(json.getPropiedades().
				get("coordenada") != null){
			coordenada = json.getPropiedades().
					get("coordenada")[0].toString();
			latitud = coordenada.substring(0, coordenada.indexOf(',') - 1);
			longitud = coordenada.substring(coordenada.indexOf(',') + 1);
			json.getPropiedades().put("latitud", new Object[]{latitud});
			json.getPropiedades().put("longitud", new Object[]{longitud});
			json.getPropiedades().remove("coordenada");
		}
		return json;
	}

}
