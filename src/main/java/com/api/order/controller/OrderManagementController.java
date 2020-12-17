package com.api.order.controller;


import com.api.order.model.*;
import com.api.order.service.OrderManagementService;
import com.google.api.client.http.HttpStatusCodes;
import com.sun.org.apache.xpath.internal.operations.Or;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * <h1>Order Management API</h1>
 * The Code is for Order Management API for creating, updating, fetching and deleting orders.
 * <p>
 * This class is the controller and acts as a routing of the
 * requests
 * @author  Pratyush
 * @version 1.0
 * @since   2020-12-16
 */

@RestController
@Validated
@AllArgsConstructor
public class OrderManagementController {


    OrderManagementService orderManagementService;

    /**
     * Lists complete details of orders at any given time based on the orderId
     *
     * @param orderId String value provided for fetching the order details.
     * @return OrderDetails provides the details of complete orders.
     */
    @GetMapping("/orders/{orderId}")
    public ResponseEntity<Object> retrieveOrder(@PathVariable(OrderManagementConstants.ORDER_ID) @NotNull @Pattern(regexp="[\\w]{8}-[\\w]{4}-[\\w]{4}-[\\w]{4}-[\\w]{12}", message=OrderManagementConstants.INVALID_ORDER) String orderId){
        OrderDetails orderDetails;
        orderDetails = orderManagementService.getSingleOrder(orderId);
        orderDetails.setOrderId(orderId);
        orderDetails.setStatus(200);
        return ResponseEntity.status(HttpStatusCodes.STATUS_CODE_OK).body(orderDetails);
    }

    /**
     * Lists complete detail of items in an order at any given time based on the orderId
     *
     * @param orderId String value provided for fetching the order details.
     * @return OrderDetails provides the details of complete orders.
     */
    @GetMapping("/orders/{orderId}/items")
    public ResponseEntity<Object> retrieveItems(@PathVariable(OrderManagementConstants.ORDER_ID) @NotNull(message = OrderManagementConstants.MISSING_ORDER_ID) @Pattern(regexp="[\\w]{8}-[\\w]{4}-[\\w]{4}-[\\w]{4}-[\\w]{12}", message=OrderManagementConstants.INVALID_ORDER) String orderId){

        List<ItemDetails> orderDetails;
        orderDetails = orderManagementService.getSingleOrder(orderId).getItemDetails();
        return ResponseEntity.status(HttpStatusCodes.STATUS_CODE_OK).body(orderDetails);
    }

    /**
     * Lists billing details of items in an order at any given time based on the orderId
     *
     * @param orderId String value provided for fetching the order details.
     * @return OrderDetails provides the details of complete orders.
     */
    @GetMapping("/orders/{orderId}/billingDetails")
    public ResponseEntity<Object> billingItems(@PathVariable(OrderManagementConstants.ORDER_ID) @NotNull(message = OrderManagementConstants.MISSING_ORDER_ID) @Pattern(regexp="[\\w]{8}-[\\w]{4}-[\\w]{4}-[\\w]{4}-[\\w]{12}", message=OrderManagementConstants.INVALID_ORDER) String orderId){

        BillingDetails billingDetails;
        billingDetails = orderManagementService.getSingleOrder(orderId).getBillingDetails();
        return ResponseEntity.status(HttpStatusCodes.STATUS_CODE_OK).body(billingDetails);
    }

    /**
     * Lists shipping details of items in an orders at any given time based on the orderId
     *
     * @param orderId String value provided for fetching the order details.
     * @return OrderDetails provides the details of complete orders.
     */
    @GetMapping("/orders/{orderId}/shippingDetails")
    public ResponseEntity<Object> shippingDetails(@PathVariable(OrderManagementConstants.ORDER_ID) @NotNull(message = OrderManagementConstants.MISSING_ORDER_ID) @Pattern(regexp="[\\w]{8}-[\\w]{4}-[\\w]{4}-[\\w]{4}-[\\w]{12}", message=OrderManagementConstants.INVALID_ORDER) String orderId){

        ShippingDetails shippingDetails;
        shippingDetails = orderManagementService.getSingleOrder(orderId).getShippingDetails();
        return ResponseEntity.status(HttpStatusCodes.STATUS_CODE_OK).body(shippingDetails);
    }


    /**
     * Creating an order
     *
     * @param orders Object taking input of the order
     * @return OrderResponse return the order status with the created orderId
     */
    @PostMapping("/orders")
    @ResponseBody
    public ResponseEntity<Object> createOrder(@Valid @RequestBody OrderDetails orders) {

        String orderId = orderManagementService.createOrder(orders);
        OrderResponse orderResponse= new OrderResponse();
        orderResponse.setOrderId(orderId);
        orderResponse.setMessage(OrderManagementConstants.SUCCESS_ORDER_CREATED);
        orderResponse.setStatusCode(201);
        return ResponseEntity.status(HttpStatusCodes.STATUS_CODE_CREATED).body(orderResponse);

    }

    /**
     * Updates the order placed by a customer
     *
     * @param orderId String value provided for fetching the order details.
     * @return OrderDetails provides details of an updated order.
     */
   @PutMapping("/orders/{orderId}")
    public  ResponseEntity<Object> updateOrder(@Valid @RequestBody OrderDetails orders, @PathVariable(OrderManagementConstants.ORDER_ID) @NotNull(message = OrderManagementConstants.MISSING_ORDER_ID) @Pattern(regexp="[\\w]{8}-[\\w]{4}-[\\w]{4}-[\\w]{4}-[\\w]{12}", message=OrderManagementConstants.INVALID_ORDER) String orderId){

        orders = orderManagementService.updateOrder(orders,orderId);
        orders.setStatus(200);
        return ResponseEntity.status(HttpStatusCodes.STATUS_CODE_OK).body(orders);
    }

    /**
     * Delete the order placed by a customer
     *
     * @param orderId String value provided for fetching the order details.
     * @return OrderDetails provides details of an updated order.
     */
    @DeleteMapping("/orders/{orderId}")
    public  ResponseEntity<Object> deleteOrder(@Valid @RequestBody OrderDetails orders, @PathVariable(OrderManagementConstants.ORDER_ID) @NotNull(message = OrderManagementConstants.MISSING_ORDER_ID) @Pattern(regexp="[\\w]{8}-[\\w]{4}-[\\w]{4}-[\\w]{4}-[\\w]{12}", message=OrderManagementConstants.INVALID_ORDER) String orderId){

        orderManagementService.deleteOrder(orderId);
        return ResponseEntity.status(HttpStatusCodes.STATUS_CODE_NO_CONTENT).body("");
    }

}
