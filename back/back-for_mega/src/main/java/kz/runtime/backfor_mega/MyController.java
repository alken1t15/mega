package kz.runtime.backfor_mega;

import kz.runtime.backfor_mega.entity.Card;
import kz.runtime.backfor_mega.entityjson.CryptoJson;
import kz.runtime.backfor_mega.entityjson.MyObject;
import kz.runtime.backfor_mega.serivce.CardService;
import kz.runtime.backfor_mega.serivce.CryptoService;
import kz.runtime.backfor_mega.serivce.HistoryService;
import kz.runtime.backfor_mega.serivce.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@RestController
@RequestMapping(path = "user/login")
public class MyController {

    @Autowired
    private CardService cardService;

    @Autowired
    private CryptoService cryptoService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private UserService userService;

    @GetMapping(path = "/test")
    public String testMethod(){
        Card card = new Card("7777777777777777","05/27","680");
        cardService.save(card);

        return "Fdfsd";
    }

    @GetMapping(path = "/maxim")
    public void handleExampleRequest() {
        String url = "https://api.coingecko.com/api/v3/coins/list?include_platform=true";
        RestTemplate restTemplate =  new RestTemplate();
        MyObject[] myObject = restTemplate.getForObject(url, MyObject[].class);
        for(MyObject m : myObject){
            System.out.println(m.getId());
        }
//        System.out.println(myObject.length);
//        System.out.println(myObject);
    }
    // BNB, BUSD, USDT, MANERO(XMR), GALA, BETH, ETH, MAGIC, LITECOIN(LTC),
    // TRX(TRON), DASH, ATOM(COSMOS), FTM(FANTOM), 1INCH, LUNC(TERRA CLASSIC),
    // DOGE(DOGECOUIN), ZEC(ZCASH), BTC(BITCOIN), NEAR(NEAR PROTOCOL)
    // MATIC(POLYGON)
    public void insetTableCrypto(){
        ArrayList<String> nameCoin = new ArrayList<>();
        nameCoin.add("bitcoin");
        nameCoin.add("binancecoin");
        nameCoin.add("binance-usd");
        nameCoin.add("gala");
        nameCoin.add("ethereum");
        nameCoin.add("magic");
        String url = null;
        for(String str : nameCoin){
            url = "https://api.coingecko.com/api/v3/simple/price?ids=" + str +"&vs_currencies=usd&include_market_cap=true&include_24hr_vol=true&include_24hr_change=true&include_last_updated_at=true&precision=14";
            RestTemplate restTemplate =  new RestTemplate();
            CryptoJson myObject = restTemplate.getForObject(url, CryptoJson.class);
            cryptoService.save(myObject);
        }





    }
}
