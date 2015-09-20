package com.sna_deportivo.pojo.entidadesEstaticas;

import com.sna_deportivo.utils.gr.FactoryObjectSNSDeportivo;
import com.sna_deportivo.utils.gr.ObjectSNSDeportivo;
import com.sna_deportivo.utils.gr.ObjectSNSDeportivoDAO;

public class FactoryGenero implements FactoryObjectSNSDeportivo{

	@Override
	public ObjectSNSDeportivo getObjetoSNS() {
		return new Genero();
	}

	@Override
	public ObjectSNSDeportivoDAO getObjetoSNSDAO() {
		return new GeneroDAO();
	}

}
