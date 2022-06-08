package com.css.kitchen.delivery;

import com.css.kitchen.delivery.courier.CourierCaller;
import com.css.kitchen.delivery.dispatchstrategy.DispatchStrategy;
import com.css.kitchen.delivery.order.CookService;
import com.css.kitchen.delivery.order.OrderPool;
import com.css.kitchen.delivery.util.LogUtil;
import com.css.kitchen.delivery.util.StatisticUtil;
import com.css.kitchen.delivery.vo.Order;
import com.css.kitchen.delivery.vo.PickUpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CountDownLatch;

@Component
public class Dispatcher {

    @Autowired
    private CourierCaller courierCaller;

    @Autowired
    private CookService cookService;

    private DispatchStrategy dispatchStrategy;

    /**
     * set the dispatch strategy
     *
     * @param dispatchStrategy
     */
    public void setDispatchStrategy(DispatchStrategy dispatchStrategy) {
        this.dispatchStrategy = dispatchStrategy;
    }

    /**
     * reveive an order
     *
     * @param order
     */
    public void receiveOrder(Order order) {
        LogUtil.orderLog("order received", order);
        //call courier
        courierCaller.callCourier(order, dispatchStrategy.getCourierPool());
        cookService.cook(order, OrderPool.getInstance());
    }

    /**
     * this method should run in a child thread to simulate the
     * kitchen bar. the bar will watch the ready order and ready courier,
     * then binding the target order and the target courier to deliver.
     */
    public void dispatcherRun(int orderSize, CountDownLatch downLatch) {
        if (dispatchStrategy == null) {
            throw new IllegalArgumentException("need call method setDispatchStrategy to set dispatch strategy");
        }

        while (StatisticUtil.getSolvedOrderSize() < orderSize) {
            if (OrderPool.getInstance().hasReadyOrder()
                    && dispatchStrategy.getCourierPool().hasReadyCourier()) {

                List<PickUpResult> pickUpResults = dispatchStrategy.dispatch(OrderPool.getInstance());

                StatisticUtil.addSample(pickUpResults);
            }
        }

        downLatch.countDown();

    }
}
