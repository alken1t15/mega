package kz.runtime.backfor_mega;

import kz.runtime.backfor_mega.entityjson.coins.BitCoinJson;
import kz.runtime.backfor_mega.entityjson.coins.GalaCoinJson;
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
        }
        else {
            return null;
        }
    }
}