package kz.runtime.backfor_mega.serivce;

import kz.runtime.backfor_mega.entity.Card;
import kz.runtime.backfor_mega.entity.History;
import kz.runtime.backfor_mega.entity.User;

public interface HistoryService {

    void save(History history);

    void delete(History history);
}