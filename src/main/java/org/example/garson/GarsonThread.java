package org.example.garson;

import org.example.Koordinasyon;
import org.example.Masa;
import org.example.Order;

import java.util.Vector;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GarsonThread extends Thread{
    private Koordinasyon koordinasyon;
    private Vector<Masa> masalar;
    private int garsonunNumarasi;
    private GarsonUret waiterFactory;
    private Semaphore garsonAktifHizmetSemaphore;
    private Lock waiterLock = new ReentrantLock();
    private Condition masaAtamaCondition = waiterLock.newCondition();
    private Order order;



    public GarsonThread(Koordinasyon koordinasyon, int garsonunNumarasi, GarsonUret waiterFactory, int garsonunIlgilenebilecegiMasaSayisi) {
        this.koordinasyon = koordinasyon;
        this.garsonunNumarasi = garsonunNumarasi;
        this.waiterFactory = waiterFactory;
        this.garsonAktifHizmetSemaphore = new Semaphore(garsonunIlgilenebilecegiMasaSayisi);
        masalar = new Vector<Masa>(garsonunIlgilenebilecegiMasaSayisi);
        this.start();
    }

    public int getUygunMasaSayisi() {int uygunMasalarinSayisi = garsonAktifHizmetSemaphore.availablePermits();return uygunMasalarinSayisi;}

    public void setMasa(Masa masa) {
        try {
            garsonAktifHizmetSemaphore.acquire();
            masalar.add(masa);
            this.waiterLock.lock();
            this.masaAtamaCondition.signalAll();
            this.waiterLock.unlock();

        } catch (InterruptedException ie) {
            System.out.println("WaiterFactory.setTable(" + masa.getMasaNumarasi() + ") IE: " + ie.getMessage());
        }
    }

    public void setSiparis(Order o){
        this.order = o;
    }

    public int getGarsonunNumarasi() {
        return this.garsonunNumarasi;
    }

    public void returnMasa(Masa masa) {
//		this.table = null;
        masalar.remove(masa);
        garsonAktifHizmetSemaphore.release();
    }
}
