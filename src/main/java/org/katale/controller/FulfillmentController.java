package org.katale.controller;

import org.katale.domains.Fulfillment;
import org.katale.service.FulfillmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/fulfillment")
public class FulfillmentController {

    @Autowired
    FulfillmentService fulfillmentService;

    @GetMapping(value = "/all")
    public List<Fulfillment> getAll(){
        return fulfillmentService.findAll();
    }

    @GetMapping(value = "/{id}")
    public Fulfillment getOne(@PathVariable long id){
        return fulfillmentService.findOne(id);
    }

    @GetMapping(value = "/order")
    public Fulfillment getById(@RequestParam long id){
        return fulfillmentService.getByOrder(id);
    }

}
