package com.api.order.service;

import com.api.order.model.*;
import com.api.order.repository.OrderManagementRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataMongoTest
public class OrderManagementServiceTest {

    @Autowired
    OrderManagementRepository orderManagementRepository;



    public Date StringToDate(String s){

        Date result = null;
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            result  = dateFormat.parse(s);
        }catch(ParseException e){
            e.printStackTrace();

        }
        return result ;
    }

    @Before
    public void setUp() {

        List<OrderDetails> orderDetailsList = new ArrayList<>();
        OrderDetails orderDetails = new OrderDetails();


        ShippingDetails shippingDetails = new ShippingDetails();
        BillingDetails billingDetails = new BillingDetails();
        UserDetails userDetails = new UserDetails();
        ItemDetails itemDetails = new ItemDetails();

        orderDetails.setCustomerId("1");
        orderDetails.setCorrelationId("1");
        orderDetails.setInvoiceAmount(162.22);
        orderDetails.setOrderTime(StringToDate("2020-12-16 17:03:00"));
        orderDetails.setCurrency("EUR");
        orderDetails.setOrderId("123-456-789");

        shippingDetails.setCity("Amsterdam");
        shippingDetails.setEmail("abc@gmail.com");
        shippingDetails.setSalutation("Mr.");
        shippingDetails.setFirstName("Pratyush");
        shippingDetails.setLastName("Mohapatra");
        shippingDetails.setMobile("+31121212121");
        shippingDetails.setId("1");
        shippingDetails.setStreet("111 XYZ");
        shippingDetails.setZipCode("751006");

        billingDetails.setCity("Amsterdam");
        billingDetails.setEmail("abc@gmail.com");
        billingDetails.setSalutation("Mr.");
        billingDetails.setFirstName("Pratyush");
        billingDetails.setLastName("Mohapatra");
        billingDetails.setMobile("+31121212121");
        billingDetails.setId("1");
        billingDetails.setStreet("111 XYZ");
        billingDetails.setZipCode("751006");

        userDetails.setEmail("abc@gmail.com");
        userDetails.setId("1");
        userDetails.setMobile("+31121212121");
        userDetails.setFirstName("Pratyush");
        userDetails.setLastName("Mohapatra");

        itemDetails.setItemId(1);
        itemDetails.setArticleId("122");
        itemDetails.setArticleName("Item X");
        itemDetails.setQuantity(1);
        itemDetails.setPrice(35.99);
        List<ItemDetails> itemList = new ArrayList<>();
        itemList.add(itemDetails);

        orderDetails.setShippingDetails(shippingDetails);
        orderDetails.setBillingDetails(billingDetails);
        orderDetails.setUserDetails(userDetails);
        orderDetails.setItemDetails(itemList);

        orderDetailsList.add(orderDetails);
        orderManagementRepository.saveAll(orderDetailsList);
    }

    @Test
    public void getOrderById() {
        Optional<OrderDetails> orderDetails = orderManagementRepository.findById("123-456-789");

        assertThat(orderDetails.isPresent());
        assertThat(orderDetails.get().getOrderId()).isEqualTo("123-456-789");

    }

    @Test
    public void deleteOrderById() {
        orderManagementRepository.deleteById("123-456-789");

        assertThat(!orderManagementRepository.existsById("123-456-789"));
    }


}
