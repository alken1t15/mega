package kz.runtime.backfor_mega.serivce;

import kz.runtime.backfor_mega.dao.HistoryRepository;
import kz.runtime.backfor_mega.entity.History;
import kz.runtime.backfor_mega.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HistoryImpl implements HistoryService {

    @Autowired
    private HistoryRepository historyRepository;

    @Override
    public void save(History history) {
        historyRepository.save(history);
    }

    @Override
    public void delete(History history) {
        historyRepository.delete(history);
    }
}