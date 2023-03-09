package kz.runtime.backfor_mega.serivce;

import kz.runtime.backfor_mega.dao.HistoryRepository;
import kz.runtime.backfor_mega.entity.History;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HistoryImpl implements HistoryService{

    @Autowired
    private HistoryRepository historyRepository;
    @Override
    public void save(History history) {
        historyRepository.save(history);
    }
}
