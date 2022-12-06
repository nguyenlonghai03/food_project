package com.cybersoft.food_project.entity;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "status")
public class StatusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "status")
    private Set<OrderStatusEntity> orderStatus;

    public Set<OrderStatusEntity> getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Set<OrderStatusEntity> orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
