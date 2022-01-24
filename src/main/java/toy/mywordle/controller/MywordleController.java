package toy.mywordle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import toy.mywordle.service.AnswerToColor;

import java.util.ArrayList;

@Controller
public class MywordleController {
    private final AnswerToColor answertocolor;

    @Autowired
    public MywordleController(AnswerToColor answertocolor){
        this.answertocolor = answertocolor;
    }

    @GetMapping("/")
    public String home(){
        return "home";
    }

    @PostMapping("/correct")
    @ResponseBody
    public ColorInfo CheckCorrect(InputAnswer ob){

        ColorInfo result = new ColorInfo();
        String inputAnswer = ob.getAnswer();
        // DB에 단어 list 확인
        String correctAnswer = "박준영";
        // 있을 시 정답이랑 비교
        if(correctAnswer.equals(inputAnswer)){
            result.setCorrect(true);
            return result;
        }
        result.setCorrect(false);
        result = answertocolor.RecordColorInfo(correctAnswer,inputAnswer);


        return result;
    }
}
