package org.katale.service.impl;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.katale.BaseTest;
import org.katale.domains.Fulfillment;
import org.katale.exceptions.FulfillmentException;
import org.katale.service.FulfillmentService;
import org.katale.utilities.Status;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class FulfillmentServiceTest extends BaseTest {

    public FulfillmentServiceTest(){
        super();
    }

    @Autowired
    FulfillmentService fulfillmentService;

    private Fulfillment fulfillment;

    @Before
    public void setUp() throws Exception {

        fulfillment=new Fulfillment();
        fulfillment.setOrderId(1234);
        fulfillment.setFulfilledBy(567);
        fulfillment.setDateFulfilled(new Date());
        fulfillment.setStatus(Status.PENDING);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void save() throws FulfillmentException {
        fulfillmentService.save(fulfillment);
    }

    @Test
    public void findOne() {
        Assert.assertNotNull(fulfillmentService.findOne(1));
    }

    @Test
    public void update() {
        fulfillment.setId(1);
        fulfillment.setFulfilledBy(543);
        Assert.assertNotNull(fulfillmentService.update(fulfillment));
    }

    @Test
    public void delete() {
        fulfillment=fulfillmentService.findOne(2);
        fulfillmentService.delete(fulfillment);
    }

    @Test
    public void findAll() {
    }

    @Test
    public void exists() {
        fulfillment.setId(1);
        Assert.assertTrue(fulfillmentService.exists(fulfillment));
    }

    @Test
    public void getByOrderid(){
      //  fulfillment.setOrderId(8374234);
        Assert.assertNull(fulfillmentService.getByOrder(8374234));
    }
}