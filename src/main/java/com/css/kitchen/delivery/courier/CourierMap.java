package com.css.kitchen.delivery.courier;

import com.css.kitchen.delivery.vo.Courier;

import java.util.concurrent.ConcurrentHashMap;
/**
 * this class hold all the ready courier which is a singleton instance.
 * this class is a thread safe class.But when call addReadyCourier,the readyCourierCount
 * value update may delay after the courier add the pool.So in mutil threads call
 * hasReadyCourier & addReadyCourier may get some trouble need another loop to call
 * hasReadyCourier.
 */
public class CourierMap extends CourierPool{

    private static final CourierPool CourierMap = new CourierMap();

    private ConcurrentHashMap<String, Courier> courierMap;

    private CourierMap() {
        this.courierMap = new ConcurrentHashMap<>();
    }

    /**
     * return the singleton courier instance
     *
     * @return
     */
    public static CourierPool getInstance() {
        return CourierMap;
    }

    /**
     * check wheather has ready courier
     *
     * @return true means has ready courier; false means not
     */
    @Override
    public boolean hasReadyCourier() {
        return !courierMap.isEmpty();
    }

    /**
     * add the ready courier to ready courier pool
     *
     * @param courier the ready courier
     * @return
     */
    @Override
    public CourierPool addReadyCourierInner(Courier courier) {
        courierMap.put(courier.getOrderId(), courier);
        return this;
    }

    public Courier getCouier(String orderId){
        return courierMap.get(orderId);
    }
}
