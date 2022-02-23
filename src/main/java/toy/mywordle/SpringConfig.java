package toy.mywordle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import toy.mywordle.repository.*;
import toy.mywordle.service.*;

import javax.persistence.EntityManager;

@Configuration
@EnableScheduling
public class SpringConfig {
    private final EntityManager em;

    @Autowired
    public SpringConfig(EntityManager em) {
        this.em = em;
    }

    @Bean
    public AnswerToColorService answertocolor(){
        return new AnswerToColorService();
    }
    @Bean
    public AnswerWordService answerWordService(){
        return new AnswerWordService(answerWordRepository());
    }
    @Bean
    public AnswerWordRepository answerWordRepository(){
        return new AnswerWordRepository(em);
    }
    @Bean
    public CheckWordService checkWordService(){
        return new CheckWordService(checkWordRepository());
    }
    @Bean
    public DailyRecordService dailyRecordService(){
        return new DailyRecordService(dailyRecordRepository());
    }
    @Bean
    public DailyAnswerService dailyAnswerService(){ return new DailyAnswerService(dailyAnswerRepository());}
    @Bean
    public AddCheckWordService addCheckWordService(){
        return new AddCheckWordService(addCheckWordRepository(),deleteWordRepository());
    }
    @Bean
    public CheckWordRepository checkWordRepository(){
        return new CheckWordRepository(em);
    }
    @Bean
    public DailyRecordRepository dailyRecordRepository(){
        return new DailyRecordRepository(em);
    }
    @Bean
    public DailyAnswerRepository dailyAnswerRepository(){
        return new DailyAnswerRepository(em);
    }
    @Bean
    public AddCheckWordRepository addCheckWordRepository(){
        return new AddCheckWordRepository(em);
    }
    @Bean
    public RequestWordRepository requestWordRepository(){
        return new RequestWordRepository(em);
    }
    @Bean
    public RequestWordService requestWordService(){
        return new RequestWordService(requestWordRepository());
    }
    @Bean
    public DeleteWordRepository deleteWordRepository(){
        return new DeleteWordRepository(em);
    }
    @Bean
    public DeleteWordService deleteWordService(){
        return new DeleteWordService(deleteWordRepository());
    }
}
