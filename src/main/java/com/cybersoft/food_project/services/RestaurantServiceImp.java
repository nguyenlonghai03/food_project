package com.cybersoft.food_project.services;

import com.cybersoft.food_project.dto.RestaurantDTO;
import com.cybersoft.food_project.dto.RestaurantDetailDTO;
import com.cybersoft.food_project.entity.FoodEntity;
import com.cybersoft.food_project.entity.RestaurantEntity;
import com.cybersoft.food_project.entity.RestaurantReviewEntity;
import com.cybersoft.food_project.model.FoodModel;
import com.cybersoft.food_project.repository.RestaurantRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImp implements RestaurantService{

    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    private ServletContext context;
    @Override
    public List<RestaurantDTO> getRestaurant() {
        List<RestaurantDTO> dtos = new ArrayList<>();
        List<RestaurantEntity> restaurants = restaurantRepository.findAll();
        for (RestaurantEntity data: restaurants ) {
            RestaurantDTO restaurantDTO = new RestaurantDTO();
            float avgRate = 0;
            float sumRate = 0;
            restaurantDTO.setTitle(data.getName());

            // Phai tra ra kieu localhost:8080/.... + data.getImage() ,, su dung getContext path

            restaurantDTO.setImage(context.getContextPath()+"/" + data.getImage());
            for (RestaurantReviewEntity dataReview :data.getRestaurantReviews()) {
                sumRate += dataReview.getRate();
            }
            if (data.getRestaurantReviews().size() > 0) {
                avgRate = sumRate/data.getRestaurantReviews().size();
            }

            restaurantDTO.setAvgRate(avgRate);
            dtos.add(restaurantDTO);
        }

        // tranfer t??? entity v??? dto
        return dtos;
    }

    @Override
//    @Cacheable("detail_restaurant")
    public RestaurantDetailDTO getDetailRestaurant(int id) {
        String key = "res" + id;
        Gson gson = new Gson();
        RestaurantDetailDTO restaurantDetailDTO = new RestaurantDetailDTO();

        if(redisTemplate.hasKey("key")){
            // Key c?? t???n t???i
            String data = (String) redisTemplate.opsForValue().get(key);
            restaurantDetailDTO = gson.fromJson(data, RestaurantDetailDTO.class); // bien string thanh doi tuong
        } else {
            // Key not exist

            //Optional l?? 1 ki???u d??? li???u, c?? th??? l?? null => h??? tr??? optional, t???c l?? c?? ho???c k c?? c??ng ???????c
            // D??? li???u cso th??? b??? null, h??? tr??? nh???ng h??m h??? tr??? s???n ki???m tra bi???n c?? null k
            // Optional gi??p x??? l?? null thay v?? t??? r??o c??c case
            Optional<RestaurantEntity> restaurantEntity = restaurantRepository.findById(id);
            if(restaurantEntity.isPresent()) {

                restaurantDetailDTO.setTitle(restaurantEntity.get().getName());
                restaurantDetailDTO.setImage(restaurantEntity.get().getImage());

                float avgRate = 0;
                float sumRate = 0;
                // Phai tra ra kieu localhost:8080/.... + data.getImage() ,, su dung getContext path

                for (RestaurantReviewEntity dataReview :restaurantEntity.get().getRestaurantReviews()) {
                    sumRate += dataReview.getRate();
                }
                if (restaurantEntity.get().getRestaurantReviews().size() > 0) {
                    avgRate = sumRate/restaurantEntity.get().getRestaurantReviews().size();
                }

                restaurantDetailDTO.setAvgRate(avgRate);

                List<FoodModel> foodModels = new ArrayList<>();
                for (FoodEntity foodEntity:restaurantEntity.get().getFoods()) {
                    FoodModel foodModel = new FoodModel();
                    foodModel.setId(foodEntity.getId());
                    foodModel.setName(foodEntity.getName());
                    foodModel.setImage(foodEntity.getImage());
                    foodModel.setPrice(foodEntity.getPrice());

                    foodModels.add(foodModel);
                }
                restaurantDetailDTO.setFoods(foodModels);

            }

            String json = gson.toJson(restaurantDetailDTO); // bien doi tuong thnah string
            redisTemplate.opsForValue().set(key, json);
        }
            return restaurantDetailDTO;


    }

    @Override
    @CacheEvict(value = "detail_restaurant", allEntries = true)
    public void clearAllCache(){}
}
