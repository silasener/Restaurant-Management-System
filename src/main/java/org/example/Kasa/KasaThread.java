package org.example.Kasa;

import org.example.RestoranYonetimSistemi;
import org.example.Siparis;
import org.example.asci.AsciUret;
import org.example.musteri.MusteriThread;

import java.util.List;
import java.util.Objects;

public class KasaThread extends Thread {
    private MusteriThread musteriThread;
    private static double siparisTutari;
    private static int odemeYapanToplamMusteriSayisi;

    public KasaThread(MusteriThread musteriThread) {
        this.musteriThread = musteriThread;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);  // Kasa ödeme işlemi için 1 saniye bekler
            Siparis siparis = AsciUret.hazirlanacakSiparisleriGetir().get(0);
            if(Objects.nonNull(siparis)){
                siparisTutari+=siparis.getSiparisTutari();
                RestoranYonetimSistemi.toplamOdemeField.setText(String.valueOf(KasaThread.getSiparisTutari()));
                odemeYapanToplamMusteriSayisi++;
                RestoranYonetimSistemi.musteriSayisiField.setText(String.valueOf(KasaThread.getOdemeYapanToplamMusteriSayisi()));
            }
            RestoranYonetimSistemi.kasaMesajEkle("Müşteri " + musteriThread.getMusteriNumarasi() + " için ödeme alındı: "+siparis.getSiparisTutari());
            RestoranYonetimSistemi.mesajEkle("Müşteri " + musteriThread.getMusteriNumarasi() + " için ödeme alındı: ");
        } catch (InterruptedException e) {
            System.out.println("KasaThread.run(): InterruptedException: " + e.getMessage());
        }
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
}
