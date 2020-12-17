package com.api.order.repository;

import com.api.order.model.OrderDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderManagementRepository extends MongoRepository<OrderDetails, String> {

}
