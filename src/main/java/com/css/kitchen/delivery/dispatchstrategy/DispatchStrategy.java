package com.css.kitchen.delivery.dispatchstrategy;

import com.css.kitchen.delivery.courier.CourierPool;
import com.css.kitchen.delivery.order.OrderPool;
import com.css.kitchen.delivery.vo.PickUpResult;

import java.util.List;

/**
 * dispatch strategy interface.
 */
public interface DispatchStrategy {

    /**
     *
     * @param orderPool  ready order pool
     * @return the courier hold a ready order
     */
    public List<PickUpResult> dispatch(OrderPool orderPool);

    /**
     *
     * @return the courier pool
     */
    public CourierPool getCourierPool();

}
