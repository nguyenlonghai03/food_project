package com.cybersoft.food_project.repository;

import com.cybersoft.food_project.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // integer laf kieu du lieu khoa chinh cua categoryentity
public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {

    // cách 1 dùng câu native query
//    @Query(value = "select c.* from category as c order by c.id DESC limit 6", nativeQuery = true)
//    public List<CategoryEntity> getExplorer();

//     Cách 2  dùng câu query cho sẵn của jpa

    public List<CategoryEntity> findTop6ByOrderByIdDesc();

}
