package org.example.asci;

import org.example.AsciPanel;
import org.example.RestoranYonetimSistemi;
import org.example.Siparis;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AsciThread extends Thread {
    private int asciNumarasi;
    private AsciPanel asciPanel;
    private AsciUret asciUret;
    private Lock asciLock = new ReentrantLock();
    private Semaphore asciMaxHizmetSemaphore;
    private Condition siparisTamamlamaCondition = asciLock.newCondition();

    public AsciThread(int asciNumarasi, AsciPanel asciPanel, AsciUret asciUret) {
        this.asciNumarasi = asciNumarasi;
        this.asciPanel = asciPanel;
        this.asciUret = asciUret;
        this.asciMaxHizmetSemaphore = new Semaphore(2); // aşçı aynı anda yapabilceği yemek sayısı
    }

    public void run() { //aşçı threadleri oluşturunca hep çalışmaya başlar
        while (true) {
            try {
                if (!AsciUret.hazirlanacakSiparisleriGetir().isEmpty()) { //sipariş varsa çalışır  hazirlanacakSiparisleriGetir()==order
                    Siparis siparis2=null;
                    boolean ikiSiparisVarMi=false;
                    //System.out.println("izin sayısı : "+asciMaxHizmetSemaphore.availablePermits());
                    asciMaxHizmetSemaphore.acquire(); // aynı anda yapabilceği yemek sayısını 1 azaltır ; asciMaxHizmetSemaphore.availablePermits() kalan izin sayısını verir
                    //System.out.println("izin sayısı lock : "+asciMaxHizmetSemaphore.availablePermits());
                    this.asciLock.lock();
                    Siparis siparis = AsciUret.hazirlanacakSiparisleriGetir().get(0); //order=; garson,aşçı,masa,sipariş
                    AsciUret.hazirlanacakSiparisleriGetir().remove(0);
                    asciPanel.asciMesajiEkle("Aşçı " + asciNumarasi + " , sipariş " + siparis.getSiparisNo() + " için yemek hazırlıyor, masası:" + siparis.getMasa().getMasaNumarasi() + ", garsonu:" + siparis.getGarsonThread().getGarsonunNumarasi()+", müşterisi:"+siparis.getMusteriNumarasi());
                    RestoranYonetimSistemi.mesajEkle("Aşçı " + asciNumarasi + " , sipariş " + siparis.getSiparisNo() + " için yemek hazırlıyor, masası:" + siparis.getMasa().getMasaNumarasi() + ", garsonu:" + siparis.getGarsonThread().getGarsonunNumarasi()+", müşterisi:"+siparis.getMusteriNumarasi());
                    if(!AsciUret.hazirlanacakSiparisleriGetir().isEmpty()){
                        ikiSiparisVarMi=true;
                        asciMaxHizmetSemaphore.acquire();
                        siparis2=AsciUret.hazirlanacakSiparisleriGetir().get(0);
                        //System.out.println("izin sayısı lock siparis2 : "+asciMaxHizmetSemaphore.availablePermits());
                        AsciUret.hazirlanacakSiparisleriGetir().remove(0);
                        asciPanel.asciMesajiEkle("Aşçı " + asciNumarasi + " , sipariş " + siparis2.getSiparisNo() + " için yemek hazırlıyor, masası:" + siparis2.getMasa().getMasaNumarasi() + ", garsonu:" + siparis2.getGarsonThread().getGarsonunNumarasi()+", müşterisi:"+siparis2.getMusteriNumarasi());
                        RestoranYonetimSistemi.mesajEkle("Aşçı " + asciNumarasi + " , sipariş " + siparis2.getSiparisNo() + " için yemek hazırlıyor, masası:" + siparis2.getMasa().getMasaNumarasi() + ", garsonu:" + siparis2.getGarsonThread().getGarsonunNumarasi()+", müşterisi:"+siparis2.getMusteriNumarasi());
                    }
                    Thread.sleep(3000); // aşçının yemek yapma süresi 3 sn
                    asciPanel.asciMesajiEkle("Aşçı " + asciNumarasi + " , sipariş " + siparis.getSiparisNo()  + " tamamladı" + ", masası: " + siparis.getMasa().getMasaNumarasi() + " , garsonu:" + siparis.getGarsonThread().getGarsonunNumarasi()+", müşterisi:"+siparis.getMusteriNumarasi());
                    RestoranYonetimSistemi.mesajEkle("Aşçı " + asciNumarasi + " , sipariş " + siparis.getSiparisNo()  + " tamamladı" + ", masası: " + siparis.getMasa().getMasaNumarasi() + " , garsonu:" + siparis.getGarsonThread().getGarsonunNumarasi()+", müşterisi:"+siparis.getMusteriNumarasi());
                    asciPanel.asciMesajiEkle("Aşçı " + asciNumarasi + " yemek yapmaya hazır");
                    RestoranYonetimSistemi.mesajEkle("Aşçı " + asciNumarasi + " yemek yapmaya hazır");
                    if(ikiSiparisVarMi){
                        asciPanel.asciMesajiEkle("Aşçı " + asciNumarasi + " , sipariş " + siparis2.getSiparisNo()  + " tamamladı" + ", masası: " + siparis2.getMasa().getMasaNumarasi() + " , garsonu:" + siparis2.getGarsonThread().getGarsonunNumarasi()+", müşterisi:"+siparis2.getMusteriNumarasi());
                        RestoranYonetimSistemi.mesajEkle("Aşçı " + asciNumarasi + " , sipariş " + siparis2.getSiparisNo()  + " tamamladı" + ", masası: " + siparis2.getMasa().getMasaNumarasi() + " , garsonu:" + siparis2.getGarsonThread().getGarsonunNumarasi()+", müşterisi:"+siparis2.getMusteriNumarasi());
                        asciPanel.asciMesajiEkle("Aşçı " + asciNumarasi + " yemek yapmaya hazır");
                        RestoranYonetimSistemi.mesajEkle("Aşçı " + asciNumarasi + " yemek yapmaya hazır");
                        //System.out.println("izin sayısı unlock siparis2 : "+asciMaxHizmetSemaphore.availablePermits());
                        ikiSiparisVarMi=false;
                        asciMaxHizmetSemaphore.release();
                    }
                    asciMaxHizmetSemaphore.release();
                    this.asciLock.unlock();
                    //System.out.println("izin sayısı unlock : "+asciMaxHizmetSemaphore.availablePermits());
                } else {
                    Thread.sleep(1000 * (int) (Math.random() * 5));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}