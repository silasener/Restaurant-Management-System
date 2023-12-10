package org.example.Kasa;

import org.example.RestoranYonetimSistemi;
import org.example.musteri.MusteriThread;

public class KasaThread extends Thread {
    private MusteriThread musteriThread;

    public KasaThread(MusteriThread musteriThread) {
        this.musteriThread = musteriThread;
    }

    @Override
    public void run() {
        try {
            // Ödeme işlemi için 1 saniye bekle
            Thread.sleep(1000);
            RestoranYonetimSistemi.kasaMesajEkle("Müşteri " + musteriThread.getMusteriNumarasi() + " için ödeme alındı: ");
            RestoranYonetimSistemi.mesajEkle("Müşteri " + musteriThread.getMusteriNumarasi() + " için ödeme alındı: ");
        } catch (InterruptedException e) {
            System.out.println("KasaThread.run(): InterruptedException: " + e.getMessage());
        }
    }
}