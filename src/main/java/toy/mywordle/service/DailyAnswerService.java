package toy.mywordle.service;

import org.springframework.beans.factory.annotation.Autowired;
import toy.mywordle.domain.dailyanswer;
import toy.mywordle.repository.DailyAnswerRepository;


import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Transactional
public class DailyAnswerService {
    private final DailyAnswerRepository dailyAnswerRepository;

    @Autowired
    public DailyAnswerService(DailyAnswerRepository dailyAnswerRepository) {
        this.dailyAnswerRepository = dailyAnswerRepository;
    }

    public void SaveWord(LocalDateTime date, String word){
        dailyanswer answerRecord = new dailyanswer();
        answerRecord.setDate(date.toLocalDate().toString());
        answerRecord.setAnswer(word);
        dailyAnswerRepository.SaveRecord(answerRecord);
    }
    public String FindAnswer(LocalDateTime now){
        String date = now.toLocalDate().toString();
        return dailyAnswerRepository.FindAnswer(date);
    }
}
