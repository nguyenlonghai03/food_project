package com.cybersoft.food_project.services;

import com.cybersoft.food_project.entity.UserEntity;
import com.cybersoft.food_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // Dua len bean
public class LoginServiceImp implements LoginService {
    @Autowired
    UserRepository userRepository;

    @Override
    public boolean checkLogin(String email, String password) {
        List<UserEntity> users = userRepository.findByEmailAndPassword(email, password);
        return users.size() > 0; // dung thi tra ve true, sai thi tra ve false
    }
}
