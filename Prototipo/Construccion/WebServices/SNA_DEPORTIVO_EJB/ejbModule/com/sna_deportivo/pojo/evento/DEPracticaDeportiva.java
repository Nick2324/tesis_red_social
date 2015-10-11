package com.sna_deportivo.pojo.evento;

public class DEPracticaDeportiva extends DAOEvento{

	public DEPracticaDeportiva(){
		super();
		this.eventoManejado = TiposEventos.PRACTICADEPORTIVALIBRE.getServicio();
	}
	
	public DEPracticaDeportiva(PracticaDeportiva pd){
		super(pd);
		this.eventoManejado = TiposEventos.PRACTICADEPORTIVALIBRE.getServicio();
	}

	@Override
	protected void setUpDAOGeneral() {
		super.setUpDAOGeneral();
		super.factoryOSNS = new PracticaDeportivaFactory();
		super.identificadorQueries = "practicaDeportiva";
	}

	@Override
	public void encontrarObjetoManejado() {
		// TODO Auto-generated method stub
		
	}

}