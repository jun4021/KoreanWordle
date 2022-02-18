package toy.mywordle.repository;
import toy.mywordle.domain.dailyanswer;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Transactional
public class DailyAnswerRepository {
    private final EntityManager em;

    public DailyAnswerRepository(EntityManager em) {
        this.em = em;
    }

    public void SaveRecord(dailyanswer record){
        em.persist(record);
    }

    public String FindAnswer(String date){
        dailyanswer word = em.find(dailyanswer.class,date);
        return word.getAnswer();
    }

}
