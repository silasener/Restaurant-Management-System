package org.example;

import org.example.Kasa.KasaThread;
import org.example.garson.GarsonThread;
import org.example.musteri.MusteriThread;

import java.util.Random;


public class Koordinasyon {
    private Masalar masalar;

    public Koordinasyon(int masaNumaralari) {
        this.masalar = new Masalar(masaNumaralari);
    } // masaları oluşturur


    public Masa musteriYerlestir(MusteriThread musteriThread) {
        Masa masa = null;
        try {
            masa = masalar.getTable();
            if (System.currentTimeMillis() - musteriThread.getYaratilmaZamani() > 20000) { //müşteri bekleme süresi 20 saniye doldu mu ?
                RestoranYonetimSistemi.beklemeSuresiDolanMusteriEkle(musteriThread.getMusteriNumarasi());
                masalar.returnMasa(masa); // masa boş olduğu belirtilir
                return null;
            }

            GarsonThread garson = RestoranYonetimSistemi.getGarsonUret().getGarson();
            garson.setMasa(masa);
            masa.masayaOturtveIlgilen(musteriThread, garson);

            RestoranYonetimSistemi.hizmetVerilenMusteriEkle(musteriThread.getMusteriNumarasi());
            RestoranYonetimSistemi.garsonMesajiEkle("Müşteri " + musteriThread.getMusteriNumarasi() + " ,oturduğu masa: " + masa.getMasaNumarasi(), garson.getGarsonunNumarasi());
            RestoranYonetimSistemi.mesajEkle("Müşteri " + musteriThread.getMusteriNumarasi() + " ,oturduğu masa: " + masa.getMasaNumarasi());

            Random rand = new Random();
            int randomOrder = rand.nextInt(2);
            Siparis order = new Siparis(randomOrder, musteriThread.getMusteriNumarasi(), masa, garson); //order içinde garson, masa , aşçı tutularak kullanıma hazır hle getirilir
            RestoranYonetimSistemi.garsonMesajiEkle("Müşteri "+musteriThread.getMusteriNumarasi()+" siparişi alındı, masası " + order.getMasa().getMasaNumarasi()+" ,sipariş no: "+order.getSiparisNo()+" tutarı: " + order.getSiparisTutari(), garson.getGarsonunNumarasi());
            RestoranYonetimSistemi.mesajEkle("Müşteri "+musteriThread.getMusteriNumarasi()+" siparişi alındı, masası " + order.getMasa().getMasaNumarasi()+" ,sipariş no: "+order.getSiparisNo()+" tutarı: " + order.getSiparisTutari());
            RestoranYonetimSistemi.asciUret.yeniSiparisEkle(order);  //aşçının siparişi oluşur
            garson.setSiparis(order);
            musteriThread.setSiparis(order);


        } catch (InterruptedException ie) {
            System.out.println("HostessThread.musteriYerlestir():InterruptedException: " + ie.getMessage());
        }
        return masa;
    }


    public void musteriAyriliyor(MusteriThread musteriThread) {
        RestoranYonetimSistemi.musteriAyrilacakLabelGuncelle(musteriThread.getMusteriNumarasi());
        boolean tumuOnceliksiz = RestoranYonetimSistemi.bekleyenMusteriler().stream().allMatch(musteriKayit -> !musteriKayit.isOncelikliMusteri());
        if (tumuOnceliksiz) {
            RestoranYonetimSistemi.oncelikliMusterilerBitirildi = true;
        }
        RestoranYonetimSistemi.garsonMesajiEkle("Müşteri: " + musteriThread.getMusteriNumarasi() + " yemek yedi , ödeme yapıp ayrılacak", musteriThread.getMasa().getGarsonThread().getGarsonunNumarasi());
        RestoranYonetimSistemi.mesajEkle("Müşteri: " + musteriThread.getMusteriNumarasi() + " yemek yedi , ödeme yapıp ayrılacak");
        RestoranYonetimSistemi.mesajEkle("Müşteri " + musteriThread.getMusteriNumarasi() + " için ödeme alındı sipariş no: "+musteriThread.getSiparis().getSiparisNo()+" - ödeme tutarı : "+musteriThread.getSiparis().getSiparisTutari());
        RestoranYonetimSistemi.kasaUret.odenecekSiparisEkle(musteriThread.getSiparis()); //kasa thread run çalışır
        musteriThread.getMasa().getGarsonThread().returnMasa(musteriThread.getMasa());
        masalar.returnMasa(musteriThread.getMasa());
    }

}
