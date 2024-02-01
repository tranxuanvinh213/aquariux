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
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
@EnableScheduling
@Slf4j
public class PriceAggregateScheduler {
    private final RestTemplate restTemplate;
    private final PriceAggregateService priceAggregateService;
    
    @Scheduled(fixedRate = 10000)
    @Transactional
    public void fetchAndAggregatePrices() {
        log.info("START fetchAndAggregatePrices");
        List<PriceData> priceDataList = new ArrayList<>();
        // Fetch and aggregate prices from Binance
        fetchPriceDataFromBinance(priceDataList);
        // Fetch and aggregate prices from Huobi
        fetchPriceDataFromHuobi(priceDataList);
        // Find best price (BidPrice highest and askPrice lowest)
        Optional<PriceData> bestPrice = priceDataList.stream().max(PriceData::compareTo);
        // call service update to database.
        bestPrice.ifPresent(priceAggregateService::save);

        log.info("END fetchAndAggregatePrices");
    }

    @Async
    void fetchPriceDataFromHuobi(List<PriceData> priceDataList) {
        HuobiResponse huobiResponse = new HuobiResponse();
        try {
            huobiResponse = restTemplate.getForObject(Crypto.HUOBI_URL, HuobiResponse.class);
        } catch (RestClientException ex) {
            ex.printStackTrace();
            log.error(ex.getMessage());
        }
        if(huobiResponse != null && huobiResponse.getData() != null) {
            for (HuobiPriceData hData: huobiResponse.getData()) {
                PriceData priceData = PriceDataMapper.INSTANCE.huobiPriceToPrice(hData);
                priceData.setSource(Crypto.HUOBI);
                if(Crypto.pairsOfCrypto.contains(priceData.getSymbol())) {
                    priceDataList.add(priceData);
                }
            }
        }
    }

    @Async
    void fetchPriceDataFromBinance(List<PriceData> priceDataList) {
        String jsonData = "";
        try {
            jsonData = restTemplate.getForObject(Crypto.BINANCE_URL, String.class);
        } catch (RestClientException ex) {
            ex.printStackTrace();
            log.error(ex.getMessage());
        }
        if(jsonData != null && !jsonData.isEmpty()) {
            List<BinancePriceData> binancePriceDataList = convertJsonToBinanceDataList(jsonData);
            if(!binancePriceDataList.isEmpty()) {
                for (BinancePriceData bData: binancePriceDataList) {
                    PriceData priceData = PriceDataMapper.INSTANCE.binancePriceToPrice(bData);
                    priceData.setSource(Crypto.BINANCE);
                    if(Crypto.pairsOfCrypto.contains(priceData.getSymbol())) {
                        priceDataList.add(priceData);
                    }
                }
            }
        }
    }

    private static List<BinancePriceData> convertJsonToBinanceDataList(String jsonResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(jsonResponse, new TypeReference<>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return Collections.emptyList();
        }
    }
}
