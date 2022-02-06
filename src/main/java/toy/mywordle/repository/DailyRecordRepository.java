package toy.mywordle.repository;


import toy.mywordle.domain.dailyrecord;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Transactional
public class DailyRecordRepository {
    private final EntityManager em;

    public DailyRecordRepository(EntityManager em) {
        this.em = em;
    }

    public void SaveRecord(dailyrecord record){
        em.persist(record);
    }


}
