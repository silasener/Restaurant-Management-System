package org.example.asci;

import org.example.CookPanel;
import org.example.Order;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CookThread extends Thread {
    private int cookNumber;
    private CookPanel cookPanel;
    private CookFactory cookFactory;

    private Lock cookLock = new ReentrantLock();
    private Condition orderCompleteCondition = cookLock.newCondition();


    public CookThread( int n, CookPanel cp, CookFactory cf ) {
        cookNumber = n;
        cookPanel = cp;
        cookFactory = cf;
    }

    public void run() { //aşçı threadleri oluşturunca hep çalışmaya başlar
        while(true){
            cookPanel.addCookMessage( "Aşçı " + cookNumber + " yemek yapmaya hazır" );
            try {
                if(!CookFactory.returnOrdersToCook().isEmpty()){ //sipariş varsa çalışır  returnOrdersToCook()==order

                    this.cookLock.lock();

                    Order o = CookFactory.returnOrdersToCook().get(0); //order=; garson,aşçı,masa,sipariş
                    CookFactory.returnOrdersToCook().remove(0);
                    cookPanel.addCookMessage("Aşçı: "+ cookNumber + " ve alınan sipariş " + o.getOrderText()+ " ve siparişin masası " +o.getMasa().getMasaNumarasi()+" garsonu :"+o.getGarson().getGarsonunNumarasi());
                    Thread.sleep(3000); // aşçının yemek yapma süresi 3 sn

                    cookPanel.addCookMessage("Aşçı:  " +cookNumber + " ve tamamladığı siparşi " + o.getOrderText()+ " ve siparişin masası " +o.getMasa().getMasaNumarasi()+" garsonu :"+o.getGarson().getGarsonunNumarasi());

                    this.cookLock.unlock();
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