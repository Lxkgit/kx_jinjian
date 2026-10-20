package com.kx.system.domain.customer;

import com.amazonaws.services.dynamodbv2.xspec.S;
import lombok.Data;

@Data
public class BankType {

    private String bankAgencyName;

    private Integer count;
}
