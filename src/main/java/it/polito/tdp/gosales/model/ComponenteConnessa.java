package it.polito.tdp.gosales.model;

public class ComponenteConnessa {

	private Integer componente;
	private Integer peso;
	public ComponenteConnessa(Integer componente, Integer peso) {
		super();
		this.componente = componente;
		this.peso = peso;
	}
	@Override
	public String toString() {
		return "Dimensione componente connessa= " + componente + ", peso totale= " + peso;
	}
	
}
