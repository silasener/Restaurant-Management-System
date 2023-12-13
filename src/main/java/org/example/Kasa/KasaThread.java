package org.example.Kasa;

import org.example.Koordinasyon;
import org.example.RestoranYonetimSistemi;
import org.example.Siparis;
import org.example.asci.AsciUret;
import org.example.musteri.MusteriThread;

import java.util.List;
import java.util.Objects;
import java.util.Vector;

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
                    Siparis siparis=musteriThread.getSiparis();
                   // Siparis siparis = AsciUret.hazirlanacakSiparisleriGetir().get(0);
                    siparisTutari += siparis.getSiparisTutari();
                    RestoranYonetimSistemi.toplamOdemeField.setText(String.valueOf(KasaThread.getSiparisTutari()));
                    odemeYapanToplamMusteriSayisi++;
                    RestoranYonetimSistemi.musteriSayisiField.setText(String.valueOf(KasaThread.getOdemeYapanToplamMusteriSayisi()));
                    RestoranYonetimSistemi.kasaMesajEkle("Müşteri " + siparis.getMusteriNumarasi()+ " için ödeme alındı sipariş no: "+siparis.getSiparisNo());
                    RestoranYonetimSistemi.mesajEkle("Müşteri " + siparis.getMusteriNumarasi() + " için ödeme alındı: ");
        } catch (Exception e) {
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
