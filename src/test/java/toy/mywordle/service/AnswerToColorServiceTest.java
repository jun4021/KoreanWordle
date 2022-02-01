package toy.mywordle.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import toy.mywordle.controller.ColorInfo;
import toy.mywordle.repository.AnswerWordRepository;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class AnswerToColorServiceTest {

    @Autowired
    AnswerWordService answerWordService;
    @Autowired
    AnswerToColorService answerToColorService;
    @Autowired
    AnswerWordRepository answerWordRepository;

    @Test
    void recordColorInfo() {
        String correct = "원자력";
        String input = "운동화";
        ColorInfo res = answerToColorService.RecordColorInfo(correct, input);
        System.out.println(res.getGreen_keyboard());
        System.out.println(res.getYellow_keyboard());
        System.out.println(res.getGrey_keyboard());



    }
}