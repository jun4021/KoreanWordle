package toy.mywordle.service;

import org.springframework.beans.factory.annotation.Autowired;
import toy.mywordle.repository.domain.Checkword;
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

    public boolean InsertWord(String newWord){
        // 중복 단어 확인
        return checkWordRepository.SaveWord(newWord);
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
