package com.robert.dd.doordashserver.model;

import com.vividsolutions.jts.geom.Point;
import org.springframework.data.annotation.ReadOnlyProperty;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "CustomerOrder")
public class CustomerOrder extends BaseModel {

    @ReadOnlyProperty
    @OneToOne
    @JoinColumn(name = "merchant_id", referencedColumnName = "id")
    private Merchant merchant;

    @ReadOnlyProperty
    @OneToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "CustomerOrderItem",
            joinColumns = { @JoinColumn(name = "order_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "merchantproduct_id", referencedColumnName = "id")} )
    private List<MerchantProduct> orderItems;


    @Column(name="delivery_address_1",nullable = false)
    private String deliveryAddress1;
    @Column(name="delivery_address_2")
    private String deliveryAddress2;
    @Column(name="delivery_address_3")
    private String deliveryAddress3;
    @Column(name="delivery_city")
    private String deliveryCity;
    @Column(name="delivery_state")
    private String deliveryState;

    @Column(name="delivery_location",nullable = false)
    private Point deliveryLocation;

    @Column(name="delivery_additional_info")
    private String deliveryAdditionalInfo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="order_time",nullable = false)
    private Date orderTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="food_ready_time")
    private Date foodReadyTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="actual_delivery_time")
    private Date actualDeliveryTime;

    @Column(columnDefinition="Decimal(12,2)")
    private double price;

    @Column(columnDefinition="Decimal(12,2)")
    private double discount;

    @Column(name="final_price",columnDefinition = "Decimal(12,2)")
    private double finalPrice;

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getDeliveryAddress1() {
        return deliveryAddress1;
    }

    public void setDeliveryAddress1(String deliveryAddress1) {
        this.deliveryAddress1 = deliveryAddress1;
    }

    public String getDeliveryAddress2() {
        return deliveryAddress2;
    }

    public void setDeliveryAddress2(String deliveryAddress2) {
        this.deliveryAddress2 = deliveryAddress2;
    }

    public String getDeliveryAddress3() {
        return deliveryAddress3;
    }

    public void setDeliveryAddress3(String deliveryAddress3) {
        this.deliveryAddress3 = deliveryAddress3;
    }

    public String getDeliveryCity() {
        return deliveryCity;
    }

    public void setDeliveryCity(String deliveryCity) {
        this.deliveryCity = deliveryCity;
    }

    public String getDeliveryState() {
        return deliveryState;
    }

    public void setDeliveryState(String deliveryState) {
        this.deliveryState = deliveryState;
    }

    public Point getDeliveryLocation() {
        return deliveryLocation;
    }

    public void setDeliveryLocation(Point deliveryLocation) {
        this.deliveryLocation = deliveryLocation;
    }

    public String getDeliveryAdditionalInfo() {
        return deliveryAdditionalInfo;
    }

    public void setDeliveryAdditionalInfo(String deliveryAdditionalInfo) {
        this.deliveryAdditionalInfo = deliveryAdditionalInfo;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Date getFoodReadyTime() {
        return foodReadyTime;
    }

    public void setFoodReady(Date foodReady) {
        this.foodReadyTime = foodReadyTime;
    }

    public Date getActualDeliveryTime() {
        return actualDeliveryTime;
    }

    public void setActualDeliveryTime(Date actualDeliveryTime) {
        this.actualDeliveryTime = actualDeliveryTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }
}
