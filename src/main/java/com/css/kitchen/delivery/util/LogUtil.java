package com.css.kitchen.delivery.util;

import com.css.kitchen.delivery.vo.Courier;
import com.css.kitchen.delivery.vo.Order;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
public class LogUtil {

    public static void orderLog(String action, Order order) {
        System.out.println(String.format("[%s] [[action]: %s] [orderId: %s] [orderName: %s] [prepareTime: %s] ",
                LocalDateTime.now(), action, order.getId(), order.getName(), order.getPrepTime()));
    }

    public static void courierLog(String action, Courier courier) {
        System.out.println(String.format("[%s] [[action]: %s] [courierId: %s] [orderId: %s] ",
                LocalDateTime.now(), action, courier.getId(), courier.getOrderId()));
    }

    public static void orderCourierLog(String action, Order order, Courier courier) {
        System.out.println(String.format("[%s] [[action]: %s]  [orderId: %s] [orderName: %s] [courierId: %s]",
                LocalDateTime.now(), action, order.getId(), order.getName(), courier.getId()));
    }
}
