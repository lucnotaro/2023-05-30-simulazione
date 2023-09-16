package it.polito.tdp.gosales.model;

public class Archi implements Comparable <Archi> {
	private String name1;
	private String name2;
	private Integer peso;
	public Archi(String name1, String name2, Integer peso) {
		super();
		this.name1 = name1;
		this.name2 = name2;
		this.peso = peso;
	}
	@Override
	public String toString() {
		return name1 + ", " + name2 + ", peso=" + peso;
	}
	public String getName1() {
		return name1;
	}
	public void setName1(String name1) {
		this.name1 = name1;
	}
	public String getName2() {
		return name2;
	}
	public void setName2(String name2) {
		this.name2 = name2;
	}
	public Integer getPeso() {
		return peso;
	}
	public void setPeso(Integer peso) {
		this.peso = peso;
	}
	@Override
	public int compareTo(Archi o) {
		return this.peso-o.getPeso();
	}
	
}
