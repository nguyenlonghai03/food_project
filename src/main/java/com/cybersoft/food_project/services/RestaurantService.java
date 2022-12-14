package com.cybersoft.food_project.services;

import com.cybersoft.food_project.dto.RestaurantDTO;
import com.cybersoft.food_project.dto.RestaurantDetailDTO;
import com.cybersoft.food_project.entity.RestaurantEntity;

import java.util.List;

public interface RestaurantService {
    List<RestaurantDTO> getRestaurant();
    RestaurantDetailDTO getDetailRestaurant(int id);

    void clearAllCache();
}
