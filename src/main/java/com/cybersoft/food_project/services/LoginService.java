package com.cybersoft.food_project.services;

import com.cybersoft.food_project.entity.UserEntity;

import java.util.List;

public interface LoginService {
    // Dinh nghia ham tra ve danh sach user
    boolean checkLogin(String email, String password);
    UserEntity checkLogin(String email);
}
