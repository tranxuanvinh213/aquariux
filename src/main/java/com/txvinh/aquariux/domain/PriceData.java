package com.txvinh.aquariux.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PriceData implements Comparable<PriceData> {
    private Long id;
    private String symbol;
    private String source;
    private Double bidPrice;
    private Double bidQty;
    private Double askPrice;
    private Double askQty;
    @JsonProperty("created_date")
    private Instant createdDate;
    @JsonProperty("updated_date")
    public Instant updatedDate;

    @Override
    public int compareTo(PriceData o) {
        if(this.bidPrice > o.getBidPrice()) {
            return 1;
        } else if(this.bidPrice.equals(o.getBidPrice()) && this.askPrice > o.getAskPrice()) {
            return -1;
        }
        return 0;
    }
}
