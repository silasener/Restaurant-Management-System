package org.example.Kasa;

import org.example.Koordinasyon;
import org.example.RestoranYonetimSistemi;
import org.example.Siparis;
import org.example.musteri.MusteriThread;

import javax.swing.*;
import java.util.Vector;
import java.util.concurrent.Semaphore;

public class KasaThread extends Thread {
    private static double siparisTutari;
    private static int odemeYapanToplamMusteriSayisi;
    private Koordinasyon koordinasyon;
    private Semaphore kasaHizmetSemaphore;
    private JPanel panelKasa;
    private KasaUret kasaUret;

    private static Vector<Siparis> odenecekSiparisler;


    public KasaThread(JPanel panelKasa, Koordinasyon koordinasyon,KasaUret kasaUret) {
        this.koordinasyon=koordinasyon;
        this.panelKasa=panelKasa;
        this.kasaUret=kasaUret;
        this.kasaHizmetSemaphore= new Semaphore(1); // 1 sipariş ödemesi alabilir yalnızca
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
        while (true){
            try {
                if(!KasaUret.odenecekSiparisleriGetir().isEmpty()){
                    kasaHizmetSemaphore.acquire();
                    Siparis siparis =KasaUret.odenecekSiparisleriGetir().get(0);
                    KasaUret.odenecekSiparisleriGetir().remove(0);
                    siparisTutari += siparis.getSiparisTutari();
                    RestoranYonetimSistemi.toplamOdemeField.setText(String.valueOf(KasaThread.getSiparisTutari()));
                    odemeYapanToplamMusteriSayisi++;
                    RestoranYonetimSistemi.musteriSayisiField.setText(String.valueOf(KasaThread.getOdemeYapanToplamMusteriSayisi()));
                    RestoranYonetimSistemi.kasaMesajEkle("Müşteri " + siparis.getMusteriNumarasi() + " için ödeme alınıyor sipariş no: " + siparis.getSiparisNo()+ " - ödeme tutarı: " + siparis.getSiparisTutari());
                    Thread.sleep(1000); // kasa ödeme alma süresi
                    RestoranYonetimSistemi.kasaMesajEkle("Müşteri " + siparis.getMusteriNumarasi() + " için ödeme alındı sipariş no: " + siparis.getSiparisNo()+ " - ödeme tutarı: " + siparis.getSiparisTutari());
                    kasaHizmetSemaphore.release();
                }else{
                    Thread.sleep(1000); //ödenecek sipariş yok uyu
                }
            } catch (Exception e) {
                System.out.println("KasaThread.run(): InterruptedException: " + e.getMessage());
            }
        }

    }

}
