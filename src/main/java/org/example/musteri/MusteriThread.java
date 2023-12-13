package org.example.musteri;

import org.example.Koordinasyon;
import org.example.Masa;
import org.example.Siparis;
import org.example.RestoranYonetimSistemi;

public class MusteriThread extends Thread{
    private int musteriNumarasi;
    private Koordinasyon koordinasyonThreadi;
    private Masa masa;
    private Siparis siparis;
    private boolean oncelikliMusteri;


    public MusteriThread(int musteriNumarasi, Koordinasyon koordinasyonThreadi,boolean oncelikliMusteri) {
        this.musteriNumarasi = musteriNumarasi;
        this.koordinasyonThreadi = koordinasyonThreadi;
        this.oncelikliMusteri=oncelikliMusteri;
        this.start();
    }

    public Siparis getSiparis() {
        return siparis;
    }

    public void setSiparis(Siparis siparis){
        this.siparis = siparis;
    }

    public Masa getMasa() {return this.masa;}

    public int getMusteriNumarasi() {return this.musteriNumarasi;}

    public void run() {
        try {
            masa = koordinasyonThreadi.musteriYerlestir(this);
            Thread.sleep(1000 * (int)(Math.random() * 10)); // sleep for between 0 and 10 seconds
            if(koordinasyonThreadi != null){
                RestoranYonetimSistemi.mesajEkle("Müşteri : "+ musteriNumarasi +" ve siparişi:  "+ siparis.getSiparisTutari() +" ve masası "+ getMasa().getMasaNumarasi());
                RestoranYonetimSistemi.garsonMesajiEkle("Müşteri: " + this.getMusteriNumarasi() + " yemek yiyor ", this.getMasa().getGarsonThread().getGarsonunNumarasi());
                Thread.sleep(3000);
                koordinasyonThreadi.musteriAyriliyor(this);
            }
        } catch (InterruptedException ie) {
            System.out.println("CustomerThread.run(): InterruptedException: " + ie.getMessage());
        }
    }

}
