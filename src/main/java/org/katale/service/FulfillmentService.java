package org.katale.service;

import org.katale.domains.Fulfillment;
import org.katale.service.generic.GenericService;

public interface FulfillmentService extends GenericService<Fulfillment> {

    Fulfillment getByOrder(long id);

}
