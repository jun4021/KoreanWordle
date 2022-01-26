package toy.mywordle.service;

import org.springframework.beans.factory.annotation.Autowired;
import toy.mywordle.domain.Answerword;
import toy.mywordle.repository.AnswerWordRepository;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Random;

@Transactional
public class AnswerWordService {
    private final AnswerWordRepository answerWordRepository;

    @Autowired
    public AnswerWordService(AnswerWordRepository answerWordRepository) {
        this.answerWordRepository = answerWordRepository;
    }

    public Integer ChooseRandomId(){
        Long total = answerWordRepository.CountWordList();

        Random random = new Random();
        return random.nextInt(total.intValue())+1;
    }
    public Answerword InsertWord(String newWord){
        // 중복 단어 확인
        ValidDuplicateWord(newWord);
        return answerWordRepository.SaveWord(newWord);
    }
    private void ValidDuplicateWord(String word){
        Optional<Answerword> result = SelectWordByWord(word);
        result.ifPresent(m -> {
            throw new IllegalStateException("단어가 이미 있습니다.");
        });
    }
    public Answerword SelectWordByCode(Integer code){
        return answerWordRepository.FindById(code);
    }
    public Optional<Answerword> SelectWordByWord(String findWord){
        return answerWordRepository.FindByword(findWord);
    }



}
