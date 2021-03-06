package org.katale.service.generic;

import org.katale.domains.Fulfillment;
import org.katale.exceptions.FulfillmentException;

import java.util.List;

public interface GenericService<T> {

    T save(T t) throws FulfillmentException;
    T findOne(long id);
    T update(T t);
    void delete(T t);
    List<T> findAll();
    boolean exists(Fulfillment fulfillment);

}
