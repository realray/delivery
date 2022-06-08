package com.css.kitchen.delivery.order;

import com.css.kitchen.delivery.vo.Order;
import org.springframework.stereotype.Component;

/**
 * simulator for the cook service.
 * Use multi thread to simulate the cook service.
 * the cooker take the preTime in order to cook the
 * order.
 */
@Component
public class CookService {


    /**
     * use a thread to simulate a cooker to cook an order.
     * if want to simulate more real,Maybe use a threadPool to
     * hold the fixed count of cookers.
     *
     * @param order     need to cooked order
     * @param orderPool contains all the ready order
     */
    public void cook(Order order, OrderPool orderPool) {
        new Thread(new Cooker(order, orderPool)).start();
    }
}
