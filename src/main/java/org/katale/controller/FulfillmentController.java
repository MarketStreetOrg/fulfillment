package org.katale.controller;

import org.katale.domains.Fulfillment;
import org.katale.service.FulfillmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/fulfillment")
@CrossOrigin(origins = "http://localhost:8080")
public class FulfillmentController {

    @Autowired
    FulfillmentService fulfillmentService;

    @GetMapping(value = "/all")
    public @ResponseBody List<Fulfillment> getAll(){
        return fulfillmentService.findAll();
    }

    @GetMapping(value = "/{id}")
    public @ResponseBody Fulfillment getOne(@PathVariable long id){
        return fulfillmentService.findOne(id);
    }

    @GetMapping(value = "/order")
    public @ResponseBody Fulfillment getByOrder(@RequestParam long id){
        return fulfillmentService.getByOrder(id);
    }

}
