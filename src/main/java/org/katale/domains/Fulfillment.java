package org.katale.domains;

import org.katale.utilities.Status;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Fulfillment {

    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private long orderId;

    @Column
    private long fulfilledBy;

    @Column
    private Date dateFulfilled;

    @Column
    private Status status;

    public Fulfillment(){}

    public Fulfillment(long orderId,long empId, Date date,Status status){
        this.fulfilledBy=empId;
        this.dateFulfilled=date;
        this.status=status;
        this.orderId=orderId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getFulfilledBy() {
        return fulfilledBy;
    }

    public void setFulfilledBy(long fulfilledBy) {
        this.fulfilledBy = fulfilledBy;
    }

    public Date getDateFulfilled() {
        return dateFulfilled;
    }

    public void setDateFulfilled(Date dateFulfilled) {
        this.dateFulfilled = dateFulfilled;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
