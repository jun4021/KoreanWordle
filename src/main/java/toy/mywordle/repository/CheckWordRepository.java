package toy.mywordle.repository;

import toy.mywordle.domain.Checkword;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class CheckWordRepository {
    private final EntityManager em;

    public CheckWordRepository(EntityManager em) {
        this.em = em;
    }

    public Checkword SaveWord(String word){
        Checkword newWord = new Checkword();
        newWord.setWord(word);
        newWord.setCount(0);
        em.persist(newWord);
        return newWord;
    }


    public Optional<Checkword> FindByword(String findword){
        List<Checkword> result = em.createQuery("select w from Checkword w where w.word = :fword", Checkword.class)
                .setParameter("fword",findword)
                .getResultList();
        Optional<Checkword> resultWord = result.stream().findAny();
        return resultWord;
    }

}
