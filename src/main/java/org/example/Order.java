package org.example;

import org.example.garson.GarsonThread;

public class Order {
	private int order;
	private int customerNumber;
	private Masa masa;
	private GarsonThread garson;
	
	public Order(int o, int cn, Masa t, GarsonThread w ) {
		order = o;
		customerNumber = cn;
		masa = t;
		garson = w;
	}
	
	public int getOrder() {
		return order;
	}
	
	public int getCustomerNumber() {
		return customerNumber;
	}
	
	public String getOrderText() {
		if ( order == 0 ) {
			return "menemen";
		} else if ( order == 1 ) {
			return "çorba";
		}
		return "makarna";
	}
	
	public GarsonThread getGarson() {
		return garson;
	}
	
	public Masa getMasa() {
		return masa;
	}
}
