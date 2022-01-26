package toy.mywordle.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import toy.mywordle.domain.Answerword;
import toy.mywordle.repository.AnswerWordRepository;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class WordListServiceTest {

    @Autowired
    AnswerWordService answerWordService;
    @Autowired
    AnswerWordRepository answerWordRepository;


    @Test
    void insertWord() {
        //given
        String newword = "운동화";

        //when
        Answerword word = answerWordService.InsertWord(newword);
        //then
        Answerword wordcheck = answerWordService.SelectWordByWord(newword).get();
        assertEquals(newword, wordcheck.getWord());
    }

    @Test
    void selectWordByCode() {
        for(int i=0;i<5;i++) {

            Integer code = answerWordService.ChooseRandomId();
            Answerword word1 = answerWordService.SelectWordByCode(code);
            System.out.println(code);
        }

    }
}
