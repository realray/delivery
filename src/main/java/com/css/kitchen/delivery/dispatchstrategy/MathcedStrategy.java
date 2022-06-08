package com.css.kitchen.delivery.dispatchstrategy;

import com.css.kitchen.delivery.courier.CourierMap;
import com.css.kitchen.delivery.courier.CourierPool;
import com.css.kitchen.delivery.order.OrderPool;
import com.css.kitchen.delivery.util.LogUtil;
import com.css.kitchen.delivery.vo.Courier;
import com.css.kitchen.delivery.vo.Order;
import com.css.kitchen.delivery.vo.PickUpResult;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Matched: a courier is dispatched for a specific order and may only pick up that order
 */
@Component("mathcedStrategy")
public class MathcedStrategy implements DispatchStrategy {

    @Override
    public List<PickUpResult> dispatch(OrderPool orderPool) {

        List<PickUpResult> results = new ArrayList<>();
        CourierMap courierMap = (CourierMap) getCourierPool();
        Order order = orderPool.getOrder();
        while (order != null) {
            Courier courier = courierMap.getCouier(order.getId());
            //if there are more than one dispatcher,this situation may occur
            //so need a while loop to acquire an available courier.
            while (courier == null) {
                courier = courierMap.getCouier(order.getId());
            }
            PickUpResult result = new PickUpResult(courier, order, LocalDateTime.now());
            LogUtil.orderCourierLog("picked up", order, courier);
            results.add(result);

            order = orderPool.getOrder();
        }

        return results;
    }

    @Override
    public CourierPool getCourierPool() {
        return CourierMap.getInstance();
    }
}
