package com.cybersoft.food_project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity(name = "restaurant_review")
public class RestaurantReviewEntity { // Cứ giữ khóa ngoại thì là nhiều
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "content")
    private String content;

    @Column(name = "rate")
    private float rate;

    @ManyToOne()
    @JoinColumn(name = "id_restaurant") // chỉ định id khóa ngoại
    //@JsonIgnore // Tranhs lỗi lặp vô tận
    private RestaurantEntity restaurant;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public RestaurantEntity getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantEntity restaurant) {
        this.restaurant = restaurant;
    }
}
