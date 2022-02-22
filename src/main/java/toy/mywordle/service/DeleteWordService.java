package toy.mywordle.service;

import org.springframework.beans.factory.annotation.Autowired;
import toy.mywordle.domain.deleteword;
import toy.mywordle.domain.requestword;
import toy.mywordle.repository.DeleteWordRepository;
import toy.mywordle.repository.RequestWordRepository;

import java.util.List;

public class DeleteWordService {
    private final DeleteWordRepository deleteWordRepository;

    @Autowired
    public DeleteWordService(DeleteWordRepository deleteWordRepository) {
        this.deleteWordRepository = deleteWordRepository;
    }

    public boolean FindByword(String word){
        return deleteWordRepository.FindByWord(word);
    }
    public void SaveWord(boolean wait, String word){
        deleteWordRepository.SaveWord(wait, word);
    }
    public void DeleteWord(String word){
        deleteWordRepository.DeleteWord(word);
    }
    public List<deleteword> FindAll(){
        return deleteWordRepository.findAll();
    }
}
