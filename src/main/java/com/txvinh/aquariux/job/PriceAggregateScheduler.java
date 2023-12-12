package com.txvinh.aquariux.job;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.txvinh.aquariux.constant.Crypto;
import com.txvinh.aquariux.mapper.PriceDataMapper;
import com.txvinh.aquariux.domain.BinancePriceData;
import com.txvinh.aquariux.domain.HuobiPriceData;
import com.txvinh.aquariux.domain.HuobiResponse;
import com.txvinh.aquariux.domain.PriceData;
import com.txvinh.aquariux.service.PriceAggregateService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
@EnableScheduling
public class PriceAggregateScheduler {
    private final RestTemplate restTemplate;
    private final PriceAggregateService priceAggregateService;
    
    @Scheduled(fixedRate = 10000)
    public void fetchAndAggregatePrices() {
        System.out.println("START");
        List<PriceData> priceDataList = new ArrayList<>();
        // Fetch and aggregate prices from Binance
        String jsonData = restTemplate.getForObject(Crypto.BINANCE_URL, String.class);
        List<BinancePriceData> binancePriceDataList = convertJsonToBinanceDataList(jsonData);
        if(binancePriceDataList != null  && !binancePriceDataList.isEmpty()) {
            for (BinancePriceData bData: binancePriceDataList) {
                PriceData priceData = PriceDataMapper.INSTANCE.binancePriceToPrice(bData);
                priceData.setSource(Crypto.BINANCE);
                if(Crypto.pairsOfCrypto.contains(priceData.getSymbol())) {
                    priceDataList.add(priceData);
                }
            }
        }

        // Fetch and aggregate prices from Huobi
        HuobiResponse huobiResponse = restTemplate.getForObject(Crypto.HUOBI_URL, HuobiResponse.class);
        if(huobiResponse != null && huobiResponse.getData() != null) {
            for (HuobiPriceData hData: huobiResponse.getData()) {
                PriceData priceData = PriceDataMapper.INSTANCE.huobiPriceToPrice(hData);
                priceData.setSource(Crypto.HUOBI);
                if(Crypto.pairsOfCrypto.contains(priceData.getSymbol())) {
                    priceDataList.add(priceData);
                }
            }
        }
        // Find best price
        Optional<PriceData> bestPrice = priceDataList.stream().max(PriceData::compareTo);
        // call service update to database.
        bestPrice.ifPresent(priceAggregateService::save);

        System.out.println("END");
    }

    private static List<BinancePriceData> convertJsonToBinanceDataList(String jsonResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(jsonResponse, new TypeReference<>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
