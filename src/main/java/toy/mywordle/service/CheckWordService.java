package toy.mywordle.service;

import org.springframework.beans.factory.annotation.Autowired;
import toy.mywordle.domain.Checkword;
import toy.mywordle.repository.CheckWordRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public class CheckWordService {
    private final CheckWordRepository checkWordRepository;

    @Autowired
    public CheckWordService(CheckWordRepository checkWordRepository) {
        this.checkWordRepository = checkWordRepository;
    }

    public Checkword InsertWord(String newWord){
        // 중복 단어 확인
        ValidDuplicateWord(newWord);
        return checkWordRepository.SaveWord(newWord);
    }
    private void ValidDuplicateWord(String word){
        Optional<Checkword> result = SelectWordByWord(word);
        result.ifPresent(m -> {
            throw new IllegalStateException("단어가 이미 있습니다.");
        });
    }
    public Optional<Checkword> SelectWordByWord(String findWord){
        return checkWordRepository.FindByword(findWord);
    }

    public boolean InCheckWord(String word){
        if (checkWordRepository.FindByword(word).isPresent()) {
            return true;
        } else {
            return false;
        }
    }

}
