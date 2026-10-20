package com.kx.system.domain.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author huhao
 * @created 2022/5/23
 * @Description 排序对象
 */
@Data
public class Order {
    @NotBlank(message = "Invalid order column")
    private String column;

    private String direction = "ASC";

    public Order(String column, String direction) {
        this.column = column;
        this.direction = direction;
    }

    public Order() {
    }
}
