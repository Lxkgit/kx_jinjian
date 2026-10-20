package com.kx.system.domain.dto;

import lombok.Data;

@Data
public class VisitDTO {
    private String identity;
    private Long customerId;
    private String type;
    private String phone;
}
