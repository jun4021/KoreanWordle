package toy.mywordle.repository;

import toy.mywordle.domain.addcheckword;
import toy.mywordle.domain.requestword;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Transactional
public class RequestWordRepository {
    private final EntityManager em;

    public RequestWordRepository(EntityManager em) {
        this.em = em;
    }

    public void SaveWord(String word){
        requestword a = em.find(requestword.class,word);
        if(a==null) {
            requestword newWord = new requestword();
            Date now = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd(EEE) HH:mm:ss");
            newWord.setDate(format.format(now));
            newWord.setWord(word);
            em.persist(newWord);
        }
    }


    public void DeleteWord(String word){
        requestword a = em.find(requestword.class,word);
        if(a!=null) {
            em.remove(a);
        }
    }

    public List<requestword> findAll() {
        return em.createQuery("select m from requestword m order by m.date", requestword.class)
                .getResultList();
    }
}
