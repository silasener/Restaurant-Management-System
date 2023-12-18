package org.example;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Masalar {
    private int toplamMasaSayisi;
    private int doluMasaSayisi;
    private Masa[] masalarListesi;
    private Lock lock = new ReentrantLock(); // senkronizasyon için kullanılır
    private Condition uygunMasaYok = lock.newCondition(); //Bekleyen threadlere masanın dolu olduğunu bildirmek için kullanılır

    public Masalar(int toplamMasaSayisi) {
        this.toplamMasaSayisi = toplamMasaSayisi;
        this.masalarListesi = new Masa[toplamMasaSayisi];
        this.doluMasaSayisi = 0;
    }

    public Masa getTable() throws InterruptedException {
        lock.lock();
        Masa tableToReturn = null;
        try {
            while (this.doluMasaSayisi == this.toplamMasaSayisi) {
                //uygun masa yok , beklenir
                uygunMasaYok.await();
                //uygun masa var
            }
            // when this thread is signalled, find the table that is available
            for (int i = 0; i < this.toplamMasaSayisi; i++) {
                if (masalarListesi[i] == null) {
                    masalarListesi[i] = new Masa(i);
                    tableToReturn = masalarListesi[i];
                    doluMasaSayisi++;
                    break;
                }
            }
        } finally {
            lock.unlock();
        }
        RestoranYonetimSistemi.masaDurumlariniGuncelle(tableToReturn.getMasaNumarasi());
        return tableToReturn;
    }

    public void returnMasa(Masa table) {
        lock.lock();
        try {
            for (int i = 0; i < this.toplamMasaSayisi; i++) {
                if (i == table.getMasaNumarasi()) {
                    masalarListesi[i] = null;
                    doluMasaSayisi--;
                    //uygun masa şu:  table.getMasaNumarasi()
                  //  RestoranYonetimSistemi.mesajEkle("Masa: " + table.getMasaNumarasi() + " uygun");
                    RestoranYonetimSistemi.masaDurumlariniGuncelle(table.getMasaNumarasi());
                    uygunMasaYok.signal();
                }
            }
        } finally {
            lock.unlock();
        }
    }
}
