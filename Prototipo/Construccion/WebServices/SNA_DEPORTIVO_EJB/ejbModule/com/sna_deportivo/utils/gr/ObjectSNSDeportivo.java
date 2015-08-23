package com.sna_deportivo.utils.gr;

import com.sna_deportivo.utils.json.JsonSerializable;

public interface ObjectSNSDeportivo extends JsonSerializable{

	public String stringJson();
	
	public void setNullObject();
	
	public void retornoToString(String aRetornar);
	
}
