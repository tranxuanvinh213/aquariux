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
public class BinanceResponse {
    List<BinancePriceData> binancePriceDataList;
}
