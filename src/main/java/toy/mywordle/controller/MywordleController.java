package toy.mywordle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import toy.mywordle.service.AnswerToColorService;
import toy.mywordle.service.AnswerWordService;
import toy.mywordle.service.CheckWordService;

import java.time.LocalDateTime;

@Controller
public class MywordleController {
    public LocalDateTime startTime = LocalDateTime.now()
            .withYear(2022)
            .withMonth(2)
            .withDayOfMonth(1)
            .withHour(0)
            .withMinute(0)
            .withSecond(0)
            .withNano(0);

    private final AnswerToColorService answerToColorService;
    private final AnswerWordService answerWordService;
    private final CheckWordService checkWordService;
    private String correctAnswer;

    @Autowired
    public MywordleController(AnswerToColorService answerToColorService, AnswerWordService answerWordService, CheckWordService checkWordService) {
        this.answerToColorService = answerToColorService;
        this.answerWordService = answerWordService;
        this.checkWordService = checkWordService;
    }



    @GetMapping("/")
    public String home(){
        // DB에서 정답 불러오기


        //Integer code = answerWordService.ChooseRandomId();
        correctAnswer = "신호등";
    //answerWordService.SelectWordByCode(code).getWord();
        //System.out.println(correctAnswer);
        return "home";
    }

    @PostMapping("/correct")
    @ResponseBody
    public ColorInfo CheckCorrect(InputAnswer ob){

        ColorInfo result = new ColorInfo();
        String inputAnswer = ob.getAnswer();


        // DB에 단어 list 확인
        if(!checkWordService.InCheckWord(inputAnswer)) {
            result.setValidWord(false);

            return result;
        }

        // 있을 시 정답이랑 비교
        result = answerToColorService.RecordColorInfo(correctAnswer,inputAnswer);
        if(correctAnswer.equals(inputAnswer)){
            result.setCorrect(true);
            return result;
        }
        result.setCorrect(false);

        return result;
    }
}
