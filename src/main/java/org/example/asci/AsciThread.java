package org.example.asci;

import org.example.AsciPanel;
import org.example.Siparis;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AsciThread extends Thread {
    private int asciNumarasi;
    private AsciPanel asciPanel;
    private AsciUret asciUret;

    private Lock asciLock = new ReentrantLock();
    private Condition siparisTamamlamaCondition = asciLock.newCondition();


    public AsciThread(int asciNumarasi, AsciPanel asciPanel, AsciUret asciUret) {
        this.asciNumarasi = asciNumarasi;
        this.asciPanel = asciPanel;
        this.asciUret = asciUret;
    }

    public void run() { //aşçı threadleri oluşturunca hep çalışmaya başlar
        while(true){
            asciPanel.asciMesajiEkle( "Aşçı " + asciNumarasi + " yemek yapmaya hazır" );
            try {
                if(!AsciUret.hazirlanacakSiparisleriGetir().isEmpty()){ //sipariş varsa çalışır  hazirlanacakSiparisleriGetir()==order

                    this.asciLock.lock();

                    Siparis o = AsciUret.hazirlanacakSiparisleriGetir().get(0); //order=; garson,aşçı,masa,sipariş
                    AsciUret.hazirlanacakSiparisleriGetir().remove(0);
                    asciPanel.asciMesajiEkle("Aşçı: "+ asciNumarasi + " ve alınan sipariş " + o.getOrderText()+ " ve siparişin masası " +o.getMasa().getMasaNumarasi()+" garsonu :"+o.getGarsonThread().getGarsonunNumarasi());
                    Thread.sleep(3000); // aşçının yemek yapma süresi 3 sn

                    asciPanel.asciMesajiEkle("Aşçı:  " + asciNumarasi + " ve tamamladığı siparşi " + o.getOrderText()+ " ve siparişin masası " +o.getMasa().getMasaNumarasi()+" garsonu :"+o.getGarsonThread().getGarsonunNumarasi());

                    this.asciLock.unlock();
                }
                else{
                    Thread.sleep(1000 * (int)(Math.random() * 20));
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }
}