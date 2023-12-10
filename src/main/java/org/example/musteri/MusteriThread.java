package org.example.musteri;

import org.example.Koordinasyon;
import org.example.Masa;
import org.example.Order;
import org.example.RestoranYonetimSistemi;

public class MusteriThread extends Thread{
    private int musteriNumarasi;
    private Koordinasyon koordinasyonThreadi;
    private Masa masa;
    private Order order;


    public MusteriThread(int musteriNumarasi, Koordinasyon koordinasyonThreadi) {
        this.musteriNumarasi = musteriNumarasi;
        this.koordinasyonThreadi = koordinasyonThreadi;
        this.start();
    }

    public void setSiparis(Order o){
        this.order = o;
    }

    public Masa getMasa() {return this.masa;}

    public int getMusteriNumarasi() {return this.musteriNumarasi;}

    public void run() {
        try {
            masa = koordinasyonThreadi.seatCustomer(this);
            Thread.sleep(1000 * (int)(Math.random() * 10)); // sleep for between 0 and 10 seconds
            if(koordinasyonThreadi != null){
                RestoranYonetimSistemi.mesajEkle("Müşteri : "+ musteriNumarasi +" ve siparişi:  "+order.getOrderText() +" ve masası "+ getMasa().getMasaNumarasi());
                RestoranYonetimSistemi.garsonMesajiEkle("Müşteri: " + this.getMusteriNumarasi() + " yemek yiyor ", this.getMasa().getGarsonThread().getGarsonunNumarasi());
                Thread.sleep(3000);
                koordinasyonThreadi.musteriAyriliyor(this);
            }
        } catch (InterruptedException ie) {
            System.out.println("CustomerThread.run(): InterruptedException: " + ie.getMessage());
        }
    }

}
