package com.txvinh.aquariux.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HuobiPriceData {
    private String symbol;
    private Double open;
    private Double high;
    private Double low;
    private Double close;
    private Double amount;
    private Double vol;
    private Double count;
    private Double bid;
    private Double bidSize;
    private Double ask;
    private Double askSize;
}
