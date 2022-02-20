package toy.mywordle.service;

import org.springframework.beans.factory.annotation.Autowired;
import toy.mywordle.domain.addcheckword;
import toy.mywordle.domain.requestword;
import toy.mywordle.repository.AddCheckWordRepository;
import toy.mywordle.repository.RequestWordRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class RequestWordService {
    private final RequestWordRepository requestWordRepository;

    @Autowired
    public RequestWordService(RequestWordRepository requestWordRepository) {
        this.requestWordRepository = requestWordRepository;
    }

    public boolean SaveWord(String word){
        return requestWordRepository.SaveWord(word);
    }
    public void DeleteWord(String word){
        requestWordRepository.DeleteWord(word);
    }
    public List<requestword> FindAll(){
        return requestWordRepository.findAll();
    }
}
