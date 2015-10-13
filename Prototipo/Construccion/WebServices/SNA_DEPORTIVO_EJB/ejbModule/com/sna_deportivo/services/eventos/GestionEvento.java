package com.sna_deportivo.services.eventos;

import com.sna_deportivo.pojo.evento.DAOEvento;
import com.sna_deportivo.pojo.evento.Evento;
import com.sna_deportivo.pojo.evento.ProductorFactoryEvento;
import com.sna_deportivo.utils.bd.excepciones.BDException;
import com.sna_deportivo.utils.gr.ObjectSNSDeportivo;
import com.sna_deportivo.utils.gr.excepciones.ProductorFactoryExcepcion;
import com.sna_deportivo.utils.json.JsonUtils;
import com.sna_deportivo.utils.json.excepciones.ExcepcionJsonDeserializacion;

public class GestionEvento {
	
	public GestionEvento(){}
	
	public String consultarEventos(String tipo, Evento evento)
									 throws BDException, 
									 ProductorFactoryExcepcion,ExcepcionJsonDeserializacion{
		DAOEvento accesoEvento;
		try {
			accesoEvento = new ProductorFactoryEvento().
							   getEventosFactory(tipo).
							   crearDAOEvento();
			accesoEvento.setEvento(evento);
			return JsonUtils.arrayObjectSNSToStringJson(
					(ObjectSNSDeportivo[])accesoEvento.getEventosDB());
		} catch (BDException e) {
			throw e;
		} catch (ProductorFactoryExcepcion e){
			throw e;
		}
		
	}
	
	public Evento crearEvento(String tipo,
							  Evento evento)
							  throws BDException, ProductorFactoryExcepcion{
		DAOEvento accesoEvento = null;
		try {
			accesoEvento = new ProductorFactoryEvento().
										 getEventosFactory(tipo).
										 crearDAOEvento();
			accesoEvento.setEvento(evento);
			return accesoEvento.crearEventoDB();
		} catch (BDException e) {
			throw e;
		} catch (ProductorFactoryExcepcion e){
			throw e;
		}
		
	}
	
	public void actualizarEvento(String tipo,
								 Evento evento)
								 throws BDException, ProductorFactoryExcepcion,ExcepcionJsonDeserializacion{
		DAOEvento accesoEvento = null;
		try {
			accesoEvento = new ProductorFactoryEvento().
										 getEventosFactory(tipo).
										 crearDAOEvento();
			accesoEvento.setEvento(evento);
			accesoEvento.updateEventoDB();
		} catch (BDException e) {
			throw e;
		} catch (ProductorFactoryExcepcion e){
			throw e;
		}
		
	}
	
	public void desactivarEvento(String tipo,
							     Evento evento) 
						         throws BDException, ProductorFactoryExcepcion,ExcepcionJsonDeserializacion{
		DAOEvento accesoEvento = null;
		try {
			accesoEvento = new ProductorFactoryEvento().
										 getEventosFactory(tipo).
					 					 crearDAOEvento();
			accesoEvento.setEvento(evento);
			accesoEvento.deleteEventoDB();
		} catch (BDException e) {
			throw e;
		} catch (ProductorFactoryExcepcion e){
			throw e;
		}
		
	}
	
}
