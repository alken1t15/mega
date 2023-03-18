package kz.runtime.backfor_mega.serivce;

import kz.runtime.backfor_mega.dao.CardRepository;
import kz.runtime.backfor_mega.entity.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardImp implements CardService {

    @Autowired
    private CardRepository cardRepository;

    @Override
    public void save(Card card) {
        cardRepository.save(card);
    }

    @Override
    public Card findByNumberAndDataNameAndSvv(String number, String dataName, String svv) {
        return cardRepository.findByNumberAndDataNameAndSvv(number, dataName, svv);
    }

    @Override
    public void delete(Card card) {
        cardRepository.delete(card);
    }

}