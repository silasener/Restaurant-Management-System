package org.example.Kasa;

import org.example.Koordinasyon;
import org.example.RestoranYonetimSistemi;
import org.example.Siparis;
import org.example.musteri.MusteriThread;

import java.util.Vector;

public class KasaThread extends Thread {
    private static double siparisTutari;
    private static int odemeYapanToplamMusteriSayisi;
    private MusteriThread musteriThread;

    public KasaThread(MusteriThread musteriThread) {
        this.musteriThread = musteriThread;
    }

    public static double getSiparisTutari() {
        return siparisTutari;
    }

    public static void setSiparisTutari(double siparisTutari) {
        KasaThread.siparisTutari = siparisTutari;
    }

    public static int getOdemeYapanToplamMusteriSayisi() {
        return odemeYapanToplamMusteriSayisi;
    }

    public static void setOdemeYapanToplamMusteriSayisi(int odemeYapanToplamMusteriSayisi) {
        KasaThread.odemeYapanToplamMusteriSayisi = odemeYapanToplamMusteriSayisi;
    }

    @Override
    public void run() {
        try {
            Siparis siparis = musteriThread.getSiparis();
            siparisTutari += siparis.getSiparisTutari();
            RestoranYonetimSistemi.toplamOdemeField.setText(String.valueOf(KasaThread.getSiparisTutari()));
            odemeYapanToplamMusteriSayisi++;
            RestoranYonetimSistemi.musteriSayisiField.setText(String.valueOf(KasaThread.getOdemeYapanToplamMusteriSayisi()));
            RestoranYonetimSistemi.kasaMesajEkle("Müşteri " + siparis.getMusteriNumarasi() + " için ödeme alındı sipariş no: " + siparis.getSiparisNo()+ " - ödeme tutarı: " + siparis.getSiparisTutari());
           // RestoranYonetimSistemi.mesajEkle("Müşteri " + siparis.getMusteriNumarasi() + " için ödeme alındı sipariş no: "+siparis.getSiparisNo()+" - ödeme tutarı : "+siparis.getSiparisTutari());
            Thread.sleep(1000); // kasa ödeme alma süresi
        } catch (Exception e) {
            System.out.println("KasaThread.run(): InterruptedException: " + e.getMessage());
        }
    }
}
