package com.sna_deportivo.utils.gr;

import com.sna_deportivo.utils.gr.excepciones.ProductorFactoryExcepcion;

public interface ProductorSNSDeportivo {

	public FactoryObjectSNSDeportivo producirFacObjetoSNS(String objetoAManejar) throws ProductorFactoryExcepcion;
	
	/*protected ArrayList<String> objetosSNSManejado;
	protected ProductorChainSNSDeportivo pcsnsd;
	
	protected ProductorChainSNSDeportivo(String objetoSNSManejado){
		this.objetosSNSManejado = new ArrayList<String>();
		this.objetosSNSManejado.add(objetoSNSManejado);
	}
	
	protected ProductorChainSNSDeportivo(ArrayList<String> objetosSNSManejados){
		this.objetosSNSManejado = objetosSNSManejados;
	}
	
	protected abstract FactoryObjectSNSDeportivo manejarProduccion(String objetoAManejar);
	
	public FactoryObjectSNSDeportivo producirFacObjetoSNS(String objetoAManejar){
		if(this.objetosSNSManejado.contains(objetoAManejar)){
			return this.manejarProduccion(objetoAManejar);
		}else{
			return this.pcsnsd.producirFacObjetoSNS(objetoAManejar);
		}
	}
	
	public void setProductorChain(ProductorChainSNSDeportivo pcsnsd){
		this.pcsnsd = pcsnsd;
	}*/
	
}
