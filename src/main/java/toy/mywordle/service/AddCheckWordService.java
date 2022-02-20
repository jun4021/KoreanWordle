package toy.mywordle.service;

import org.springframework.beans.factory.annotation.Autowired;
import toy.mywordle.domain.addcheckword;
import toy.mywordle.repository.AddCheckWordRepository;
import toy.mywordle.repository.DailyAnswerRepository;
import toy.mywordle.repository.DeleteWordRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class AddCheckWordService {
    private final AddCheckWordRepository addCheckWordRepository;
    private final DeleteWordRepository deleteWordRepository;

    @Autowired
    public AddCheckWordService(AddCheckWordRepository addCheckWordRepository, DeleteWordRepository deleteWordRepository) {
        this.addCheckWordRepository = addCheckWordRepository;
        this.deleteWordRepository = deleteWordRepository;
    }

    public boolean SaveWord(String word){
        if(!deleteWordRepository.FindByWord(word)){
            return addCheckWordRepository.SaveWord(word);
        }
        return false;
    }
    public void DeleteWord(String word){
        addCheckWordRepository.DeleteWord(word);
    }
    public List<addcheckword> FindAll(){
        return addCheckWordRepository.findAll();
    }
}
