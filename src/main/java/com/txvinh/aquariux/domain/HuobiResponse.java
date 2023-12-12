package com.txvinh.aquariux.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HuobiResponse {
    private List<HuobiPriceData> data;
    private String status;
    private Long ts;
    
}
