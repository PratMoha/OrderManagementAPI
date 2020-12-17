package com.api.order.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderResponse {

    private String orderId;

    private String message;

    private int statusCode;

}
