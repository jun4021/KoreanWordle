package toy.mywordle.repository;

import toy.mywordle.domain.addcheckword;
import toy.mywordle.domain.deleteword;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Transactional
public class DeleteWordRepository {
    private final EntityManager em;

    public DeleteWordRepository(EntityManager em) {
        this.em = em;
    }

    public void SaveWord(String word){
        deleteword a = em.find(deleteword.class,word);
        if(a==null) {
            deleteword newWord = new deleteword();
            Date now = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd(EEE) HH:mm:ss");
            newWord.setDate(format.format(now));
            newWord.setWord(word);
            em.persist(newWord);
        }
    }
    public boolean FindByWord(String word){
        deleteword a = em.find(deleteword.class,word);
        if(a == null){
            return false;
        }
        return true;
    }

    public void DeleteWord(String word){
        deleteword a = em.find(deleteword.class,word);
        if(a!=null) {
            em.remove(a);
        }
    }

    public List<deleteword> findAll() {
        return em.createQuery("select m from deleteword m order by m.date", deleteword.class)
                .getResultList();
    }
}

