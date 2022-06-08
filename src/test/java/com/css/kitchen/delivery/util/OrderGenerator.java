package com.css.kitchen.delivery.util;

import com.css.kitchen.delivery.vo.Order;
import lombok.SneakyThrows;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class OrderGenerator {

    @SneakyThrows
    public static Queue<Order> getAllOrder() {

        String filePath = "dispatch_orders.json";
        String jsonContent = FileUtil.ReadFile(filePath);
        List<Order> list = JsonUtil.toList(jsonContent, Order.class);
        return new LinkedList<>(list);
    }



}
