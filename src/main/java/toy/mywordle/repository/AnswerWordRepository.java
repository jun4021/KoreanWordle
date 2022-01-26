package toy.mywordle.repository;

import toy.mywordle.domain.Answerword;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class AnswerWordRepository {

    private final EntityManager em;

    public AnswerWordRepository(EntityManager em) {
        this.em = em;
    }

    public Answerword SaveWord(String word){
        Answerword newWord = new Answerword();
        newWord.setWord(word);
        em.persist(newWord);

        return newWord;
    }

    public Answerword FindById(Integer id){
        Answerword word = em.find(Answerword.class,id);
        return word;
    }

    public Optional<Answerword> FindByword(String findword){
        List<Answerword> result = em.createQuery("select w from Answerword w where w.word = :fword", Answerword.class)
                .setParameter("fword",findword)
                .getResultList();
        Optional<Answerword> resultWord = result.stream().findAny();
        return resultWord;
    }

    public Long CountWordList(){
        return em.createQuery("select count(w) from Answerword w",Long.class)
                .getSingleResult();
    }
}
