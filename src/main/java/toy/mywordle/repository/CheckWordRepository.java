package toy.mywordle.repository;

import toy.mywordle.domain.Checkword;

import javax.persistence.EntityManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class CheckWordRepository {
    private final EntityManager em;

    public CheckWordRepository(EntityManager em) {
        this.em = em;
    }

    public boolean SaveWord(String word){
        Checkword a = em.find(Checkword.class,word);
        if(a==null) {
            Checkword newWord = new Checkword();
            Date now = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            newWord.setWord(word);
            newWord.setCount(0);
            newWord.setAdddate(format.format(now));
            em.persist(newWord);
            return true;
        }
        return false;
    }

    public void PlusCount(String word){
        Checkword a = em.find(Checkword.class,word);
        a.setCount(a.getCount()+1);
    }


    public Optional<Checkword> FindByword(String findword){
        List<Checkword> result = em.createQuery("select w from Checkword w where w.word = :fword", Checkword.class)
                .setParameter("fword",findword)
                .getResultList();
        Optional<Checkword> resultWord = result.stream().findAny();
        return resultWord;
    }

}
