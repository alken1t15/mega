package kz.runtime.backfor_mega;

import kz.runtime.backfor_mega.entity.Card;
import kz.runtime.backfor_mega.entity.Crypto;
import kz.runtime.backfor_mega.entity.User;
import kz.runtime.backfor_mega.entity.Wallet;
import kz.runtime.backfor_mega.entityjson.*;
import kz.runtime.backfor_mega.serivce.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    @Autowired
    private WalletService walletService;

    @GetMapping(path = "/test")
    public String testMethod() {
        Card card = new Card("7777777777777777", "05/27", "680");
        cardService.save(card);

        return "Fdfsd";
    }

    @GetMapping(path = "/maxim")
    public void handleExampleRequest() {
        insetTableCrypto();
    }


    // С формы регистрации получаем userName, pass, email, phone, birthday
    @PostMapping(path = "/register")
    public void testMethod(@RequestBody Registration registration) {
        User user = userService.findByEmailAndPass(registration.getEmail(), registration.getPass());
        if (user == null) {
            user = new User(registration.getUserName(), registration.getPass(), registration.getEmail(), registration.getPhone(), registration.getBirthday());
            userService.save(user);
        } else {
            System.out.println("Такой аккаунт есть");
        }
    }

    @GetMapping(path = "/sign")
    public User signInUser(@RequestBody Registration registration) {
        List<User> userList = userService.findAllByEmailAndPass(registration.getEmail(), registration.getPass());
        for (User user : userList) {
            if (user.getEmail().equals(registration.getEmail()) && user.getPass().equals(registration.getPass())) {
                return user;
            }
        }
        return new User();
    }

    // Объект внутри которого другой объект 1 внутри него массив 2 внутри массива объекты 3 внутри объекта 3 поле
    // Сокр имя. Полное имя. Название картинки в LoverCase
    // внутри 1 объекта есть другой массив внутри которого объекты с полями: labels время . name + название валюты in usdt . поле data

    @PostMapping(path = "/profile/output")
    public Boolean getStatusProfile(@RequestBody Trade trade) {
        User user = userService.findByUserName(trade.getUserName());
        List<Wallet> wallets = user.getWalletList();
        for (Wallet wallet : wallets) {
            if (wallet.getNameCrypt().equals(trade.getCrypt())) {
                double sum = wallet.getCount() - trade.getCount();
                if (sum <= 0) {
                    return false;
                }
                wallet.setCount(sum);
                walletService.save(wallet);
                return true;
            }
        }
        return false;
    }

    @PostMapping(path = "/edit/profile")
    public Boolean editMyProfile(@RequestBody UpdateAccount updateAccount) {
        User user = userService.findByUserName(updateAccount.getUserName());
        if (user == null) {
            return false;
        } else {
            user.setUserName(updateAccount.getUserNameModified());
            user.setFirstName(updateAccount.getFirstName());
            user.setSecondName(updateAccount.getSecondName());
            user.setMiddle_name(updateAccount.getMiddleName());
            user.setAge(updateAccount.getAge());
            user.setPhone(updateAccount.getPhone());
            user.setBirthday(updateAccount.getBirthday());
            userService.save(user);
            return true;
        }
    }

    @PostMapping(path = "/edit/pass")
    public Boolean editMyPass(@RequestBody UpdatePass updatePass) {
        User user = userService.findByUserName(updatePass.getUserName());
        if (user == null) {
            return false;
        } else if (user.getPass().equals(updatePass.getPass())) {
            user.setPass(updatePass.getNewPass());
            userService.save(user);
            return true;
        } else {
            return false;
        }
    }

    @PostMapping(path = "/edit/card")
    public Boolean editMyCard(@RequestBody UpdateCard updateCard) {
        Card card = cardService.findByNumberAndDataNameAndSvv(updateCard.getNumber(), updateCard.getDataName(), updateCard.getSvv());
        if (card == null) {
            ///UserName НАДО ДОБАВИТЬ
            cardService.save(new Card(updateCard.getNumber(), updateCard.getDataName(), updateCard.getSvv()));
            return false;
        } else {
            User user = userService.findByUserName(card.getUser().getUserName());
            List<Wallet> wallets = user.getWalletList();
            for (Wallet wallet : wallets) {
                if (wallet.getNameCrypt().equals(updateCard.getCrypt())) {
                    wallet.setCount(wallet.getCount() + updateCard.getCount());
                    walletService.save(wallet);
                    return true;
                }
            }
            Wallet wallet = new Wallet();
            wallet.setCount(updateCard.getCount());
            wallet.setNameCrypt(updateCard.getCrypt());
            wallet.setUser(user);
            walletService.save(wallet);
            return true;
        }
        // name_wallet зачем
    }

    @PostMapping(path = "/edit/delete/profile")
    public Boolean deleteMyProfile(@RequestBody String userName) {
        User user = userService.findByUserName(userName);
        if (user == null) {
            return false;
        } else {
            userService.delete(user);
            return true;
        }
    }


    @GetMapping(path = "/market")
    public MainObject getMarket() {
        ArrayList<Market> markets = new ArrayList<>();
        ArrayList<String> nameCoins = new ArrayList<>();
        ArrayList<ListObject> listObjects = new ArrayList<>();
        RodObject rodObject = new RodObject();
        nameCoins.add("Bitcoin");
        nameCoins.add("Binancecoin");
        nameCoins.add("Binance-usd");
        nameCoins.add("Gala");
        nameCoins.add("Ethereum");
        nameCoins.add("Magic");
        List<Crypto> cryptoList = null;
        for (String name : nameCoins) {
            cryptoList = cryptoService.findByFullName(name);
            Market market = new Market();
            ArrayList<LocalDateTime> localDateTimes = new ArrayList<>();
            ArrayList<Double> list = new ArrayList<>();
            market.setName(cryptoList.get(0).getName());
            for (Crypto crypto : cryptoList) {
                localDateTimes.add(crypto.getDates());
                double roundedNum = Math.round(crypto.getPrice() * 10000.0) / 10000.0;
                list.add(roundedNum);
            }
            market.setDate(localDateTimes);
            market.setPrice(list);
            markets.add(market);
        }
        for (String name : nameCoins) {
            cryptoList = cryptoService.findByFullName(name);
            Crypto crypto = cryptoList.get(0);
            ListObject listObject = new ListObject();
            listObject.setFullName(crypto.getFullName());
            listObject.setName(crypto.getName());
            listObject.setNameImg(crypto.getFullName().toLowerCase());
            listObjects.add(listObject);
        }
        rodObject.setListObjects(listObjects);
        MainObject mainObject = new MainObject();
        mainObject.setRodObject(rodObject);
        mainObject.setMarketList(markets);
        return mainObject;
    }

    @GetMapping(path = "/getPriceCoins")
    public List<CoinsList> getPriceCoins() {
        ArrayList<CoinsList> coinsLists = new ArrayList<>();
        ArrayList<String> nameCoin = new ArrayList<>();
        nameCoin.add("Bitcoin");
        nameCoin.add("Binancecoin");
        nameCoin.add("Binance-usd");
        nameCoin.add("Gala");
        nameCoin.add("Ethereum");
        nameCoin.add("Magic");
        for (String name : nameCoin) {
            CoinsList coinsList = new CoinsList();
            List<Crypto> cryptoList = cryptoService.findByName(name);
            Crypto crypto = cryptoList.get(cryptoList.size() - 1);
            coinsList.setName(crypto.getName() + "\"USDT");
            coinsList.setPrice(crypto.getPrice());
            String result = String.format("%.4f", crypto.getChange());
            System.out.println(result);
            coinsList.setGap(result);
            coinsList.setSign(crypto.getChange() >= 0);
            coinsLists.add(coinsList);
        }
        return coinsLists;
    }

    @GetMapping(path = "/getPriceCoin")
    public Double getPriceCoin() {
        String name = "bitcoin";
        List<Crypto> cryptoList = cryptoService.findByName(name);
        return cryptoList.get(cryptoList.size() - 1).getPrice();
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
        int count = 0;
        ArrayList<String> fullName = new ArrayList<>();
        fullName.add("bitcoin");
        fullName.add("binancecoin");
        fullName.add("binance-usd");
        fullName.add("gala");
        fullName.add("ethereum");
        fullName.add("magic");
        ArrayList<String> name = new ArrayList<>();
        name.add("BTC");
        name.add("BNB");
        name.add("BUSD");
        name.add("GALA");
        name.add("ETH");
        name.add("MAGIC");
        String url = null;
        for (var str : fullName) {
            url = "https://api.coingecko.com/api/v3/simple/price?ids=" + str + "&vs_currencies=usd&include_market_cap=true&include_24hr_vol=true&include_24hr_change=true&include_last_updated_at=true&precision=14";
            HashMap<String, Double> arr = FactoryJson.createJsonObject(url, str);
            Crypto crypto = new Crypto();
            for (int i = count; i < name.size(); i++) {
                crypto.setName(name.get(i));
                count += 1;
                break;
            }
            crypto.setFullName(str.substring(0, 1).toUpperCase() + str.substring(1));
            for (Map.Entry<String, Double> set : arr.entrySet()) {
                switch (set.getKey()) {
                    case "usd" -> {
                        crypto.setPrice(set.getValue());
                        crypto.setPriceSell(set.getValue() * 0.95);
                    }
                    case "usd_24h_change" -> crypto.setChange(set.getValue());
                    case "last_updated_at" -> {
                        long seconds = Math.round(set.getValue());
                        LocalDateTime dateTime = LocalDateTime.ofEpochSecond(seconds, 0, ZoneOffset.ofHours(6));
                        crypto.setDates(dateTime);
                    }
                }
            }

            cryptoService.save(crypto);
        }
    }
}