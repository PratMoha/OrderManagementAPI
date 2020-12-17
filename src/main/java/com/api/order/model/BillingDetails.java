package com.api.order.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * BillingDetails - Billing details for the customer creating the order.
 */

@Getter
@Setter
public class BillingDetails {

    @NotEmpty(message = "Bad Request! Missing article Id")
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

    @NotEmpty(message = "Bad Request! Missing paymentStatus")
    private String paymentStatus;

    @NotEmpty(message = "Bad Request! Missing paymentMethod")
    private String paymentMethod;

    @NotEmpty(message = "Bad Request! Missing mobile")
    private String mobile;

    @NotEmpty(message = "Bad Request! Missing email")
    private String email;
    
}
