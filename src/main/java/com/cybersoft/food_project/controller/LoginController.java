package com.cybersoft.food_project.controller;

import com.cybersoft.food_project.payload.request.SignInRequest;
import com.cybersoft.food_project.payload.response.DataResponse;
import com.cybersoft.food_project.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // DDinhj nghia api
@RequestMapping("/signin")
@CrossOrigin // CHo phép những domain khác truy cập vào api
public class LoginController {

    @Autowired
    LoginService loginService;

    @PostMapping("")
    public ResponseEntity<DataResponse> signin(@RequestBody SignInRequest request) { //? :là trả về kiểu gì thì chưa biết
        boolean isSuccess = loginService.checkLogin(request.getUsername(), request.getPassword());
        DataResponse dataResponse = new DataResponse();
        dataResponse.setStatus(HttpStatus.OK.value()); // Vì logic code chạy mượt thì sẽ là 200
        dataResponse.setSuccess(isSuccess);
        dataResponse.setDesc("");
        dataResponse.setData("");
        return new ResponseEntity<>(dataResponse, HttpStatus.OK);
    }
}
