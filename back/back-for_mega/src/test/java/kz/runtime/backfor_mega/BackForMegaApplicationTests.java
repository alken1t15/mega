package kz.runtime.backfor_mega;

import kz.runtime.backfor_mega.entity.User;
import kz.runtime.backfor_mega.entity.Wallet;
import kz.runtime.backfor_mega.entityjson.Trade;
import kz.runtime.backfor_mega.entityjson.UpdateAccount;
import kz.runtime.backfor_mega.entityjson.UpdatePass;
import kz.runtime.backfor_mega.serivce.UserService;
import kz.runtime.backfor_mega.serivce.WalletService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
class BackForMegaApplicationTests {

    @Autowired
    private UserService userService;

    @Autowired
    private WalletService walletService;

    @Test
    void textMethod() {
        String email = "kramzos.yt@gmail.com";
        String pass = "123456";
        User user = userService.findByEmailAndPass(email, pass);
        Assertions.assertEquals(email, user.getEmail());
    }

    @Test
    void testMethodTrue() {
        Registration registration = new Registration();
        registration.setEmail("fsdf@mail.ru");
        registration.setPass("fsdfewf54353");
        registration.setPhone("6353544353");
        registration.setUserName("Fdsfsd");
        registration.setBirthday(LocalDate.now());
        Boolean bol = testMethodTest(registration);
        Assertions.assertEquals(bol, true);
    }

    @Test
    void testMethodFalse() {
        Registration registration = new Registration();
        String email = "kramzos.yt@gmail.com";
        String pass = "123456";
        registration.setEmail(email);
        registration.setPass(pass);
        registration.setPhone("6353544353");
        registration.setUserName("Fdsfsd");
        registration.setBirthday(LocalDate.now());
        Boolean bol = testMethodTest(registration);
        Assertions.assertEquals(bol, false);
    }

    @Test
    void signInUserTrue() {
        Registration registration = new Registration();
        String email = "kramzos.yt@gmail.com";
        String pass = "123456";
        registration.setEmail(email);
        registration.setPass(pass);
        registration.setPhone("6353544353");
        registration.setUserName("Fdsfsd");
        registration.setBirthday(LocalDate.now());
        User user = signInUser(registration);
        Assertions.assertEquals(user.getPass(), registration.getPass());
    }

    @Test
    void signInUserFalse() {
        Registration registration = new Registration();
        registration.setEmail("fsdf@mail.ru");
        registration.setPass("fsdfewf54353");
        registration.setPhone("6353544353");
        registration.setUserName("Fdsfsd");
        registration.setBirthday(LocalDate.now());
        User user = signInUser(registration);
        Assertions.assertNotEquals(user.getPass(), registration.getPass());
    }

    @Test
    void editMyProfileTrue() {
        UpdateAccount updateAccount = new UpdateAccount();
        updateAccount.setUserName("Fdsfsd");
        updateAccount.setAge(30);
        Boolean bol = editMyProfile(updateAccount);
        Assertions.assertEquals(bol, true);
    }

    @Test
    void editMyProfileFalse() {
        UpdateAccount updateAccount = new UpdateAccount();
        updateAccount.setUserName("Fdsfsdfdsf");
        updateAccount.setAge(30);
        Boolean bol = editMyProfile(updateAccount);
        Assertions.assertEquals(bol, false);
    }


    @Test
    void editMyPassTrue() {
        UpdatePass updatePass = new UpdatePass();
        updatePass.setUserName("Fdsfsd");
        updatePass.setPass("fsdfewf54353");
        updatePass.setNewPass("66666");
        Boolean bol = editMyPass(updatePass);
        Assertions.assertEquals(bol, true);
    }

    @Test
    void editMyPassFalse() {
        UpdatePass updatePass = new UpdatePass();
        updatePass.setUserName("Fdsfsdfdsfds");
        updatePass.setPass("fsdfewf54353");
        updatePass.setNewPass("66666");
        Boolean bol = editMyPass(updatePass);
        Assertions.assertEquals(bol, false);
    }

    @Test
    void editMyPassFalse2() {
        UpdatePass updatePass = new UpdatePass();
        updatePass.setUserName("Fdsfsd");
        updatePass.setPass("fsdfewf543536755675");
        updatePass.setNewPass("66666");
        Boolean bol = editMyPass(updatePass);
        Assertions.assertEquals(bol, false);
    }

    @Test
    void deleteMyProfileTrue() {
        Boolean bol = deleteMyProfile("Fdsfsd");
        Assertions.assertEquals(bol, true);
    }

    @Test
    void deleteMyProfileFalse() {
        Boolean bol = deleteMyProfile("Fdsfsdfsdfds");
        Assertions.assertEquals(bol, false);
    }

    public Boolean testMethodTest(Registration registration) {
        User user = userService.findByEmailAndPass(registration.getEmail(), registration.getPass());
        if (user == null) {
            user = new User(registration.getUserName(), registration.getPass(), registration.getEmail(), registration.getPhone(), registration.getBirthday());
            user.setRegisterAccount(LocalDate.now());
            userService.save(user);
            return true;
        } else {
            System.out.println("Такой аккаунт есть");
            return false;
        }
    }

    public User signInUser(Registration registration) {
        User user = userService.findByEmailAndPass(registration.getEmail(), registration.getPass());
        if (user != null) {
            return user;
        }
        return new User();
    }

//    public Boolean getStatusProfile(Trade trade) {
//        User user = userService.findByUserName(trade.getUserName());
//        List<Wallet> wallets = user.getWalletList();
//        for (Wallet wallet : wallets) {
//            if (wallet.getNameCrypt().equals(trade.getCrypt())) {
//                double sum = wallet.getCount() - trade.getCount();
//                if (sum <= 0) {
//                    return false;
//                }
//                wallet.setCount(sum);
//                walletService.save(wallet);
//                return true;
//            }
//        }
//        return false;
//    }

    public Boolean editMyProfile(UpdateAccount updateAccount) {
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

    public Boolean editMyPass(UpdatePass updatePass) {
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

    public Boolean deleteMyProfile(String userName) {
        User user = userService.findByUserName(userName);
        if (user == null) {
            return false;
        } else {
            userService.delete(user);
            return true;
        }
    }
}