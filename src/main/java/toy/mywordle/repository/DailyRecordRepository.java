package toy.mywordle.repository;


import toy.mywordle.repository.domain.addcheckword;
import toy.mywordle.repository.domain.dailyrecord;

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

    public void DelRecord(String date) {
        dailyrecord a = em.find(dailyrecord.class, date);
        if (a != null) {
            em.remove(a);
        }
    }

    public List<dailyrecord> findAll() {
        return em.createQuery("select m from dailyrecord m", dailyrecord.class)
                .getResultList();
    }
    public dailyrecord findByDate(String date){
        return em.find(dailyrecord.class, date);
    }

}
