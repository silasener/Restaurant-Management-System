package org.example.garson;

import org.example.Koordinasyon;
import org.example.RestoranYonetimSistemi;

import java.util.Vector;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GarsonUret {

    private Vector<GarsonThread> garsonThreadVectorleri = new Vector<GarsonThread>();
    private int garsonSayisi;
    private int garsonunIlgilenebilecegiMasaSayisi;
    private Koordinasyon koordinasyon;
    private Lock lock = new ReentrantLock();
    private Condition garsonUygunluguCondition = lock.newCondition();

    public GarsonUret(Koordinasyon koordinasyon, int garsonSayisi) {
        this.koordinasyon = koordinasyon;
        this.garsonSayisi = garsonSayisi;
        int garsonBasinaDusenMasaSayisi= (int) Math.ceil((double) RestoranYonetimSistemi.toplamMasaSayisi / garsonSayisi);
        this.garsonunIlgilenebilecegiMasaSayisi = garsonBasinaDusenMasaSayisi;  //garsonun ilgilenebileceği masa sayısı
        for (int i = 0; i < this.garsonSayisi; i++) {
            garsonThreadVectorleri.add(new GarsonThread(this.koordinasyon, i, this, garsonunIlgilenebilecegiMasaSayisi));
        }
    }


    public GarsonThread getGarson() {
        GarsonThread garsonThreadi = null;
        try {
            lock.lock();
            while (garsonThreadi == null) {
                int i;
                for (i = 0; i < garsonThreadVectorleri.size(); i++) {
                    garsonThreadi = garsonThreadVectorleri.get(i);
                    if (garsonThreadi != null) {
                        if (garsonThreadi.getUygunMasaSayisi() > 0) {
                            break;
                        }
                    }
                }
                if (i == garsonThreadVectorleri.size()) {
                    garsonUygunluguCondition.await();
                }
            }
        } catch (InterruptedException ie) {
            System.out.println("WaiterFactory.getGarsonThread(): IE : " + ie.getMessage());
        } finally {
            lock.unlock();
        }
        return garsonThreadi;
    }
}
