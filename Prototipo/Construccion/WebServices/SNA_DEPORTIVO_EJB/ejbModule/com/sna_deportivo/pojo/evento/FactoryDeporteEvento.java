package com.sna_deportivo.pojo.evento;

import com.sna_deportivo.utils.gr.FactoryObjectSNSDeportivo;
import com.sna_deportivo.utils.gr.ObjectSNSDeportivo;
import com.sna_deportivo.utils.gr.ObjectSNSDeportivoDAO;

public class FactoryDeporteEvento implements FactoryObjectSNSDeportivo{

	@Override
	public ObjectSNSDeportivo getObjetoSNS() {
		return new DeporteEvento();
	}

	@Override
	public ObjectSNSDeportivoDAO getObjetoSNSDAO() {
		return new DeporteEventoDAO();
	}

}
