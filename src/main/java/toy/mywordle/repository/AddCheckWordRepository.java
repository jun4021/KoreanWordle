package toy.mywordle.repository;

import toy.mywordle.domain.addcheckword;

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

    public boolean SaveWord(String word){
        addcheckword a = em.find(addcheckword.class,word);
        if(a==null) {
            addcheckword newWord = new addcheckword();
            Date now = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd(EEE) HH:mm:ss");
            newWord.setDate(format.format(now));
            newWord.setWord(word);
            newWord.setCount(1);
            em.persist(newWord);
            return true;
        }
        else{
            a.setCount(a.getCount()+1);
        }
        return false;
    }


    public void DeleteWord(String word){
        addcheckword a = em.find(addcheckword.class,word);
        if(a!=null) {
            em.remove(a);
        }
    }

    public List<addcheckword> findAll() {
        return em.createQuery("select m from addcheckword m where m.count > 5 order by m.count DESC", addcheckword.class)
                .getResultList();
    }
}

