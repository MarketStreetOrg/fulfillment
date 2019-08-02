package org.katale.repository.impl;

import org.katale.domains.Fulfillment;
import org.katale.repository.FulfillmentRepo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FulfillmentRepoImpl implements FulfillmentRepo {

    List<Fulfillment> fulfillments = new ArrayList<Fulfillment>();

    @PersistenceContext
    EntityManager entityManager;

    public List<Fulfillment> getAll() {
        Query query = entityManager.createQuery("select fulfillment from Fulfillment fulfillment");
        return query.getResultList();
    }

    public Fulfillment getByID(long id) {
        return entityManager.find(Fulfillment.class, id);
    }

    public Fulfillment getByName(String name) {
        return null;
    }

    public Fulfillment save(Fulfillment fulfillment) {
        entityManager.persist(fulfillment);
        return fulfillment;
    }

    public Fulfillment update(Fulfillment fulfillment) {
        return entityManager.merge(fulfillment);
    }

    public void delete(long id) { }

    public void delete(String param) { }

    public void delete(Fulfillment fulfillment) {
        entityManager
                .createQuery("delete from Fulfillment f where f.id=:id")
                .setParameter("id", fulfillment.getId())
                .executeUpdate();
    }

    public Boolean exists(Fulfillment fulfillment) {
        fulfillment = entityManager.find(Fulfillment.class, fulfillment.getId());
        return fulfillment != null;
    }

    @Override
    public Fulfillment getByOrder(long id) {
        List<Fulfillment> ls = entityManager.createQuery("select f from Fulfillment f where f.orderId=:id")
                .setParameter("id", id)
                .getResultList();

        return ls.isEmpty() ? null : ls.get(0);
    }
}
