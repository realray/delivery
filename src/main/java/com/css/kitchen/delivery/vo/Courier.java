package com.css.kitchen.delivery.vo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Courier {

    private String id;
    /**
     * if the courier need special order ,the order id can not null
     */
    private String orderId;

    private LocalDateTime arrivalTime;
}
