package com.cybersoft.food_project.security;

import com.cybersoft.food_project.jwt.JwtTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecSecurityConfig {

    // Dùng để khởi tạo danh sách user cứng và danh sách user này sẽ được lưu ở RAM
//    @Bean // ghi de len cai bean cua spring security
//    public InMemoryUserDetailsManager userDetailsService() {
//        // Tạo danh sách user lưu vào bộ nhớ
//        UserDetails user1 = User.withUsername("cybersoft") // quy định name của user
//                .password(passwordEncoder().encode("123")) // mã hóa mật khẩu bằng hàm passwordEncoder
//                .roles("USER") // quy định role cho user này
//                .build(); // build lên tạo ra 1 user
//        UserDetails user2 = User.withUsername("admin") // quy định name của user
//                .password(passwordEncoder().encode("123")) // mã hóa mật khẩu bằng hàm passwordEncoder
//                .roles("ADMIN") // quy định role cho user này
//                .build(); // build lên tạo ra 1 user
//        // Tạo xong thì phải lưu vào trong bộ nhớ
//        return new InMemoryUserDetailsManager(user1, user2); // Lưu vào trong bộ nhớ
//    }

    @Autowired
    CustomAuthentProvider customAuthentProvider;

    @Autowired
    JwtTokenFilter jwtTokenFilter;

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        System.out.println("AuthenticationManager");
        // AuthenticationManagerBuilder có thằng này mới xài đc customProvider
        // Khởi tạo AuthenticationManagerBuilder
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        // Gán lại cái provider = cái custom thay vì xài mắc định
        authenticationManagerBuilder.authenticationProvider(customAuthentProvider);
        return authenticationManagerBuilder.build();
    }

    // Kiểu mã hóa dữ liệu
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    // Nơi quy định các rule liên quan tới bảo mật và quyền truy cập
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        System.out.println("filterChain");
        /*
        * antMatchers: Định nghĩa link cần xác thực
        * authenticated: Bắt buộc phải chứng thực(đăng nhập)
        * permitAll: cho phép truy cập full quyền vào link chỉ định ở antMatchers
        * anyRequest: Toàn bộ request gọi vào api
        * sessionManagement: quản lý toàn bộ session trong cái project của mình
        * sessionCreationPolicy: quy định role sử dụng session trong project như thế nào
        * STATELESS là k lưu trữ session
        * */
        // Cấu hình
        http.csrf().disable()
                // đoạn này là k sử dụng session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests() // Những cái request của mình đều phải thông qua chứng thực tức là cái authorize này
                .antMatchers("/signin").permitAll()// Khai báo những cái pattern của những link nào đc phép truy cập, hay k đc phép
                .antMatchers("/refresh-token").permitAll() // trang refresh token thif luon permission all
                .antMatchers("/file/**").permitAll()
                .antMatchers("/signin/test").authenticated()
                .anyRequest().authenticated();

    /*
    * // Thêm filter trước 1 filter nào đó
    * */
        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
