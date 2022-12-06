package com.cybersoft.food_project.entity;

import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity(name="user_detail") // Đặt tên giống tên bảng
public class UserDetailEntity {

    @Id // của Javaxpersistance
    @Column(name = "id_user")
    private int idUser;

    @Column(name="address")
    private String address;

    @Column(name = "mobile_phone")
    private String mobilePhone;

    @Column(name = "state")
    private String state;

    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;

    @OneToOne()
    @JoinColumn(name = "id_user") // Thằng nào nắm khóa ngoại sẽ chứa join column
    private UserEntity user;

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
