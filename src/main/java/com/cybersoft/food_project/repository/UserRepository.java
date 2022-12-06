package com.cybersoft.food_project.repository;

import com.cybersoft.food_project.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // JpaRepo nhận vào 1 entity và 1 kiểu dữ liệu của khóa chính đó , dua len bean
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    // nho viet ten dung syntax cua jpa
    List<UserEntity> findByEmailAndPassword(String email, String password);
    List<UserEntity> findByEmail(String email);

}
