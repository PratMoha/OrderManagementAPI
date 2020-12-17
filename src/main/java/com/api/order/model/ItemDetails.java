package com.api.order.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * ItemDetails - Item details for the customer creating the order.
 */

@Getter
@Setter
public class ItemDetails {

    @NotNull(message = "Bad Request! Missing item Id")
    private int itemId;

    @NotEmpty(message = "Bad Request! Missing article Id")
    private String articleId;

    @NotNull(message = "Bad Request! Missing article price")
    private double price;

    @NotNull(message = "Bad Request! Missing quantity")
    private int quantity;

    @NotEmpty(message = "Bad Request! Missing article name")
    private String articleName;
    
}
