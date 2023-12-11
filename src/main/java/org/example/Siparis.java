package org.example;

import org.example.garson.GarsonThread;

public class Siparis {
	private int siparis;
	private int musteriNumarasi;
	private Masa masa;
	private GarsonThread garsonThread;
	
	public Siparis(int siparis, int musteriNumarasi, Masa masa, GarsonThread garsonThread) {
		this.siparis = siparis;
		this.musteriNumarasi = musteriNumarasi;
		this.masa = masa;
		this.garsonThread = garsonThread;
	}
	
	public int getSiparis() {
		return siparis;
	}
	
	public int getMusteriNumarasi() {
		return musteriNumarasi;
	}
	
	public String getOrderText() {
		if ( siparis == 0 ) {
			return "menemen";
		} else if ( siparis == 1 ) {
			return "çorba";
		}
		return "makarna";
	}
	
	public GarsonThread getGarsonThread() {
		return garsonThread;
	}
	
	public Masa getMasa() {
		return masa;
	}
}
