package com.robert.dd.doordashserver.model;

import org.springframework.data.annotation.ReadOnlyProperty;

import javax.persistence.*;

@Entity
@Table(name = "MerchantProduct")
public class MerchantProduct extends BaseModel {

    /*@ReadOnlyProperty
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id", referencedColumnName = "id")
    private Merchant merchant;*/


    @Column(name="merchant_id")
    private String merchantId;

    private String name;

    /** Appetizer, Entree, Dessert **/
    @Column(name="group_name")
    private String groupName;

    private String description;

    @Column(columnDefinition = "Decimal(12,2)")
    private double price;

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
