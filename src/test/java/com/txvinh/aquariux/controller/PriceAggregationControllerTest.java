package com.txvinh.aquariux.controller;

import com.txvinh.aquariux.constant.Crypto;
import com.txvinh.aquariux.domain.PriceData;
import com.txvinh.aquariux.service.PriceAggregateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PriceAggregationControllerTest {
    private MockMvc mockMvc;

    @Mock
    private PriceAggregateService priceAggregateService;

    @InjectMocks
    private PriceAggregationController priceAggregationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(priceAggregationController).build();
    }

    @Test
    void getAll() throws Exception {
        PriceData priceData = PriceData.builder().build();
        PriceData priceData1 = PriceData.builder().build();
        // Mock data
        List<PriceData> priceDataList = Arrays.asList(
                priceData,
                priceData1
        );

        // Mock the service behavior
        when(priceAggregateService.getList()).thenReturn(priceDataList);

        // Perform the request and verify the response
        mockMvc.perform(MockMvcRequestBuilders.get("/prices/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].symbol").value(priceDataList.get(0).getSymbol()))
                .andExpect(jsonPath("$[1].symbol").value(priceDataList.get(1).getSymbol()));
    }

    @Test
    void getLatestPrice() throws Exception {
        // Mock data
        PriceData latestPriceData = PriceData.builder()
                .bidPrice(BigDecimal.valueOf(2000.0))
                .symbol(Crypto.ETHUSDT)
                .build();

        // Mock the service behavior
        when(priceAggregateService.getLatestPriceAggregation()).thenReturn(latestPriceData);

        // Perform the request and verify the response
        mockMvc.perform(MockMvcRequestBuilders.get("/prices/latest"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.symbol").value(latestPriceData.getSymbol()));
    }

}