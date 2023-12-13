package com.txvinh.aquariux.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PriceData implements Comparable<PriceData> {
    private Long id;
    private String symbol;
    private String source;
    private BigDecimal bidPrice;
    private BigDecimal bidQty;
    private BigDecimal askPrice;
    private BigDecimal askQty;
    @JsonProperty("created_date")
    private Instant createdDate;
    @JsonProperty("updated_date")
    public Instant updatedDate;

    @Override
    public int compareTo(PriceData o) {
        if(this.bidPrice.doubleValue() > o.getBidPrice().doubleValue() ) {
            return 1;
        } else if(this.bidPrice.equals(o.getBidPrice()) && this.askPrice.doubleValue() > o.getAskPrice().doubleValue() ) {
            return -1;
        }
        return 0;
    }
}
