package kz.runtime.backfor_mega;

import kz.runtime.backfor_mega.entity.Card;
import kz.runtime.backfor_mega.serivce.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "user/login")
public class MyController {

    @Autowired
    private CardService cardService;

    @GetMapping(path = "/test")
    public String testMethod(){
        Card card = new Card("7777777777777777","05/27","680");
        cardService.save(card);

        return "Fdfsd";
    }
}
