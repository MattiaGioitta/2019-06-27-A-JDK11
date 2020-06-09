package it.polito.tdp.crimes.model;

public class Adiacenza {
	
	private String of1;
	private String of2;
	private int peso;
	/**
	 * @param of1
	 * @param of2
	 * @param peso
	 */
	public Adiacenza(String of1, String of2, int peso) {
		super();
		this.of1 = of1;
		this.of2 = of2;
		this.peso = peso;
	}
	/**
	 * @return the of1
	 */
	public String getOf1() {
		return of1;
	}
	/**
	 * @param of1 the of1 to set
	 */
	public void setOf1(String of1) {
		this.of1 = of1;
	}
	/**
	 * @return the of2
	 */
	public String getOf2() {
		return of2;
	}
	/**
	 * @param of2 the of2 to set
	 */
	public void setOf2(String of2) {
		this.of2 = of2;
	}
	/**
	 * @return the peso
	 */
	public int getPeso() {
		return peso;
	}
	/**
	 * @param peso the peso to set
	 */
	public void setPeso(int peso) {
		this.peso = peso;
	}
	@Override
	public String toString() {
		return  of1 +" "+ of2 + " peso=" + peso;
	}
	
	

}
