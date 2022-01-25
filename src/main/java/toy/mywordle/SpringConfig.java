package toy.mywordle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import toy.mywordle.repository.WordRepository;
import toy.mywordle.service.AnswerToColor;

import javax.persistence.EntityManager;

@Configuration
public class SpringConfig {
    private final EntityManager em;

    @Autowired
    public SpringConfig(EntityManager em) {
        this.em = em;
    }

    @Bean
    public AnswerToColor answertocolor(){
        return new AnswerToColor();
    }
    @Bean
    public WordRepository wordRepository(){
        return new WordRepository(em);
    }

}
