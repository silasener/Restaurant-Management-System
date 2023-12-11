package org.example.garson;

import org.example.Koordinasyon;
import org.example.Masa;
import org.example.Siparis;
import org.example.RestoranYonetimSistemi;

import java.util.Vector;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GarsonThread extends Thread{
    private Koordinasyon koordinasyon;
    private Vector<Masa> masalar;
    private int garsonunNumarasi;
    private GarsonUret garsonUret;
    private Semaphore garsonAktifHizmetSemaphore;
    private Lock garsonLock = new ReentrantLock();
    private Condition masaAtamaCondition = garsonLock.newCondition();
    private Condition siparisTamamlamaCondition = garsonLock.newCondition();
    private Siparis siparis;



    public GarsonThread(Koordinasyon koordinasyon, int garsonunNumarasi, GarsonUret garsonUret, int garsonunIlgilenebilecegiMasaSayisi) {
        this.koordinasyon = koordinasyon;
        this.garsonunNumarasi = garsonunNumarasi;
        this.garsonUret = garsonUret;
        this.garsonAktifHizmetSemaphore = new Semaphore(garsonunIlgilenebilecegiMasaSayisi);
        masalar = new Vector<Masa>(garsonunIlgilenebilecegiMasaSayisi);
        this.start();
    }

    public int getUygunMasaSayisi() {
        int uygunMasalarinSayisi = garsonAktifHizmetSemaphore.availablePermits();
        return uygunMasalarinSayisi;
    }

    public void setMasa(Masa masa) {
        try {
            garsonAktifHizmetSemaphore.acquire();
            masalar.add(masa);
            this.garsonLock.lock();
            this.masaAtamaCondition.signalAll();
            this.garsonLock.unlock();

        } catch (InterruptedException ie) {
            System.out.println("WaiterFactory.setTable(" + masa.getMasaNumarasi() + ") IE: " + ie.getMessage());
        }
    }

    public void setSiparis(Siparis o){
        try {
            Thread.sleep(2000); //garson 2 saniye sipariş alıyor
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        this.siparis = o;
    }

    public Siparis returnSiparis(){
        return siparis;
    }

    public int getGarsonunNumarasi() {return this.garsonunNumarasi;}

    public void returnMasa(Masa masa) {
        masalar.remove(masa);
        garsonAktifHizmetSemaphore.release();
    }


    public Masa getMasa(int i) {
        if (masalar.size() > i) {
            return masalar.elementAt(i);
        }
        return null;
    }


    public void run() {
        try {
            while (true) {
                this.garsonLock.lock();
                this.masaAtamaCondition.await(); //Garson, bir masa atanana kadar bekler , masa atanır
                this.siparisTamamlamaCondition.await(); //müşterinin siparişi tamamlanana kadar bekler.
                this.garsonLock.unlock();
                Thread.sleep(1000 * (int)(Math.random() * 10)); // sleep for between 0 and 10 seconds
                if (getMasa(0) != null) { // Garsonun ilgilendiği masalar içinde en az bir masa varsa
                    getMasa(0).getLock().lock();
                    // signal the customer who is "eating"
                    getMasa(0).getHazirCondition().signal(); //Masanın  müşterisine "yemek hazır"
                    RestoranYonetimSistemi.garsonMesajiEkle("Garson: "+getGarsonunNumarasi()+" siparişi "+ returnSiparis().getSiparisTutari()+" masası "+ siparis.getMasa().getMasaNumarasi(),getGarsonunNumarasi());
                    getMasa(0).getLock().unlock();
                }
            }
        } catch (InterruptedException ie) {
            System.out.println("CustomerThread.run(): InterruptedException: " + ie.getMessage());
        }
    }


}
