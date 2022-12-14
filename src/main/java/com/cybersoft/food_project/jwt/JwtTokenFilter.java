package com.cybersoft.food_project.jwt;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

// Thằng này phải chạy trước filter chứng thực
@Component // Đưa lên bean
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    JwtTokenHelper jwtTokenHelper;

    private Gson gson = new Gson();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Cắt header và lấy token
        String token = getTokenFromHeader(request);
        if(token != null) {
            // Kiểm tra token có phải do hệ thống của mình sinh ra hay không
            if(jwtTokenHelper.validaToken(token)) {
                // Token hợp lệ thì tạo chứng thực ra
// nhớ phải có tham số cuối cùng danh sách role
                String json = jwtTokenHelper.decodeToken(token);

                //Map.class là biên về kiểu dữ liệu map
                Map<String, Object> map = gson.fromJson(json, Map.class); // bieens ve doi tuong Map

                if (StringUtils.hasText(map.get("type").toString()) &&
                        !map.get("type").toString().equals("refresh")) {
                    // Set chứng thực
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken("", "", new ArrayList<>());
                    SecurityContext securityContext = SecurityContextHolder.getContext();
                    securityContext.setAuthentication(authenticationToken);
                }

            }
        }
//        Để đi tiếp
                filterChain.doFilter(request, response); // k có cái này sẽ k redirect tới cái người dùng gọi
    }

    private String getTokenFromHeader (HttpServletRequest request) {
        // Lấy giá trị token ở header có keylà authorization
        // 1 Header có nhiều key
        String strToken = request.getHeader("Authorization");
//StringUtils.hasText(strToken) nếu k phải là null thì trả true
        if(StringUtils.hasText(strToken) && strToken.startsWith("Bearer ")) {
            // Xứ lý khi token hợp lệ
            // substring là hàm cắt chuôix,
            String finalToken = strToken.substring(7); // cắt từ vị trí thứ 7 tới hết
            return finalToken;
        } else {
            return null;
        }
    }
}
