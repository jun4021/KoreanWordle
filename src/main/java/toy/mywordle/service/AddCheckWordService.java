package toy.mywordle.service;

import org.springframework.beans.factory.annotation.Autowired;
import toy.mywordle.domain.addcheckword;
import toy.mywordle.repository.AddCheckWordRepository;
import toy.mywordle.repository.DailyAnswerRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class AddCheckWordService {
    private final AddCheckWordRepository addCheckWordRepository;

    @Autowired
    public AddCheckWordService(AddCheckWordRepository addCheckWordRepository) {
        this.addCheckWordRepository = addCheckWordRepository;
    }

    public void SaveWord(String word){
        addCheckWordRepository.SaveWord(word);
    }
    public void DeleteWord(String word){
        addCheckWordRepository.DeleteWord(word);
    }
    public List<addcheckword> FindAll(){
        return addCheckWordRepository.findAll();
    }
}
