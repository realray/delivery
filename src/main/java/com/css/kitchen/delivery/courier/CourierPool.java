package com.css.kitchen.delivery.courier;

import com.css.kitchen.delivery.vo.Courier;

import java.time.LocalDateTime;


public abstract class CourierPool {

    /**
     * check wheather has ready courier
     *
     * @return true means has ready courier; false means not
     */
    public abstract boolean hasReadyCourier();

    /**
     * add the ready courier to ready courier pool
     *
     * @param courier the ready courier
     * @return this courierPool
     */
    public CourierPool addReadyCourier(Courier courier){

        courier.setArrivalTime(LocalDateTime.now());
        return this.addReadyCourierInner(courier);
    }

    protected abstract CourierPool addReadyCourierInner(Courier courier);

}
