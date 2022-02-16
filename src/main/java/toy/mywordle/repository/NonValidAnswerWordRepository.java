package toy.mywordle.repository;

import toy.mywordle.domain.Answerword;
import toy.mywordle.domain.Checkword;
import toy.mywordle.domain.dailyrecord;
import toy.mywordle.domain.non_valid_answer_word;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

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

    public void DeleteWord(String word){
        non_valid_answer_word DelWord = em.find(non_valid_answer_word.class,word);
        if(DelWord!=null) {
            em.remove(DelWord);
        }

    }

    public List<non_valid_answer_word> findAll() {
        return em.createQuery("select m from non_valid_answer_word m", non_valid_answer_word.class)
                .getResultList();
    }
}
