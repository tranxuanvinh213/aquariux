package com.txvinh.aquariux.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TradingHistory {
    private Long id;
    private Long userId;
    private String type;
    private String symbol;
    private Double amount;
    private Double price;
    private String status;
    @JsonProperty("created_date")
    private Instant createdDate;
    @JsonProperty("updated_date")
    public Instant updatedDate;
}
