package com.txvinh.aquariux.constant;

import java.util.Set;

public class Crypto {
    public static final String BINANCE_URL = "https://api.binance.com/api/v3/ticker/bookTicker";
    public static final String HUOBI_URL = "https://api.huobi.pro/market/tickers";
    public static final String HUOBI = "HUOBI";
    public static final String BINANCE = "BINANCE";
    public static final String USDT = "USDT";
    public static final String ETHUSDT = "ETHUSDT";
    public static final String BTCUSDT = "BTCUSDT";
    public static final Set<String> pairsOfCrypto = Set.of(ETHUSDT, BTCUSDT);
    
    public static final String SUCCESS = "SUCCESS";
    public static final String FAIL = "FAIL";
    public static final String PENDING = "PENDING";

    public static final String SELL = "SELL";
    public static final String BUY = "BUY";

}
