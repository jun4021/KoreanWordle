package toy.mywordle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import toy.mywordle.repository.AnswerWordRepository;
import toy.mywordle.repository.CheckWordRepository;
import toy.mywordle.service.AnswerToColorService;
import toy.mywordle.service.AnswerWordService;
import toy.mywordle.service.CheckWordService;

import javax.persistence.EntityManager;

@Configuration
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
    public CheckWordRepository checkWordRepository(){
        return new CheckWordRepository(em);
    }

}
