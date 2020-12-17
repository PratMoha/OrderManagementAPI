package com.api.order.controller;


import com.api.order.OrderManagementApplication;
import com.api.order.model.*;
import com.api.order.service.OrderManagementService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = OrderManagementApplication.class)
@WebAppConfiguration
public class OrderManagementControllerTest {

    @Autowired
    WebApplicationContext webApplicationContext;

    protected MockMvc mvc;

    @MockBean
    OrderManagementService orderManagementService;



    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
    protected <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }

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
    OrderDetails orderDetails = new OrderDetails();

    @Before
    public void init() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();


        ShippingDetails shippingDetails = new ShippingDetails();
        BillingDetails billingDetails = new BillingDetails();
        UserDetails userDetails = new UserDetails();
        ItemDetails itemDetails = new ItemDetails();
        orderDetails.setOrderId("988281ba-47e2-47cd-9f3d-f7cedf1527c0");
        orderDetails.setCustomerId("1");
        orderDetails.setCorrelationId("1");
        orderDetails.setInvoiceAmount(162.22);
        orderDetails.setOrderTime(StringToDate("2020-12-16 17:03:00"));
        orderDetails.setCurrency("EUR");

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
    }

    @Test
    public void createOrder_happy() throws Exception {
        when(orderManagementService.createOrder(any())).thenReturn("123-42-22-22");

        String uri = "/orders";
        String inputJson = mapToJson(orderDetails);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        String response = "{\"orderId\":\"123-42-22-22\",\"message\":\"Success ! Order Created\",\"statusCode\":201}";
        assertEquals(status,201);
        assertEquals(content,response);
    }

    @Test
    public void createOrder_unhappy() throws Exception {
        when(orderManagementService.createOrder(any())).thenReturn("123-42-22-22");
        orderDetails.setCurrency("");
        String uri = "/orders";
        String inputJson = mapToJson(orderDetails);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        String response = "{\"message\":\"Bad Request! Missing currency\",\"statusCode\":\"400\"}";
        assertEquals(status,400);
        assertEquals(content,response);
    }

    @Test
    public void getOrder() throws Exception {
        orderDetails.setOrderId("988281ba-47e2-47cd-9f3d-f7cedf1527c0");
        when(orderManagementService.getSingleOrder(any())).thenReturn(orderDetails);

        String uri = "/orders/988281ba-47e2-47cd-9f3d-f7cedf1527c0";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        String response = "{\"orderId\":\"988281ba-47e2-47cd-9f3d-f7cedf1527c0\",\"status\":200,\"customerId\":\"1\",\"correlationId\":\"1\",\"invoiceAmount\":162.22,\"orderTime\":\"2020-12-16 16:03:00\",\"currency\":\"EUR\",\"userDetails\":{\"id\":\"1\",\"salutation\":null,\"firstName\":\"Pratyush\",\"lastName\":\"Mohapatra\",\"mobile\":\"+31121212121\",\"email\":\"abc@gmail.com\"},\"itemDetails\":[{\"itemId\":1,\"articleId\":\"122\",\"price\":35.99,\"quantity\":1,\"articleName\":\"Item X\"}],\"billingDetails\":{\"id\":\"1\",\"salutation\":\"Mr.\",\"firstName\":\"Pratyush\",\"lastName\":\"Mohapatra\",\"street\":\"111 XYZ\",\"zipCode\":\"751006\",\"city\":\"Amsterdam\",\"paymentStatus\":null,\"paymentMethod\":null,\"mobile\":\"+31121212121\",\"email\":\"abc@gmail.com\"},\"shippingDetails\":{\"id\":\"1\",\"salutation\":\"Mr.\",\"firstName\":\"Pratyush\",\"lastName\":\"Mohapatra\",\"street\":\"111 XYZ\",\"zipCode\":\"751006\",\"city\":\"Amsterdam\",\"mobile\":\"+31121212121\",\"email\":\"abc@gmail.com\"}}";
        assertEquals(status,200);
        assertEquals(content,response);
    }
    @Test
    public void getOrder_unhappy() throws Exception {
        orderDetails.setOrderId("47e2-47cd-9f3d-f7cedf1527c0");
        when(orderManagementService.getSingleOrder(any())).thenReturn(orderDetails);

        String uri = "/orders/47e2-47cd-9f3d-f7cedf1527c0";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        String response = "{\"message\":\"retrieveOrder.orderId: Bad Request ! Invalid OrderId\",\"statusCode\":\"400\"}";
        assertEquals(status,400);
        assertEquals(content,response);
    }

    @Test
    public void updateOrder() throws Exception {
        orderDetails.setOrderId("988281ba-47e2-47cd-9f3d-f7cedf1527c0");
        orderDetails.setInvoiceAmount(162.99);
        String inputJson = mapToJson(orderDetails);
        when(orderManagementService.updateOrder(any(),any())).thenReturn(orderDetails);

        String uri = "/orders/988281ba-47e2-47cd-9f3d-f7cedf1527c0";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        String response = "{\"orderId\":\"988281ba-47e2-47cd-9f3d-f7cedf1527c0\",\"status\":200,\"customerId\":\"1\",\"correlationId\":\"1\",\"invoiceAmount\":162.99,\"orderTime\":\"2020-12-16 16:03:00\",\"currency\":\"EUR\",\"userDetails\":{\"id\":\"1\",\"salutation\":null,\"firstName\":\"Pratyush\",\"lastName\":\"Mohapatra\",\"mobile\":\"+31121212121\",\"email\":\"abc@gmail.com\"},\"itemDetails\":[{\"itemId\":1,\"articleId\":\"122\",\"price\":35.99,\"quantity\":1,\"articleName\":\"Item X\"}],\"billingDetails\":{\"id\":\"1\",\"salutation\":\"Mr.\",\"firstName\":\"Pratyush\",\"lastName\":\"Mohapatra\",\"street\":\"111 XYZ\",\"zipCode\":\"751006\",\"city\":\"Amsterdam\",\"paymentStatus\":null,\"paymentMethod\":null,\"mobile\":\"+31121212121\",\"email\":\"abc@gmail.com\"},\"shippingDetails\":{\"id\":\"1\",\"salutation\":\"Mr.\",\"firstName\":\"Pratyush\",\"lastName\":\"Mohapatra\",\"street\":\"111 XYZ\",\"zipCode\":\"751006\",\"city\":\"Amsterdam\",\"mobile\":\"+31121212121\",\"email\":\"abc@gmail.com\"}}";
        assertEquals(status,200);
        assertEquals(content,response);
    }

    @Test
    public void updateOrder_unhappy() throws Exception {
        orderDetails.setOrderId("47e2-47cd-9f3d-f7cedf1527c0");
        orderDetails.setInvoiceAmount(162.99);
        when(orderManagementService.getSingleOrder(any())).thenReturn(orderDetails);

        String uri = "/orders/47e2-47cd-9f3d-f7cedf1527c0";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        String response = "{\"message\":\"retrieveOrder.orderId: Bad Request ! Invalid OrderId\",\"statusCode\":\"400\"}";
        assertEquals(status,400);
        assertEquals(content,response);
    }

}
