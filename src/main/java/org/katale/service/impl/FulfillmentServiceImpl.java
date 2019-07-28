package org.katale.service.impl;

import org.katale.domains.Fulfillment;
import org.katale.repository.FulfillmentRepo;
import org.katale.service.FulfillmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class FulfillmentServiceImpl implements FulfillmentService {

    @Autowired
    FulfillmentRepo fulfillmentRepo;

    public Fulfillment save(Fulfillment fulfillment) {
        return fulfillmentRepo.save(fulfillment);
    }

    public Fulfillment findOne(long id) {
        return fulfillmentRepo.getByID(id);
    }

    public Fulfillment update(Fulfillment fulfillment) {
        return fulfillmentRepo.update(fulfillment);
    }

    public void delete(Fulfillment fulfillment) {
        fulfillmentRepo.delete(fulfillment);
    }

    public List<Fulfillment> findAll() {
        return fulfillmentRepo.getAll();
    }

    public boolean exists(Fulfillment fulfillment) {
        return fulfillmentRepo.exists(fulfillment);
    }

    @Override
    public Fulfillment getByOrder(long id) { return fulfillmentRepo.getByOrder(id); }
}