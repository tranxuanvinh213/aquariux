package com.txvinh.aquariux.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TradingTransaction {
    private Long id;
    private Long userId;
    private String type;
    private Double amount;
    private Double price;
    @JsonProperty("created_date")
    private Instant createdDate;
    @JsonProperty("updated_date")
    public Instant updatedDate;
}
