package com.css.kitchen.delivery.vo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PickUpResult {
    private Courier courier;
    private Order order;
    private LocalDateTime pickupTime;


    public PickUpResult(Courier courier, Order order, LocalDateTime pickupTime) {
        this.courier = courier;
        this.order = order;
        this.pickupTime = pickupTime;
    }
}
