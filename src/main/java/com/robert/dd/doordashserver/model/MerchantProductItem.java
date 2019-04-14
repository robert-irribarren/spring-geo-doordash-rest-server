package com.robert.dd.doordashserver.model;

import org.springframework.data.annotation.ReadOnlyProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "MerchantProductItem")
public class MerchantProductItem extends BaseModel {

    @ReadOnlyProperty
    @JoinColumn(name = "merchant_id", referencedColumnName = "id")
    private Merchant merchant;

    private String name;

    private String description;

    @Column(columnDefinition = "Decimal(12,2)")
    private double price;
}
