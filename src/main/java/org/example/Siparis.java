package org.example;

import org.example.garson.GarsonThread;

public class Siparis {
	private static int sonSiparisNo = 0; // Statik bir değişken kullanarak her yeni siparişte artan bir numara sağlıyoruz.

	private int siparisNo;
	private int siparis;
	private int musteriNumarasi;
	private Masa masa;
	private GarsonThread garsonThread;
	
	public Siparis(int siparis, int musteriNumarasi, Masa masa, GarsonThread garsonThread) {
		this.siparisNo = ++sonSiparisNo;
		this.siparis = siparis;
		this.musteriNumarasi = musteriNumarasi;
		this.masa = masa;
		this.garsonThread = garsonThread;
	}

	public int getSiparisNo() {
		return siparisNo;
	}

	public int getSiparis() {
		return siparis;
	}
	
	public int getMusteriNumarasi() {
		return musteriNumarasi;
	}
	
	public Integer getSiparisTutari() {
		if ( siparis == 0 ) {
			return 10;
		} else if ( siparis == 1 ) {
			return 20;
		}
		return 30;
	}
	
	public GarsonThread getGarsonThread() {
		return garsonThread;
	}
	
	public Masa getMasa() {
		return masa;
	}
}
