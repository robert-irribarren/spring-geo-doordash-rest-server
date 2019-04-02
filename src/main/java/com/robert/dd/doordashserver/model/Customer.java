package com.robert.dd.doordashserver.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="Customer")
public class Customer extends BaseModel {
    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="phone")
    private String phone;

    @Column(name="email")
    private String email;

    @Column(name = "verified_email")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean verifiedEmail;

    @Column(name = "verified_phone")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean verifiedPhone;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "CustomerAddress",
            joinColumns = { @JoinColumn(name = "customer_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "address_id", referencedColumnName = "id") })
    private List<Address> addresses;

    public String getFirstName(){
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isVerifiedEmail() {
        return verifiedEmail;
    }

    public void setVerifiedEmail(boolean verifiedEmail) {
        this.verifiedEmail = verifiedEmail;
    }

    public boolean isVerifiedPhone() {
        return verifiedPhone;
    }

    public void setVerifiedPhone(boolean verifiedPhone) {
        this.verifiedPhone = verifiedPhone;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }
}