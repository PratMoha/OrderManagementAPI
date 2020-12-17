package com.api.order.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * ShippingDetails - Shipping details for the customer creating the order.
 */

@Getter
@Setter
public class ShippingDetails {

    @NotEmpty(message = "Bad Request! Missing Shipping Id")
    private String id;


    @NotEmpty(message = "Bad Request! Missing salutation")
    private String salutation;


    @NotEmpty(message = "Bad Request! Missing firstName")
    private String firstName;


    @NotEmpty(message = "Bad Request! Missing lastName")
    private String lastName;


    @NotEmpty(message = "Bad Request! Missing street")
    private String street;


    @NotEmpty(message = "Bad Request! Missing zipCode")
    private String zipCode;


    @NotEmpty(message = "Bad Request! Missing city")
    private String city;

    @NotEmpty(message = "Bad Request! Missing mobile")
    private String mobile;

    @NotEmpty(message = "Bad Request! Missing email")
    private String email;

}
