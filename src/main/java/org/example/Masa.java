package org.example;

import org.example.garson.GarsonThread;
import org.example.musteri.MusteriThread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Masa {

	private int tableNumber;
	private MusteriThread musteriThread;
	private GarsonThread garsonThread;
	private Lock lock = new ReentrantLock();
	private Condition readyCondition = lock.newCondition();

	public Masa(int tableNumber) {
		this.tableNumber = tableNumber;
	}
	
	public int getMasaNumarasi() {
		return this.tableNumber;
	}

	public MusteriThread getMusteriThread() {return musteriThread;}

	public GarsonThread getGarsonThread() {return garsonThread;}
	
	public Lock getLock() {
		return lock;
	}
	
	public Condition getReadyCondition() {
		return readyCondition;
	}

	public void masayaOturtveIlgilen(MusteriThread musteriThread, GarsonThread garsonThread) {
		this.musteriThread = musteriThread;
		this.garsonThread = garsonThread;
	}


}
