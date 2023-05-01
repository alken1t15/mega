package kz.runtime.backfor_mega;

import jakarta.mail.MessagingException;
import kz.runtime.backfor_mega.dao.HistoryRepository;
import kz.runtime.backfor_mega.entity.*;
import kz.runtime.backfor_mega.entityjson.*;
import kz.runtime.backfor_mega.serivce.*;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
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

    private final CardService cardService;

    private final CryptoService cryptoService;

    private final HistoryService historyService;

    private final UserService userService;

    private final WalletService walletService;

    private final HistoryRepository historyRepository;

    private final EmailService emailService;

    private final UserServiceProfile userServiceProfile;

    public MyController(CardService cardService, CryptoService cryptoService, HistoryService historyService, UserService userService, WalletService walletService, HistoryRepository historyRepository, EmailService emailService,UserServiceProfile userServiceProfile) {
        this.cardService = cardService;
        this.cryptoService = cryptoService;
        this.historyService = historyService;
        this.userService = userService;
        this.walletService = walletService;
        this.historyRepository = historyRepository;
        this.emailService = emailService;
        this.userServiceProfile = userServiceProfile;
    }

    @GetMapping(path = "/maxim")
    public void handleExampleRequest() {
        insetTableCrypto();
    }


    @PostMapping(path = "/signup")
    public Boolean registration(@RequestBody Registration registration) {
      return userServiceProfile.accountRegistration(registration);
    }


    @PostMapping(path = "/signin")
    public UserJson signInUser(@RequestBody Registration registration) {
        return userServiceProfile.userAccount(registration);
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
    public Boolean editMyProfile(@RequestPart String userName,
                                 @RequestPart String userNameModified,
                                 @RequestPart String firstName,
                                 @RequestPart String secondName,
                                 @RequestPart String lastName,
                                 @RequestPart String age,
                                 @RequestParam("file") MultipartFile image) {
        return userServiceProfile.changingYourAccount(userName,userNameModified,firstName,secondName,lastName,age,image);
    }

    @PostMapping(path = "/profile/private")
    public Boolean editMyPass(@RequestBody UpdatePass updatePass) {
      return userServiceProfile.changingTheAccountPassword(updatePass);
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
            try {
                emailService.sendHtmlEmail(user.getEmail(), "Покупка криптовалюты", "<!DOCTYPE html>\n" +
                        "<html>\n" +
                        "<head>\n" +
                        "    <title>Поздравление с покупкой криптовалюты</title>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "    <h1>Поздравляем!</h1>\n" +
                        "    <p>Вы успешно купили криптовалюту на нашей платформе. Мы рады, что вы выбрали нас для осуществления своих инвестиций в криптовалюты.</p>\n" +
                        "    <p>Ваша покупка:</p>\n" +
                        "    <ul>\n" +
                        "        <li>Количество: "+ wallet.getCount()+" </li>\n" +
                        "        <li>Тип: "+ wallet.getNameCrypt()+"</li>\n" +
                        "    </ul>\n" +
                        "    <p>Мы надеемся, что ваша покупка принесет вам высокую доходность. Если у вас возникнут какие-либо вопросы или проблемы, пожалуйста, не стесняйтесь обращаться к нашей службе поддержки.</p>\n" +
                        "    <p>Спасибо за вашу покупку!</p>\n" +
                        "</body>\n" +
                        "</html>");
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }finally {
                return true;
            }
        }
    }

    @PostMapping(path = "/profile/delete")
    public Boolean deleteMyProfile(@RequestBody Trade trade) {
        return userServiceProfile.deletingAUserAccount(trade);
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