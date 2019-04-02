package com.robert.dd.doordashserver.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "CustomerAddress")
public class CustomerAddress extends BaseModel {

    @Column(name="customer_id")
    private String customerId;


    @Column(name="address_id")
    private String addressId;
}
