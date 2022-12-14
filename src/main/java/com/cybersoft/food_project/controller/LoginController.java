package com.cybersoft.food_project.controller;

import com.cybersoft.food_project.jwt.JwtTokenHelper;
import com.cybersoft.food_project.payload.request.SignInRequest;
import com.cybersoft.food_project.payload.response.DataResponse;
import com.cybersoft.food_project.payload.response.DataTokenResponse;
import com.cybersoft.food_project.services.LoginService;
import org.apache.catalina.security.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController // DDinhj nghia api
@RequestMapping("/signin")
@CrossOrigin // CHo phép những domain khác truy cập vào api
public class LoginController {

    @Autowired
    LoginService loginService;

    @Autowired
    JwtTokenHelper jwtTokenHelper;

    @Autowired
    AuthenticationManager authenticationManager;
    @GetMapping("/test")
    public String test() {
        return "Hell";
    }
    private long expireDate = 8 * 60 * 60 * 1000;
    private long refreshExpireDate = 80 * 60 * 60 * 1000; // 80 tieng
    @PostMapping("")
    public ResponseEntity<DataResponse> signin(@RequestBody SignInRequest request) { //? :là trả về kiểu gì thì chưa biết
//        boolean isSuccess = loginService.checkLogin(request.getUsername(), request.getPassword());
        // UsernamePasswordAuthenticationToken nhớ giống kiểu return bên provider
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(request.getUsername(),
                request.getPassword());
        // Chứng thực cái user password truyền vào coi thử giống với trong db hay không
//        nếu thành công r thì trả ra lưu vào trong securitycontext
        Authentication auth =   authenticationManager.authenticate(authRequest);

        // Nếu thất bại thì k chạy xuống đây nữa vì k có quyền
        //Sau khi chứng thực xong tạo 1 cái context cho nó để sau này sử dụng lại k cần phải chứng thực
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(auth);

        String token = jwtTokenHelper.generateToken(request.getUsername(), "authen", expireDate); // trong token này sẽ lưu tên đăng
        String refreshToken = jwtTokenHelper.generateToken(request.getUsername(), "refresh", refreshExpireDate);
       // String decodeToken = jwtTokenHelper.decodeToken(token);

        // nhập của người dùng , lưu ý token k lưu password hoặc thông tin quan trọng

        DataTokenResponse dataTokenResponse = new DataTokenResponse();
        dataTokenResponse.setToken(token);
        dataTokenResponse.setRefreshToken(refreshToken);

        DataResponse dataResponse = new DataResponse();
        dataResponse.setStatus(HttpStatus.OK.value()); // Vì logic code chạy mượt thì sẽ là 200
//        dataResponse.setSuccess(isSuccess);
       // dataResponse.setDesc(decodeToken);
        dataResponse.setData(dataTokenResponse);
        return new ResponseEntity<>(dataResponse, HttpStatus.OK);
    }
}
