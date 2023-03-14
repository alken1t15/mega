package kz.runtime.backfor_mega;

import kz.runtime.backfor_mega.entity.User;
import kz.runtime.backfor_mega.serivce.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
class BackForMegaApplicationTests {

    @Autowired
    private UserService userService;

    @Test
    void textMethod() {
        String email = "kramzos.yt@gmail.com";
        String pass = "123456";
        User user = userService.findByEmailAndPass(email, pass);
        Assertions.assertEquals(email,user.getEmail());
    }

    @Test
    void testMethodTrue(){
        MyController myController = new MyController();
        Registration registration = new Registration();
        registration.setEmail("fsdf@mail.ru");
        registration.setPass("fsdfewf54353");
        registration.setPhone("6353544353");
        registration.setUserName("Fdsfsd");
        registration.setBirthday(LocalDate.now());
       Boolean bol = myController.testMethod(registration);
       Assertions.assertEquals(bol,false);
    }

    @Test
    void testMethodFalse(){
        MyController myController = new MyController();
        Registration registration = new Registration();
        String email = "kramzos.yt@gmail.com";
        String pass = "123456";
        registration.setPhone("6353544353");
        registration.setUserName("Fdsfsd");
        registration.setBirthday(LocalDate.now());
        Boolean bol = myController.testMethod(registration);
        Assertions.assertEquals(bol,true);
    }

}
