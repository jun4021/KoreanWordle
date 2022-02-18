package toy.mywordle.service;

import org.springframework.beans.factory.annotation.Autowired;

import toy.mywordle.domain.dailyrecord;
import toy.mywordle.repository.DailyRecordRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Transactional
public class DailyRecordService {
    private final DailyRecordRepository dailyRecordRepository;

    @Autowired
    public DailyRecordService(DailyRecordRepository dailyRecordRepository) {
        this.dailyRecordRepository = dailyRecordRepository;
    }

    public dailyrecord RecordLoading(LocalDateTime now){
        dailyrecord record = new dailyrecord();
        if(dailyRecordRepository.findByDate(now.toLocalDate().toString())!=null){
            record = dailyRecordRepository.findByDate(now.toLocalDate().toString());
            dailyRecordRepository.DelRecord(now.toLocalDate().toString());
        }
        return record;
    }

    public void SaveRecord(dailyrecord record){
        dailyRecordRepository.SaveRecord(record);
    }

    public List<dailyrecord> findAll(){
        return dailyRecordRepository.findAll();
    }
}
