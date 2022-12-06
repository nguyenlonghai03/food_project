package com.cybersoft.food_project.entity;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "restaurant")
public class RestaurantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "image")
    private String image;

    @OneToMany(mappedBy = "restaurant")
    private Set<RestaurantReviewEntity> restaurantReviews; // Vì bên kia là many nên ở đây là list


    @OneToMany(mappedBy = "restaurant")
    private Set<FoodEntity> foods;

    public Set<FoodEntity> getFoods() {
        return foods;
    }

    public void setFoods(Set<FoodEntity> foods) {
        this.foods = foods;
    }

    public Set<RestaurantReviewEntity> getRestaurantReviews() {
        return restaurantReviews;
    }

    public void setRestaurantReviews(Set<RestaurantReviewEntity> restaurantReviews) {
        this.restaurantReviews = restaurantReviews;
    }

    @Column(name="name")
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
