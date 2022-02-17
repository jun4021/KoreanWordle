package toy.mywordle.repository;

import toy.mywordle.domain.*;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Transactional
public class AddCheckWordRepository {
    private final EntityManager em;

    public AddCheckWordRepository(EntityManager em) {
        this.em = em;
    }

    public void SaveWord(String word){
        addcheckword a = em.find(addcheckword.class,word);
        if(a==null) {
            addcheckword newWord = new addcheckword();
            Date now = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd(EEE) HH:mm:ss");
            newWord.setDate(format.format(now));
            newWord.setWord(word);
            em.persist(newWord);

        }
    }

    public void DeleteWord(String word){
        addcheckword a = em.find(addcheckword.class,word);
        if(a!=null) {
            em.remove(a);
        }
    }

    public List<addcheckword> findAll() {
        return em.createQuery("select m from addcheckword m order by m.date", addcheckword.class)
                .getResultList();
    }
}

