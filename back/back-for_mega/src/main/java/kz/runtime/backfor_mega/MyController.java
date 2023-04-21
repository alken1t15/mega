package kz.runtime.backfor_mega;

import kz.runtime.backfor_mega.dao.HistoryRepository;
import kz.runtime.backfor_mega.entity.*;
import kz.runtime.backfor_mega.entityjson.*;
import kz.runtime.backfor_mega.serivce.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class MyController {
    private static final ArrayList<String> fullName = new ArrayList<>();
    private static final ArrayList<String> name = new ArrayList<>();

    private static final ArrayList<String> nameCoin = new ArrayList<>();

    static {
        fullName.add("bitcoin");
        fullName.add("binancecoin");
        fullName.add("binance-usd");
        fullName.add("gala");
        fullName.add("ethereum");
        fullName.add("magic");
        name.add("BTC");
        name.add("BNB");
        name.add("BUSD");
        name.add("GALA");
        name.add("ETH");
        name.add("MAGIC");
        nameCoin.add("Bitcoin");
        nameCoin.add("Binancecoin");
        nameCoin.add("Binance-usd");
        nameCoin.add("Gala");
        nameCoin.add("Ethereum");
        nameCoin.add("Magic");
    }

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
    @Autowired
    private HistoryRepository historyRepository;

    @GetMapping(path = "/maxim")
    public void handleExampleRequest() {
        insetTableCrypto();
    }


    // С формы регистрации получаем userName, pass, email, phone, birthday
    @PostMapping(path = "/signup")
    public Boolean testMethod(@RequestBody Registration registration) {
        User user = userService.findByEmailAndPass(registration.getEmail(), registration.getPass());
        if (user == null) {
            user = new User(registration.getUserName(), registration.getPass(), registration.getEmail(), registration.getPhone(), registration.getBirthday());
            user.setRegisterAccount(LocalDate.now());
            userService.save(user);
            return true;
        } else {
            return false;
        }
    }

    @PostMapping(path = "/signin")
    public UserJson signInUser(@RequestBody Registration registration) {
        User user = userService.findByEmailAndPass(registration.getEmail(), registration.getPass());
        if (user != null) {
            return new UserJson(user.getId(), user.getUserName(), user.getFirstName(), user.getSecondName(), user.getMiddle_name(), user.getAge(), user.getAddress(), user.getPhone(), user.getPass(), user.getEmail(), user.getBirthday());
        }
        return new UserJson();
    }

    // Объект внутри которого другой объект 1 внутри него массив 2 внутри массива объекты 3 внутри объекта 3 поле
    // Сокр имя. Полное имя. Название картинки в LoverCase
    // внутри 1 объекта есть другой массив внутри которого объекты с полями: labels время . name + название валюты in usdt . поле data

    @PostMapping(path = "/profile/output")
    public Boolean getStatusProfile(@RequestBody Trade trade) {
        User user = userService.findByEmailAndPass(trade.getEmail(), trade.getPass());
        List<Wallet> wallets = user.getWalletList();
        for (Wallet wallet : wallets) {
            if (wallet.getNameCrypt().equals(trade.getCrypt())) {
                double sum = wallet.getCount() - trade.getCount();
                if (sum <= 0) {
                    return false;
                }
                wallet.setCount(sum);
                walletService.save(wallet);
                Wallet wallet1 = walletService.findByNameWallet(trade.getWallet());
                if (wallet1 != null) {
                    wallet1.setCount(wallet1.getCount() + trade.getCount());
                    walletService.save(wallet1);
                    History history1 = new History(trade.getWallet(), LocalDateTime.now(), trade.getCrypt(), trade.getCount(), wallet1.getUser());
                    historyRepository.save(history1);
                }
                History history = new History(trade.getWallet(), LocalDateTime.now(), trade.getCrypt(), trade.getCount(), wallet.getUser());
                historyRepository.save(history);
            }
        }
        return true;
    }

    @PostMapping(path = "/profile/person")
    public Boolean editMyProfile(@RequestBody UpdateAccount updateAccount) {
        User user = userService.findByUserName(updateAccount.getUserName());
        if (user == null) {
            return false;
        } else {
            user.setUserName(updateAccount.getUserNameModified());
            user.setFirstName(updateAccount.getFirstName());
            user.setSecondName(updateAccount.getSecondName());
            user.setMiddle_name(updateAccount.getLastName());
            user.setAge(updateAccount.getAge());
            user.setBirthday(updateAccount.getBirthday());
            userService.save(user);
            return true;
        }
    }

    @PostMapping(path = "/profile/private")
    public Boolean editMyPass(@RequestBody UpdatePass updatePass) {
        User user = userService.findByEmailAndPass(updatePass.getEmail(), updatePass.getPass());
        if (user != null) {
            user.setPass(updatePass.getNewPass());
            userService.save(user);
            return true;
        } else {
            return false;
        }
    }

    @PostMapping(path = "/card")
    public Boolean editMyCard(@RequestBody UpdateCard updateCard) {
        Card card = cardService.findByNumberAndDataNameAndSvv(updateCard.getNumber(), updateCard.getDataName(), updateCard.getSvv());
        User user2 = userService.findByEmailAndPass(updateCard.getEmail(), updateCard.getPass());
        if (card == null) {
            Card card1 = new Card(updateCard.getNumber(), updateCard.getDataName(), updateCard.getSvv());
            card1.setUser(user2);
            cardService.save(card1);
            return false;
        } else {
            User user = card.getUser();
            List<Wallet> wallets = user.getWalletList();
            for (Wallet wallet : wallets) {
                if (wallet.getNameCrypt().equals(updateCard.getCrypt())) {
                    wallet.setCount(wallet.getCount() + updateCard.getCount());
                    walletService.save(wallet);
                    return true;
                }
            }
            long rand = (int) (Math.random() * 1000000000000000000L);
            Wallet wallet = new Wallet(String.valueOf(rand), updateCard.getCrypt(), updateCard.getCount(), user);
            walletService.save(wallet);
            return true;
        }
    }

    @PostMapping(path = "/profile/delete")
    public Boolean deleteMyProfile(@RequestBody Trade trade) {
        User user = userService.findByEmailAndPass(trade.getEmail(), trade.getPass());
        if (user == null) {
            return false;
        } else {
            List<Card> cards = user.getCardList();
            List<History> historyList = user.getHistoryList();
            List<Wallet> walletList = user.getWalletList();
            for (Card card : cards) {
                cardService.delete(card);
            }
            for (History history : historyList) {
                historyService.delete(history);
            }
            for (Wallet wallet : walletList) {
                walletService.delete(wallet);
            }
            userService.delete(user);
            return true;
        }
    }


    @PostMapping(path = "/market")
    public MainObject getMarket() {
        ArrayList<Market> markets = new ArrayList<>();
//        ArrayList<String> nameCoins = new ArrayList<>();
        ArrayList<ListObject> listObjects = new ArrayList<>();
        RodObject rodObject = new RodObject();
//        nameCoins.add("Bitcoin");
//        nameCoins.add("Binancecoin");
//        nameCoins.add("Binance-usd");
//        nameCoins.add("Gala");
//        nameCoins.add("Ethereum");
//        nameCoins.add("Magic");
        List<Crypto> cryptoList = null;
        for (String name : nameCoin) {
            cryptoList = cryptoService.findByFullName(name);
            Market market = new Market();
            ArrayList<LocalDateTime> localDateTimes = new ArrayList<>();
            ArrayList<Double> list = new ArrayList<>();
            market.setName(cryptoList.get(0).getFullName());
            for (Crypto crypto : cryptoList) {
                localDateTimes.add(crypto.getDates());
                double roundedNum = Math.round(crypto.getPrice() * 10000.0) / 10000.0;
                list.add(roundedNum);
            }
            market.setDate(localDateTimes);
            market.setPrice(list);
            markets.add(market);
        }
        for (String name : nameCoin) {
            cryptoList = cryptoService.findByFullName(name);
            Crypto crypto = cryptoList.get(0);
            listObjects.add(new ListObject(crypto.getName(), crypto.getFullName(), crypto.getName().toLowerCase()));
        }
        rodObject.setListObjects(listObjects);
        MainObject mainObject = new MainObject();
        mainObject.setRodObject(rodObject);
        mainObject.setMarketList(markets);
        return mainObject;
    }

    @PostMapping(path = "/")
    public Simple getPriceCoin() {
        ArrayList<CoinsList> coinsLists = new ArrayList<>();
//        ArrayList<String> nameCoin = new ArrayList<>();
        Simple simple = new Simple();
//        nameCoin.add("Bitcoin");
//        nameCoin.add("Binancecoin");
//        nameCoin.add("Binance-usd");
//        nameCoin.add("Gala");
//        nameCoin.add("Ethereum");
//        nameCoin.add("Magic");
        for (String name : nameCoin) {
            CoinsList coinsList = new CoinsList();
            List<Crypto> cryptoList = cryptoService.findByFullName(name);
            Crypto crypto = cryptoList.get(cryptoList.size() - 1);
            if (name.equals("Bitcoin")) {
                simple.setPrice(Math.round(crypto.getPrice()));
            }
            coinsList.setName(crypto.getName() + "/USDT");
            double roundedNum = Math.round(crypto.getPrice() * 10000.0) / 10000.0;
            coinsList.setPrice(roundedNum);
            String result = String.format("%.2f", crypto.getChange());
            coinsList.setGap(result);
            coinsList.setSign(crypto.getChange() >= 0);
            coinsLists.add(coinsList);
        }
        simple.setCoinsLists(coinsLists);
        return simple;
    }

    // BNB, BUSD, USDT, MANERO(XMR), GALA, BETH, ETH, MAGIC, LITECOIN(LTC),
    // TRX(TRON), DASH, ATOM(COSMOS), FTM(FANTOM), 1INCH, LUNC(TERRA CLASSIC),
    // DOGE(DOGECOUIN), ZEC(ZCASH), BTC(BITCOIN), NEAR(NEAR PROTOCOL)
    // MATIC(POLYGON)
    public void insetTableCrypto() {
        int count = 0;
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

    @PostMapping(path = "/profile/history")
    public List<HistoryJson> getHistory(@RequestBody HistoryUpdate historyUpdate) {
        ArrayList<HistoryJson> historyJsons = new ArrayList<>();
        User user = userService.findByEmailAndPass(historyUpdate.getEmail(), historyUpdate.getPass());
        if (user == null) {
            return historyJsons;
        }
        List<History> historyList = user.getHistoryList();
        for (History history : historyList) {
            HistoryJson historyJson = new HistoryJson(history.getId(), history.getDates(), history.getNameWallet(), history.getNameCrypt(), history.getCounts());
            historyJsons.add(historyJson);
        }
        return historyJsons;
    }

    @PostMapping(path = "/profile/briefcase")
    public List<UpdateCrypt> getCryptList(@RequestBody HistoryUpdate historyUpdate) {
        ArrayList<UpdateCrypt> updateCrypts = new ArrayList<>();
        User user = userService.findByEmailAndPass(historyUpdate.getEmail(), historyUpdate.getPass());
        if (user == null) {
            return updateCrypts;
        }
        List<Wallet> wallets = user.getWalletList();
        for (Wallet wallet : wallets) {
            UpdateCrypt updateCrypt = new UpdateCrypt(wallet.getNameCrypt(), wallet.getCount(), wallet.getNameCrypt().toLowerCase());
            updateCrypts.add(updateCrypt);
        }
        return updateCrypts;
    }
}