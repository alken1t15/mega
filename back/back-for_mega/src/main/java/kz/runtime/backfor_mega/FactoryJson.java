package kz.runtime.backfor_mega;

import kz.runtime.backfor_mega.entityjson.coins.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

public class FactoryJson {

    public static HashMap<String, Double> createJsonObject(String url,String nameClass){
        RestTemplate restTemplate =  new RestTemplate();
        if(nameClass.equals("bitcoin")){
            BitCoinJson bitCoinJson = restTemplate.getForObject(url, BitCoinJson.class);
            return bitCoinJson.getBitcoin();
        } else if(nameClass.equals("gala")){
            GalaCoinJson galaCoinJson = restTemplate.getForObject(url, GalaCoinJson.class);
            return galaCoinJson.getGala();
        }else if(nameClass.equals("binancecoin")){
            BinanceCoinJson binanceCoinJson = restTemplate.getForObject(url, BinanceCoinJson.class);
            return binanceCoinJson.getBinanceCoin();
        }else if(nameClass.equals("binance-usd")) {
            BinanceUsdCoinJson binanceUsdCoinJson = restTemplate.getForObject(url, BinanceUsdCoinJson.class);
            return binanceUsdCoinJson.getBinanceUsd();
        }else if(nameClass.equals("ethereum")) {
            EthereumCoinJson ethereumCoinJson = restTemplate.getForObject(url, EthereumCoinJson.class);
            return ethereumCoinJson.getEthereum();
        }else if(nameClass.equals("magic")) {
            MagicCoinJson magicCoinJson = restTemplate.getForObject(url, MagicCoinJson.class);
            return magicCoinJson.getMagic();
        }
        else {
            return null;
        }
    }
}