package com.css.kitchen.delivery.courier;

import com.css.kitchen.delivery.util.LogUtil;
import com.css.kitchen.delivery.vo.Courier;
import com.css.kitchen.delivery.vo.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * This class is used to simulate the caller of courier.
 * The caller call the courier then after 3-15 seconds
 * the courier arrive, then put the courier into ready pool.
 */
@Slf4j
@Component
public class CourierCaller {

    ExecutorService cookerPool = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
            60L, TimeUnit.SECONDS,
            new SynchronousQueue<Runnable>());

    /**
     * simulate the call process of courier
     *
     * @param courierPool
     */
    public void callCourier(Order order, CourierPool courierPool) {
        LogUtil.orderLog("courier call", order);
        cookerPool.execute(() -> {
            Courier courier = new Courier();
            courier.setId(UUID.randomUUID().toString());
            courier.setOrderId(order.getId());
            Random random = new Random();
            int arrivedSecond = random.nextInt(13) + 3;
            try {
                Thread.sleep(arrivedSecond*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            courierPool.addReadyCourier(courier);
            LogUtil.courierLog("courier arrived", courier);

        });
    }
}
