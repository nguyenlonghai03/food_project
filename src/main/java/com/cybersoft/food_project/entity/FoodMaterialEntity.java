package com.cybersoft.food_project.entity;

import com.cybersoft.food_project.entity.id.FoodMaterialId;
import org.springframework.boot.autoconfigure.condition.ConditionalOnJava;

import javax.persistence.*;

@Entity(name = "food_material")
@IdClass(FoodMaterialId.class)
public class FoodMaterialEntity {
    @Id // Id này sẽ tham chếu với Id bên IdClass
    private int id_food; // Lưu ý tên ở đây sẽ trùng với tên bên FoodMaterialId

    @Id
    private int id_material;

    // Đối với bảng trung gian k được insert hay update mà chỉ được query nếu k sẽ bị lỗi lặp vô tận
    @ManyToOne()
    @JoinColumn(name = "id_food", insertable = false, updatable = false)
    private FoodEntity food;

    @ManyToOne()
    @JoinColumn(name = "id_material", insertable = false, updatable = false)
    private MaterialEntity material;

    public int getId_food() {
        return id_food;
    }

    public void setId_food(int id_food) {
        this.id_food = id_food;
    }

    public int getId_material() {
        return id_material;
    }

    public void setId_material(int id_material) {
        this.id_material = id_material;
    }

    public FoodEntity getFood() {
        return food;
    }

    public void setFood(FoodEntity food) {
        this.food = food;
    }

    public MaterialEntity getMaterial() {
        return material;
    }

    public void setMaterial(MaterialEntity material) {
        this.material = material;
    }
}
