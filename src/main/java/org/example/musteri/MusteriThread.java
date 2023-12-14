package org.example.musteri;

import org.example.Koordinasyon;
import org.example.Masa;
import org.example.Siparis;
import org.example.RestoranYonetimSistemi;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    public boolean isOncelikliMusteri() {
        return oncelikliMusteri;
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
            if(this.isOncelikliMusteri()){
                masa = koordinasyonThreadi.musteriYerlestir(this);
                Thread.sleep(1000 * (int)(Math.random() * 10)); // sleep for between 0 and 10 seconds
                if(koordinasyonThreadi != null){
                    RestoranYonetimSistemi.mesajEkle("Müşteri : "+ musteriNumarasi +" ve siparişi:  "+ siparis.getSiparisTutari() +" ve masası "+ getMasa().getMasaNumarasi());
                    System.out.println("Öncelikli müşteri yerleştiriliyor müşteri no:"+musteriNumarasi);
                    RestoranYonetimSistemi.garsonMesajiEkle("Müşteri: " + this.getMusteriNumarasi() + " yemek yiyor ", this.getMasa().getGarsonThread().getGarsonunNumarasi());
                    Thread.sleep(3000);
                    koordinasyonThreadi.musteriAyriliyor(this);
                }
            }else{
                while (!RestoranYonetimSistemi.oncelikliMusterilerBitirildi) {
                    Thread.sleep(1000);
                }
                masa = koordinasyonThreadi.musteriYerlestir(this);
                Thread.sleep(1000 * (int)(Math.random() * 10)); // 0 ile 10 saniye arasında bekletme
                if (koordinasyonThreadi != null) {
                    RestoranYonetimSistemi.mesajEkle("Normal Müşteri : " + musteriNumarasi + " masaya yerleşti.");
                    System.out.println("Öncelikli müşteriler yerleştirildi ,Normal müşteri yerleştiriliyor müşteri no: "+this.musteriNumarasi);
                    RestoranYonetimSistemi.garsonMesajiEkle("Müşteri: " + this.getMusteriNumarasi() + " yemek yiyor ", this.getMasa().getGarsonThread().getGarsonunNumarasi());
                    Thread.sleep(3000);
                    koordinasyonThreadi.musteriAyriliyor(this);
                }
            }

        } catch (InterruptedException ie) {
            System.out.println("CustomerThread.run(): InterruptedException: " + ie.getMessage());
        }
    }

}
