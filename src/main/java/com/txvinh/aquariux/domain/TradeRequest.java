package com.txvinh.aquariux.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TradeRequest {
    private String action;
    private String symbol;
    private Double amount;
    private Double fee;
}
