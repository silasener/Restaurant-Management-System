package org.example.garson;

import org.example.Koordinasyon;
import org.example.Masa;
import org.example.Order;
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
    private GarsonUret waiterFactory;
    private Semaphore garsonAktifHizmetSemaphore;
    private Lock waiterLock = new ReentrantLock();
    private Condition masaAtamaCondition = waiterLock.newCondition();
    private Condition siparisTamamlamaCondition = waiterLock.newCondition();
    private Order order;



    public GarsonThread(Koordinasyon koordinasyon, int garsonunNumarasi, GarsonUret waiterFactory, int garsonunIlgilenebilecegiMasaSayisi) {
        this.koordinasyon = koordinasyon;
        this.garsonunNumarasi = garsonunNumarasi;
        this.waiterFactory = waiterFactory;
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
            this.waiterLock.lock();
            this.masaAtamaCondition.signalAll();
            this.waiterLock.unlock();

        } catch (InterruptedException ie) {
            System.out.println("WaiterFactory.setTable(" + masa.getMasaNumarasi() + ") IE: " + ie.getMessage());
        }
    }

    public void setSiparis(Order o){this.order = o;}

    public Order returnSiparis(){
        return order;
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
                // waiter can only have one table currently
                // wait until notified, meaning that a table has been seated
                // i don't think i always want to do this - what if a table has already been set?
                this.waiterLock.lock();
                this.masaAtamaCondition.await();
                this.siparisTamamlamaCondition.await();
                this.waiterLock.unlock();
                Thread.sleep(1000 * (int)(Math.random() * 10)); // sleep for between 0 and 10 seconds
                if (getMasa(0) != null) {
                    getMasa(0).getLock().lock();
                    // signal the customer who is "eating"
                    getMasa(0).getReadyCondition().signal();
                    RestoranYonetimSistemi.garsonMesajiEkle("Garson: "+getGarsonunNumarasi()+" siparişi "+ returnSiparis().getOrderText()+" masası "+order.getMasa().getMasaNumarasi(),getGarsonunNumarasi());
                    getMasa(0).getLock().unlock();
                }
            }
        } catch (InterruptedException ie) {
            System.out.println("CustomerThread.run(): InterruptedException: " + ie.getMessage());
        }
    }


}
