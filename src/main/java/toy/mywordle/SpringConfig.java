package toy.mywordle;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import toy.mywordle.service.AnswerToColor;

@Configuration
public class SpringConfig {
    @Bean
    public AnswerToColor answertocolor(){
        return new AnswerToColor();
    }

}
