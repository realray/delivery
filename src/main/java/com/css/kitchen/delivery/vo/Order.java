package com.css.kitchen.delivery.vo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class Order {

    private String id;
    private String name;
    private int prepTime;
    private LocalDateTime preparedTime;

}
