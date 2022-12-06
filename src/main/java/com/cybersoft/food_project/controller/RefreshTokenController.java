package com.cybersoft.food_project.controller;

import com.cybersoft.food_project.jwt.JwtTokenHelper;
import com.cybersoft.food_project.payload.response.DataResponse;
import com.cybersoft.food_project.payload.response.DataTokenResponse;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/refresh-token") // ;link refresh token
public class RefreshTokenController {

    // Phải upload được file
    // Phải lấy ra được file đó và trả đường dẫn file cho người dùng
    @Autowired
    JwtTokenHelper jwtTokenHelper;
    private long expireDate = 8 * 60 * 60 * 1000;
    private long refreshExpireDate = 80 * 60 * 60 * 1000; // 80 tieng
    private Gson gson = new Gson();

    @PostMapping
    public ResponseEntity<?> index(@RequestParam("token") String token) {
        DataResponse dataResponse = new DataResponse();
        // Phải validate lại token
        if (jwtTokenHelper.validaToken(token)) {
            // Token hợp lệ thì tạo chứng thực ra
// nhớ phải có tham số cuối cùng danh sách role
            String json = jwtTokenHelper.decodeToken(token);

            //Map.class là biên về kiểu dữ liệu map
            Map<String, Object> map = gson.fromJson(json, Map.class); // bieens ve doi tuong Map

            if (StringUtils.hasText(map.get("type").toString()) &&
                    map.get("type").toString().equals("refresh")) { // là refresh token thif ms xử lý

                String tokenAuthen = jwtTokenHelper.generateToken(map.get("username").toString(), "authen", expireDate); // trong token này sẽ lưu tên đăng
                String refreshToken = jwtTokenHelper.generateToken(map.get("username").toString(), "refresh", refreshExpireDate);
                // String decodeToken = jwtTokenHelper.decodeToken(token);

                // nhập của người dùng , lưu ý token k lưu password hoặc thông tin quan trọng

                DataTokenResponse dataTokenResponse = new DataTokenResponse();
                dataTokenResponse.setToken(tokenAuthen);
                dataTokenResponse.setRefreshToken(refreshToken);


                dataResponse.setStatus(HttpStatus.OK.value()); // Vì logic code chạy mượt thì sẽ là 200
        dataResponse.setSuccess(true);
                 dataResponse.setDesc("");
                dataResponse.setData(dataTokenResponse);
            }

        } else {
            dataResponse.setStatus(HttpStatus.OK.value()); // Vì logic code chạy mượt thì sẽ là 200
            dataResponse.setSuccess(true);
            dataResponse.setDesc("");
            dataResponse.setData("");
        }
        return new ResponseEntity<>(dataResponse, HttpStatus.OK);
    }
}
