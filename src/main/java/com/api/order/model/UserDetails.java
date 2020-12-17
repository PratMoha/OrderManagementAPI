package com.api.order.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * UserDetails - User details for the customer creating the order.
 */

@Getter
@Setter
public class UserDetails {

    @NotEmpty(message = "Bad Request! Missing article Id")
    private String id;

    @NotEmpty(message = "Bad Request! Missing salutation")
    private String salutation;

    private String firstName;

    private String lastName;

    private String mobile;

    private String email;
    
}
