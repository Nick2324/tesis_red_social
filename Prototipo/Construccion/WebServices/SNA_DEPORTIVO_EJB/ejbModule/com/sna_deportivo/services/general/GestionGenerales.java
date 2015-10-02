package com.sna_deportivo.services.general;

import com.sna_deportivo.pojo.entidadesEstaticas.ProductorFactoryGenerales;
import com.sna_deportivo.utils.json.JsonUtils;

public class GestionGenerales {

	public String getEntidadGeneral(String tipo){
		return JsonUtils.arrayObjectSNSToStringJson(
				new ProductorFactoryGenerales().
					getFactorySegunTipo(tipo).
					getObjetoSNSDAO().getObjetoSNSDeportivoDB());
	}
	
}
