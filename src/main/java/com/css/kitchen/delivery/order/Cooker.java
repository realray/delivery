package com.css.kitchen.delivery.order;

import com.css.kitchen.delivery.util.LogUtil;
import com.css.kitchen.delivery.vo.Order;
import lombok.SneakyThrows;

/**
 * cooker thread
 */
public class Cooker implements Runnable {

    private Order order;
    private OrderPool orderPool;

    public Cooker(Order order, OrderPool orderPool) {
        this.order = order;
        this.orderPool = orderPool;
    }

    @SneakyThrows
    @Override
    public void run() {
        LogUtil.orderLog("cook start", order);
        Thread.sleep(order.getPrepTime() * 1000);
        orderPool.setReady(order);
        LogUtil.orderLog("cook end", order);

    }
}
