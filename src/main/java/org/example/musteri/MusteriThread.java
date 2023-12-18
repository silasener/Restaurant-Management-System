package org.example.musteri;

import org.example.Koordinasyon;
import org.example.Masa;
import org.example.RestoranYonetimSistemi;
import org.example.Siparis;

public class MusteriThread extends Thread {
    private int musteriNumarasi;
    private Koordinasyon koordinasyonThreadi;
    private Masa masa;
    private Siparis siparis;
    private boolean oncelikliMusteri;
    private long yaratilmaZamani;


    public MusteriThread(int musteriNumarasi, Koordinasyon koordinasyonThreadi, boolean oncelikliMusteri) {
        this.musteriNumarasi = musteriNumarasi;
        this.koordinasyonThreadi = koordinasyonThreadi;
        this.oncelikliMusteri = oncelikliMusteri;
        this.yaratilmaZamani = System.currentTimeMillis();
        this.start();
    }

    public long getYaratilmaZamani() {
        return yaratilmaZamani;
    }

    public boolean isOncelikliMusteri() {
        return oncelikliMusteri;
    }

    public Siparis getSiparis() {
        return siparis;
    }

    public void setSiparis(Siparis siparis) {
        this.siparis = siparis;
    }

    public Masa getMasa() {
        return this.masa;
    }

    public int getMusteriNumarasi() {
        return this.musteriNumarasi;
    }

    public void run() {
        try {
            if (this.isOncelikliMusteri()) {
                masa = koordinasyonThreadi.musteriYerlestir(this);
                Thread.sleep(1000 * (int) (Math.random() * 10)); // sleep for between 0 and 10 seconds
                if (System.currentTimeMillis() - this.yaratilmaZamani > 20000) { //müşteri bekleme süresi 20 saniye doldu mu ?
                    this.interrupt();
                    RestoranYonetimSistemi.beklemeSuresiDolanMusteriEkle(this.getMusteriNumarasi());
                    return;
                }
                while (masa == null) {
                    Thread.sleep(1000);
                }
                if (koordinasyonThreadi != null) {
                    RestoranYonetimSistemi.garsonMesajiEkle("Sipariş no "+siparis.getSiparisNo()+ " teslim edildi , masası "+this.getMasa().getMasaNumarasi()+" ,müşterisi "+musteriNumarasi, this.getMasa().getGarsonThread().getGarsonunNumarasi());
                    RestoranYonetimSistemi.mesajEkle("Müşteri : " + musteriNumarasi + " ve siparişi:  " + siparis.getSiparisTutari() + " ve masası " + getMasa().getMasaNumarasi());
                    RestoranYonetimSistemi.garsonMesajiEkle("Müşteri: " + this.getMusteriNumarasi() + " yemek yiyor ", this.getMasa().getGarsonThread().getGarsonunNumarasi());
                    Thread.sleep(3000); //müşteri yemek yeme süresi
                    koordinasyonThreadi.musteriAyriliyor(this);
                }
            } else {
                while (!RestoranYonetimSistemi.oncelikliMusterilerBitirildi) {
                    if (System.currentTimeMillis() - this.yaratilmaZamani > 20000) { //müşteri bekleme süresi 20 saniye doldu mu ?
                        this.interrupt();
                        RestoranYonetimSistemi.beklemeSuresiDolanMusteriEkle(this.getMusteriNumarasi());
                        return;
                    }
                    Thread.sleep(1000);
                }

                if (System.currentTimeMillis() - this.yaratilmaZamani > 20000) { //müşteri bekleme süresi 20 saniye doldu mu ?
                    this.interrupt();
                    RestoranYonetimSistemi.beklemeSuresiDolanMusteriEkle(this.getMusteriNumarasi());
                    return;
                }

                masa = koordinasyonThreadi.musteriYerlestir(this);
                Thread.sleep(1000 * (int) (Math.random() * 10)); // 0 ile 10 saniye arasında bekletme
                if (koordinasyonThreadi != null) {
                    RestoranYonetimSistemi.mesajEkle("Normal Müşteri : " + musteriNumarasi + " masaya yerleşti.");
                    System.out.println("Öncelikli müşteriler yerleştirildi ,Normal müşteri yerleştiriliyor müşteri no: " + this.musteriNumarasi);
                    RestoranYonetimSistemi.garsonMesajiEkle("Müşteri: " + this.getMusteriNumarasi() + " yemek yiyor ", this.getMasa().getGarsonThread().getGarsonunNumarasi());
                    Thread.sleep(3000); //müşteri yemek yeme süresi
                    koordinasyonThreadi.musteriAyriliyor(this);
                }
            }
        } catch (InterruptedException ie) {
            System.out.println("CustomerThread.run(): InterruptedException: " + ie.getMessage());
        }
    }

}
