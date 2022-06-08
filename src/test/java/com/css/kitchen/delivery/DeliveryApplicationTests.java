package com.css.kitchen.delivery;

import com.css.kitchen.delivery.dispatchstrategy.DispatchStrategy;
import com.css.kitchen.delivery.util.OrderGenerator;
import com.css.kitchen.delivery.util.StatisticUtil;
import com.css.kitchen.delivery.vo.Order;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RunWith(SpringRunner.class)
@SpringBootTest
class DeliveryApplicationTests {

    @Autowired
    private Dispatcher dispatcher;

    @Autowired
    private DispatchStrategy fifoStrategy;

    @Autowired
    private DispatchStrategy mathcedStrategy;

    private ExecutorService ex = Executors.newFixedThreadPool(1);


    @Test
    void contextLoads() {

    }

    @Test
    void testDispatchFifo() {
        System.out.println("[[test FIFO start]]");
        dispatcher.setDispatchStrategy(fifoStrategy);
        runSimulate();
        System.out.println("[[test FIFO end]]");
    }

    @Test
    void testDispatchMathced() {
        System.out.println("[[test MATCHED start]]");
        dispatcher.setDispatchStrategy(mathcedStrategy);
        runSimulate();
        System.out.println("[[test MATCHED end]]");

    }

    @SneakyThrows
    private void runSimulate() {

        Queue<Order> orderQueue = OrderGenerator.getAllOrder();

        CountDownLatch downLatch = new CountDownLatch(1);

        ex.execute(() -> dispatcher.dispatcherRun(orderQueue.size(),downLatch));

        //simulate receiving 2 delivery orders per second
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!orderQueue.isEmpty()) {
                    dispatcher.receiveOrder(orderQueue.poll());
                }
            }
        }, 0L, 500L);

        downLatch.await();
        ex.shutdown();
        StatisticUtil.calAvrgAndPrint();

    }


}
