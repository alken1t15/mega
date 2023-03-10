package kz.runtime.backfor_mega;

import kz.runtime.backfor_mega.entity.Card;
import kz.runtime.backfor_mega.entity.Crypto;
import kz.runtime.backfor_mega.entity.User;
import kz.runtime.backfor_mega.entityjson.MyObject;
import kz.runtime.backfor_mega.serivce.CardService;
import kz.runtime.backfor_mega.serivce.CryptoService;
import kz.runtime.backfor_mega.serivce.HistoryService;
import kz.runtime.backfor_mega.serivce.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "user/login")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
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
    public String testMethod() {
        Card card = new Card("7777777777777777", "05/27", "680");
        cardService.save(card);

        return "Fdfsd";
    }

    @GetMapping(path = "/maxim")
    public void handleExampleRequest() {
        String url = "https://api.coingecko.com/api/v3/coins/list?include_platform=true";
        RestTemplate restTemplate = new RestTemplate();
        MyObject[] myObject = restTemplate.getForObject(url, MyObject[].class);
        for (MyObject m : myObject) {
            System.out.println(m.getId());
        }
        insetTableCrypto();
    }

    @PostMapping(path = "/register")
    public void testMethod(@RequestBody Registration registration) {
        User user = userService.findByEmailAndPass(registration.getEmail(),registration.getPass());
        if(user == null){
            user = new User();
            user.setEmail(registration.getEmail());
            user.setPhone(registration.getTel());
            user.setPass(registration.getPass());
            userService.save(user);
        }else {
            System.out.println("Такой аккаунт есть");
        }
    }

    @GetMapping(path = "/test3")
    public String textMethod() {
        return "jsfdjsdfsdf";
    }

    // BNB, BUSD, USDT, MANERO(XMR), GALA, BETH, ETH, MAGIC, LITECOIN(LTC),
    // TRX(TRON), DASH, ATOM(COSMOS), FTM(FANTOM), 1INCH, LUNC(TERRA CLASSIC),
    // DOGE(DOGECOUIN), ZEC(ZCASH), BTC(BITCOIN), NEAR(NEAR PROTOCOL)
    // MATIC(POLYGON)
    public void insetTableCrypto() {
        ArrayList<String> nameCoin = new ArrayList<>();
        nameCoin.add("bitcoin");
        nameCoin.add("binancecoin");
        nameCoin.add("binance-usd");
        nameCoin.add("gala");
        nameCoin.add("ethereum");
        nameCoin.add("magic");
        String url = null;
        for (var str : nameCoin) {
            url = "https://api.coingecko.com/api/v3/simple/price?ids=" + str + "&vs_currencies=usd&include_market_cap=true&include_24hr_vol=true&include_24hr_change=true&include_last_updated_at=true&precision=14";
            HashMap<String, Double> arr = FactoryJson.createJsonObject(url, str);
            Crypto crypto = new Crypto();
            crypto.setName(str);
            System.out.println(url);
            System.out.println(arr);
            for (Map.Entry<String, Double> set : arr.entrySet()) {
                switch (set.getKey()) {
                    case "usd" -> {
                        crypto.setPrice(set.getValue());
                        crypto.setPriceSell(set.getValue() * 0.95);
                    }
                    case "usd_24h_change" -> crypto.setChange(set.getValue());
                    case "last_updated_at" -> {
                        long seconds = Math.round(set.getValue());
                        LocalDateTime dateTime = LocalDateTime.ofEpochSecond(seconds, 0, ZoneOffset.UTC);
                        crypto.setDates(dateTime);
                    }
                }
            }

            cryptoService.save(crypto);
        }
    }
}