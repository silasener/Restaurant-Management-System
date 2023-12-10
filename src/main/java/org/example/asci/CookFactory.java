package org.example.asci;

import org.example.CookPanel;
import org.example.Order;

import java.util.Vector;

public class CookFactory {
    private static Vector<Order> ordersToCook;

    public CookFactory(CookPanel cp, int numCooks ) {
        ordersToCook = new Vector<Order>();

        for ( int i=0; i<numCooks; i++ ) {
            CookThread c = new CookThread( i, cp, this );
            c.start();
        }

    }

    public void appendNewOrder(Order o) {
        ordersToCook.add(o);
    }
    public static Vector<Order> returnOrdersToCook(){
        return ordersToCook;
    }

}