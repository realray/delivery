package com.css.kitchen.delivery.courier;

import com.css.kitchen.delivery.vo.Courier;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * use a queue to hold the ready courier.
 * this class is thread safe.
 */
public class CourierQeueu extends CourierPool {

    private static final CourierPool CourierQeueu = new CourierQeueu();

    private ConcurrentLinkedQueue<Courier> courierQueue;


    private CourierQeueu() {
        this.courierQueue = new ConcurrentLinkedQueue<>();
    }

    /**
     * return the singleton courier instance
     *
     * @return
     */
    public static CourierPool getInstance() {
        return CourierQeueu;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public boolean hasReadyCourier() {
        return !courierQueue.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CourierPool addReadyCourierInner(Courier courier) {
        courierQueue.add(courier);
        return this;
    }

    public Courier getCourier() {
        return courierQueue.poll();
    }

}
