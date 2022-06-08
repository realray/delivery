package com.css.kitchen.delivery.util;

import com.css.kitchen.delivery.vo.PickUpResult;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * a util used to statistic:
 * Average food wait time (milliseconds) between order ready and pickup
 * Average courier wait time (milliseconds) between arrival and order pickup
 */
public class StatisticUtil {

    /**
     * Suppose on multi thread dispatchers, a synchronized class will be safe
     */
    private static final AtomicLong totalOrderPkTime = new AtomicLong(0);
    private static final AtomicLong totalCourierWtTime = new AtomicLong(0);
    private static final AtomicInteger orderCounts = new AtomicInteger(0);

    public static void addSample(List<PickUpResult> pickUpResults) {
        if (pickUpResults == null || pickUpResults.size() == 0) {
            return;
        }
        orderCounts.getAndAdd(pickUpResults.size());

        for (PickUpResult pickUpResult : pickUpResults) {

            totalOrderPkTime.getAndAdd(pickUpResult.getOrder().getPreparedTime()
                    .until(pickUpResult.getPickupTime(), ChronoUnit.SECONDS));

            totalCourierWtTime.getAndAdd(pickUpResult.getCourier().getArrivalTime()
                    .until(pickUpResult.getPickupTime(), ChronoUnit.SECONDS));
        }
    }

    public static int getSolvedOrderSize() {
        return orderCounts.get();
    }

    public static void calAvrgAndPrint() {
        double averagePkTime = (double) totalOrderPkTime.get() / orderCounts.get();
        System.out.println(String.format("[[order average pick Time]]:  %.2f S", averagePkTime));

        double averageWaitTime = (double) totalCourierWtTime.get() / orderCounts.get();

        System.out.println(String.format("[[courier average wait Time]]: %.2f S", averageWaitTime));

    }


}
