package com.css.kitchen.delivery.order;

import com.css.kitchen.delivery.vo.Order;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * hold all the ready order in a concurrentLinkedQueue
 * this class is thread safe
 */
public class OrderPool {

    private static final OrderPool orderPool = new OrderPool();

    private ConcurrentLinkedQueue<Order> orderQueue;

    private OrderPool() {
        this.orderQueue = new ConcurrentLinkedQueue<>();
    }

    /**
     * @return the singleton instance
     */
    public static OrderPool getInstance() {
        return orderPool;
    }

    /**
     * put the ready order into pool
     *
     * @param order the ready order
     */
    public void setReady(Order order) {
        order.setPreparedTime(LocalDateTime.now());
        orderQueue.add(order);
    }

    /**
     * check wheather is there ready order in the pool
     *
     * @return true if the pool has ready order
     */
    public boolean hasReadyOrder() {
        return !orderQueue.isEmpty();
    }

    public Order getOrder(){
        return orderQueue.poll();
    }


}
