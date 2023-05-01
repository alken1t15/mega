package kz.runtime.backfor_mega.serivce;

import jakarta.mail.MessagingException;
import kz.runtime.backfor_mega.Registration;
import kz.runtime.backfor_mega.entity.Card;
import kz.runtime.backfor_mega.entity.History;
import kz.runtime.backfor_mega.entity.User;
import kz.runtime.backfor_mega.entity.Wallet;
import kz.runtime.backfor_mega.entityjson.Trade;
import kz.runtime.backfor_mega.entityjson.UpdatePass;
import kz.runtime.backfor_mega.entityjson.UserJson;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class UserServiceProfile {

    private final UserService userService;

    private final HistoryService historyService;

    private final  CardService cardService;

    private final WalletService walletService;

    private final EmailService emailService;

    public UserServiceProfile(UserService userService, HistoryService historyService, CardService cardService, WalletService walletService, EmailService emailService) {
        this.userService = userService;
        this.historyService = historyService;
        this.cardService = cardService;
        this.walletService = walletService;
        this.emailService = emailService;
    }

    public Boolean accountRegistration(Registration registration) {
        User user = userService.findByEmailAndPass(registration.getEmail(), registration.getPass());
        if (user == null) {
            user = new User(registration.getUserName(), registration.getPass(), registration.getEmail(), registration.getPhone(), registration.getBirthday());
            user.setRegisterAccount(LocalDate.now());
            userService.save(user);
            try {
                emailService.sendHtmlEmail(user.getEmail(), "Успешная регистрация", "<!DOCTYPE html>\n" +
                        "<html>\n" +
                        "<head>\n" +
                        "    <title>Поздравление с регистрацией</title>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "    <h1>Поздравляем!</h1>\n" +
                        "    <p>Вы успешно зарегистрировались на нашем сайте. Мы рады приветствовать вас в нашем сообществе!</p>\n" +
                        "    <p>Вы можете войти на сайт, используя свои учетные данные:</p>\n" +
                        "    <ul>\n" +
                        "        <li>Логин: "+user.getEmail()+" </li>\n" +
                        "        <li>Пароль: "+user.getPass()+"</li>\n" +
                        "    </ul>\n" +
                        "    <p>Если у вас возникнут какие-либо вопросы или проблемы, пожалуйста, не стесняйтесь обращаться к нашей службе поддержки.</p>\n" +
                        "    <p>Спасибо за регистрацию!</p>\n" +
                        "</body>\n" +
                        "</html>");
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }finally {
                return true;
            }
        } else {
            return false;
        }
    }

    public UserJson userAccount(Registration registration) {
        User user = userService.findByEmailAndPass(registration.getEmail(), registration.getPass());
        if (user != null) {
            byte[] img;
            try {
                File file = new File(user.getImg());
                FileInputStream fileInputStream = new FileInputStream(file);
                img = fileInputStream.readAllBytes();
            } catch (NullPointerException e) {
                return new UserJson(user.getId(), user.getUserName(), user.getFirstName(), user.getSecondName(), user.getMiddle_name(), user.getAge(), user.getAddress(), user.getPhone(), user.getPass(), user.getEmail(), user.getBirthday());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return new UserJson(user.getId(), user.getUserName(), user.getFirstName(), user.getSecondName(), user.getMiddle_name(), user.getAge(), user.getAddress(), user.getPhone(), user.getPass(), user.getEmail(), user.getBirthday(), img);
        }
        return new UserJson();
    }

    public Boolean changingYourAccount(String userName,
                                       String userNameModified,
                                       String firstName,
                                       String secondName,
                                       String lastName,
                                       String age,
                                       MultipartFile image) {
        User user = userService.findByUserName(userName);
        if (user == null) {
            return false;
        } else {
            user.setUserName(userNameModified);
            user.setFirstName(firstName);
            user.setSecondName(secondName);
            user.setMiddle_name(lastName);
            user.setAge(Integer.valueOf(age));
            try {
                byte[] bytes;
                bytes = image.getBytes();
                File file = new File("image/" + image.getOriginalFilename());
                FileOutputStream fos;
                fos = new FileOutputStream(file);
                fos.write(bytes);
                fos.close();
            } catch (IOException e) {
                userService.save(user);
                throw new RuntimeException(e);
            }
            user.setImg("image/" + image.getOriginalFilename());
            userService.save(user);
            return true;
        }
    }
    public Boolean changingTheAccountPassword(UpdatePass updatePass){
        User user = userService.findByEmailAndPass(updatePass.getEmail(), updatePass.getPass());
        if (user != null) {
            user.setPass(updatePass.getNewPass());
            userService.save(user);
            return true;
        } else {
            return false;
        }
    }

    public Boolean deletingAUserAccount(Trade trade){
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
}