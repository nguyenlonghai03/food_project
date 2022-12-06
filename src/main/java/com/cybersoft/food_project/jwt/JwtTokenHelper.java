package com.cybersoft.food_project.jwt;

import com.google.gson.Gson;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

// Class giúp tạo token
@Component // Đưa lên bean
public class JwtTokenHelper {

    private final String strKey = "xJHDonkgbMOgIGNodeG7l2kgYuG6o28gbeG6rXQgZMOgaSBoxqFuIDI1NiBiaXQg"; // Chuỗi base 64
    private Gson gson = new Gson();
    // Hàm tạo token

    public String generateToken(String data, String type, long expireDate) { // muốn lưu trữ gì thì truyền vào
        Date now = new Date();
        Date dateExpired = new Date(now.getTime() + expireDate);
//        Key secrectKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(strKey)); //giải mã

// Map giống như lưu json object lưu dạng key value, cách làm cứng
        Map<String, Object> subjectData = new HashMap<>();
        subjectData.put("username", data);
        subjectData.put("type", type);

        String json = gson.toJson(subjectData); // parse ra json

        return Jwts.builder()
                .setSubject(json)  // lưu trữ dữ liệu vào trong token với kiểu string
                .setIssuedAt(now) // Thời gian tạo token này
                .setExpiration(dateExpired) // thời gian hết hạn
                .signWith(secretKey, SignatureAlgorithm.HS256) // Thuật toán mã hóa token
                .compact(); //Trả ra token đã đc mã hóa
    }

    public String decodeToken(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(strKey)); //giải mã
        // giúp parse token, phai co secret key
        return Jwts.parserBuilder().setSigningKey(secretKey).build()// mới đưa chìa khóa vào thôi
                .parseClaimsJws(token).getBody().getSubject(); //parse token (giải mã) lấy subject
    }

    public boolean validaToken(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(strKey)); //giải mã
        // giúp parse token, phai co secret key
        boolean isSuccess = false;
        try{
            Jwts.parserBuilder().setSigningKey(secretKey).build()// mới đưa chìa khóa vào thôi
                    .parseClaimsJws(token).getBody().getSubject(); //parse token (giải mã) lấy subject
            isSuccess = true;
        }catch (MalformedJwtException ex) {
            System.out.println("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            System.out.println("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            System.out.println("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            System.out.println("JWT claims string is empty.");
        }
        return isSuccess;
    }
}
