package com.sna_deportivo.pojo.ubicacion;

import com.sna_deportivo.utils.gr.FactoryObjectSNSDeportivo;
import com.sna_deportivo.utils.gr.ObjectSNSDeportivo;
import com.sna_deportivo.utils.gr.ObjectSNSDeportivoDAO;

public class FactoryPais implements FactoryObjectSNSDeportivo{

	@Override
	public ObjectSNSDeportivo getObjetoSNS() {
		return new Pais();
	}

	@Override
	public ObjectSNSDeportivoDAO getObjetoSNSDAO() {
		return new PaisDAO();
	}

}
