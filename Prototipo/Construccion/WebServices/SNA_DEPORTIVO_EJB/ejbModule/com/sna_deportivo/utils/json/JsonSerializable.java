
package com.sna_deportivo.utils.json;

import com.sna_deportivo.utils.json.excepciones.ExcepcionJsonDeserializacion;

/**
 * @author nicolas
 *
 */

public interface JsonSerializable {
	
	public abstract JsonObject serializarJson();
	
	public abstract void deserializarJson(JsonObject json) throws ExcepcionJsonDeserializacion;
	
}
