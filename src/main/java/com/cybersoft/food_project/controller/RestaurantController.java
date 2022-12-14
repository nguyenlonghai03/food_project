package com.cybersoft.food_project.controller;

import com.cybersoft.food_project.dto.RestaurantDTO;
import com.cybersoft.food_project.dto.RestaurantDetailDTO;
import com.cybersoft.food_project.entity.RestaurantEntity;
import com.cybersoft.food_project.services.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController()
@RequestMapping("/restaurant")
public class RestaurantController {

    @Autowired
    RestaurantService restaurantService;

    @GetMapping("")
    public ResponseEntity<?> getRestaurant() {
        List<RestaurantDTO> restaurants = restaurantService.getRestaurant();
        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    @GetMapping("/clear-cache")
    public ResponseEntity<?> clearCacheRestaurant() {
        restaurantService.clearAllCache();
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @GetMapping("/{id}") // id của restaurant lấy detail
    public ResponseEntity<?> getDetailRestaurant(@PathVariable("id") int id) {
        RestaurantDetailDTO restaurantDTO = restaurantService.getDetailRestaurant(id);

        return new ResponseEntity<>(restaurantDTO, HttpStatus.OK);
    }

}
