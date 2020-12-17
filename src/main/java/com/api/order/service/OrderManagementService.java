package com.api.order.service;


import com.api.order.exceptions.ValidationException;
import com.api.order.model.OrderDetails;
import com.api.order.model.OrderManagementConstants;
import com.api.order.repository.OrderManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@Service
public class OrderManagementService {

    @Autowired
    OrderManagementRepository orderManagementRepository;

    /**
     * Fetching an order
     *
     * @param orderId unique representation of order
     * @return OrderDetails return the order object with the details based on the orderId
     */
    public OrderDetails getSingleOrder(String orderId) {

         Optional<OrderDetails> orderDetails = orderManagementRepository.findById(orderId);
         if(orderDetails.isPresent()){
             return orderDetails.get();
         }else{
             throw new ValidationException(OrderManagementConstants.NOT_FOUND);
         }

    }

    /**
     * Creating an order
     *
     * @param orderDetails Object taking input of the order
     * @return String returns the order status with the created orderId
     */
    public String createOrder(OrderDetails orderDetails){
        final String uuid = UUID.randomUUID().toString();
        orderDetails.setOrderId(uuid);
        orderManagementRepository.save(orderDetails);
        return uuid;
    }

    /**
     * Updating an order
     *
     * @param orderDetails Object taking input of the order
     * @param orderId  unique representation of order
     * @return String returns the order status with the created orderId
     */
    public OrderDetails updateOrder(OrderDetails orderDetails, String orderId) {
        orderDetails.setOrderId(orderId);
        orderManagementRepository.save(orderDetails);
        if (orderManagementRepository.existsById(orderId)) {
            return orderManagementRepository.save(orderDetails);
        }else{
            throw new ValidationException(OrderManagementConstants.NOT_FOUND);
        }
    }

    /**
     * Deleting an order
     *
     * @param orderId  unique representation of order
     * @return String returns the order status with the created orderId
     */
    public String deleteOrder(String orderId) {
        orderManagementRepository.delete(getSingleOrder(orderId));
        return orderId;
    }
}
