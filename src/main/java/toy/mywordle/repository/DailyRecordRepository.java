package toy.mywordle.repository;


import toy.mywordle.domain.dailyrecord;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
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
    public Integer CalSuccessRate(String date){
        dailyrecord a = em.find(dailyrecord.class, date);
        if(a==null){
            return 0;
        }
        else{
            try {
                Integer sol = 100 * a.getCorrectanswer() / a.gettrystart();
                return sol;
            }
            catch (Exception e){
                return 0;
            }

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
