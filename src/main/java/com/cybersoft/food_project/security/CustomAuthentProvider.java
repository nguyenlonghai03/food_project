package com.cybersoft.food_project.security;

import com.cybersoft.food_project.entity.UserEntity;
import com.cybersoft.food_project.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.ArrayList;

// Nhận vào username password, sau đó tiến hành xử lý r trả ra 1 đối tượng userdetail
@Component // đưa lên bean để bên kia sử dụng
public class CustomAuthentProvider implements AuthenticationProvider {

    @Autowired
    LoginService loginService;

//    @Autowired
//  @Qualifier("ten Bean") // Chỉ định tên bean muốn lấy trên container nếu trùng class
//    PasswordEncoder passwordEncoder;
PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("authentication");
        // Xử lý đăng nhập thành công hay thất bại
        String username = authentication.getName();
        // password lưu trong Credentials vì đã đc mã hóa rồi
        String password = authentication.getCredentials().toString(); // layas password

        // Query database, nếu tồn tại trong data base thì return ra, còn thì return null => dang nhap that bai

        UserEntity userEntity = loginService.checkLogin(username);
        // Nhận vào 2 tham số 1 cái chưa mã hóa, 1 cái đã mã hóa ở DB để so sánh


        if (userEntity != null ) {
            boolean isMatchPassword = passwordEncoder.matches(password, userEntity.getPassword());
            // arraylist là danh sách role
            // trả ra đc cái này thì ms là thành công
            if(isMatchPassword) {
                return new UsernamePasswordAuthenticationToken(userEntity.getEmail(), userEntity.getPassword(),
                        new ArrayList<>()); // trả ra null thì là đăng nhập thất bại
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
