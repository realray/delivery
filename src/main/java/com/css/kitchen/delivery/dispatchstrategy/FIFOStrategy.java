package com.css.kitchen.delivery.dispatchstrategy;

import com.css.kitchen.delivery.courier.CourierPool;
import com.css.kitchen.delivery.courier.CourierQeueu;
import com.css.kitchen.delivery.order.OrderPool;
import com.css.kitchen.delivery.util.LogUtil;
import com.css.kitchen.delivery.vo.Courier;
import com.css.kitchen.delivery.vo.Order;
import com.css.kitchen.delivery.vo.PickUpResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * FIFO
 * a courier picks up the next available order upon arrival. If there are
 * multiple orders available, pick up the first available order.
 */
@Slf4j
@Component("fifoStrategy")
public class FIFOStrategy implements DispatchStrategy {

    /**
     * @param orderPool ready order pool
     * @return
     */
    @Override
    public List<PickUpResult> dispatch(OrderPool orderPool) {

        List<PickUpResult> results = new ArrayList<>();
        CourierQeueu courierQeueu = (CourierQeueu) this.getCourierPool();
        Order order = orderPool.getOrder();
        while (order != null) {
            Courier courier = courierQeueu.getCourier();
            //if there are more than one dispatcher,this situation may occur
            //so need a while loop to acquire an available courier.
            while (courier == null) {
                courier = courierQeueu.getCourier();
            }
            PickUpResult result = new PickUpResult(courier, order, LocalDateTime.now());
            LogUtil.orderCourierLog("picked up",order,courier);
            results.add(result);
            order = orderPool.getOrder();
        }


        return results;
    }


    @Override
    public CourierPool getCourierPool() {
        return CourierQeueu.getInstance();
    }
}
