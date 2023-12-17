package org.example.Kasa;

import org.example.RestoranYonetimSistemi;
import org.example.Siparis;
import org.example.musteri.MusteriThread;

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
            RestoranYonetimSistemi.kasaMesajEkle("Müşteri " + siparis.getMusteriNumarasi() + " için ödeme alınıyor sipariş no: " + siparis.getSiparisNo());
            Thread.sleep(1000); // kasa ödeme alma süresi
            RestoranYonetimSistemi.kasaMesajEkle("Müşteri " + siparis.getMusteriNumarasi() + " için ödeme alındı sipariş no: " + siparis.getSiparisNo());
            RestoranYonetimSistemi.mesajEkle("Müşteri " + siparis.getMusteriNumarasi() + " için ödeme alındı: ");
        } catch (Exception e) {
            System.out.println("KasaThread.run(): InterruptedException: " + e.getMessage());
        }
    }
}
