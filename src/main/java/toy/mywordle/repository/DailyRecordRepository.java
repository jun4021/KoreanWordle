package toy.mywordle.repository;


import toy.mywordle.domain.dailyrecord;
import toy.mywordle.domain.non_valid_answer_word;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class DailyRecordRepository {
    private final EntityManager em;

    public DailyRecordRepository(EntityManager em) {
        this.em = em;
    }

    public void SaveRecord(dailyrecord record){
        em.persist(record);
    }

    public List<dailyrecord> findAll() {
        return em.createQuery("select m from dailyrecord m", dailyrecord.class)
                .getResultList();
    }

}
