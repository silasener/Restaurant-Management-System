package org.example.asci;

import org.example.AsciPanel;
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
                    this.asciLock.lock();
                    Siparis siparis = AsciUret.hazirlanacakSiparisleriGetir().get(0); //order=; garson,aşçı,masa,sipariş
                    asciPanel.asciMesajiEkle("Aşçı: " + asciNumarasi + " ve alınan sipariş " + siparis.getSiparisNo() + " ve siparişin masası " + siparis.getMasa().getMasaNumarasi() + " garsonu :" + siparis.getGarsonThread().getGarsonunNumarasi());
                    Thread.sleep(3000); // aşçının yemek yapma süresi 3 sn
                    asciPanel.asciMesajiEkle("Aşçı:  " + asciNumarasi + " ve tamamladığı sipariş " + siparis.getSiparisNo() + " ve siparişin masası " + siparis.getMasa().getMasaNumarasi() + " garsonu :" + siparis.getGarsonThread().getGarsonunNumarasi());
                    AsciUret.hazirlanacakSiparisleriGetir().remove(0);
                    this.asciLock.unlock();
                    asciPanel.asciMesajiEkle("Aşçı " + asciNumarasi + " yemek yapmaya hazır");
                } else {
                    Thread.sleep(1000 * (int) (Math.random() * 5));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}