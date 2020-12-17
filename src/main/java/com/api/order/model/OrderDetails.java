package com.api.order.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * OrderDetails - Order details for the customer creating the order.
 */

@Getter
@Setter
@Document(collection = "OrderManagement")
public class OrderDetails {

    @Id
    private String orderId;

    private int status;

    @NotEmpty(message = "Bad Request! Missing customer Id")
    private String customerId;

    private String correlationId;

    @NotNull(message = "Bad Request! Missing invoice amount")
    private double invoiceAmount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date orderTime;

    @NotEmpty(message = "Bad Request! Missing currency")
    private String currency;

    @NotNull(message = "Bad Request! Missing userDetails")
    private UserDetails userDetails;

    @NotNull(message = "Bad Request! Missing itemDetails")
    private List<ItemDetails> itemDetails;

    @NotNull(message = "Bad Request! Missing billingDetails")
    private BillingDetails billingDetails;

    @NotNull(message = "Bad Request! Missing shippingDetails")
    private ShippingDetails shippingDetails;


}
