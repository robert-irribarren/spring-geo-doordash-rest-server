package com.robert.dd.doordashserver.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="Merchant")
public class Merchant extends BaseModel {

    private String phone;

    @Column(name="banner_image_url")
    private String bannerImageUrl;
    private String name;
    private long distance;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "MerchantCategoryMap",
            joinColumns = { @JoinColumn(name = "merchant_id", referencedColumnName = "id")},
            inverseJoinColumns = { @JoinColumn(name="merchantcategory_id", referencedColumnName = "id")})
    private List<MerchantCategory> categories;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBannerImageUrl() {
        return bannerImageUrl;
    }

    public void setBannerImageUrl(String bannerImageUrl) {
        this.bannerImageUrl = bannerImageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public long getDistance() {
        return distance;
    }

    public void setDistance(long distance) {
        this.distance = distance;
    }

    public List<MerchantCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<MerchantCategory> categories) {
        this.categories = categories;
    }
}
