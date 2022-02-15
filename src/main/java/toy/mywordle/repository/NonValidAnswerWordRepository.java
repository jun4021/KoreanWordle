package toy.mywordle.repository;

import toy.mywordle.domain.Answerword;
import toy.mywordle.domain.Checkword;
import toy.mywordle.domain.non_valid_answer_word;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Transactional
public class NonValidAnswerWordRepository {
    private final EntityManager em;

    public NonValidAnswerWordRepository(EntityManager em) {
        this.em = em;
    }

    public void SaveWord(String word){
        non_valid_answer_word a = em.find(non_valid_answer_word.class,word);
        if(a==null) {
            non_valid_answer_word newWord = new non_valid_answer_word();
            newWord.setWord(word);
            em.persist(newWord);
        }

    }
}
