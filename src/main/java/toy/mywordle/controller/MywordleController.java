package toy.mywordle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import toy.mywordle.domain.dailyrecord;
import toy.mywordle.repository.DailyRecordRepository;
import toy.mywordle.service.AnswerToColorService;
import toy.mywordle.service.AnswerWordService;
import toy.mywordle.service.CheckWordService;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

@Controller
public class MywordleController {
    private final AnswerToColorService answerToColorService;
    private final AnswerWordService answerWordService;
    private final CheckWordService checkWordService;
    private final DailyRecordRepository dailyRecordRepository;
    private String correctAnswer = "내일로";
    private dailyrecord record = new dailyrecord();


    @Autowired
    public MywordleController(AnswerToColorService answerToColorService, AnswerWordService answerWordService, CheckWordService checkWordService, DailyRecordRepository dailyRecordRepository) {
        this.answerToColorService = answerToColorService;
        this.answerWordService = answerWordService;
        this.checkWordService = checkWordService;
        this.dailyRecordRepository = dailyRecordRepository;
    }


    @Scheduled(cron="0 0 0 * * ?")
    public void ChooseAnswer(){
        Integer code = answerWordService.ChooseRandomId();
        correctAnswer = answerWordService.SelectWordByCode(code).getWord();
        dailyRecordRepository.SaveRecord(record);
        record = new dailyrecord();

    }

    @GetMapping("/")
    public String home(){
        // DB에서 정답 불러오기
        LocalDateTime now = LocalDateTime.now();
        record.setDate(now.toLocalDate().toString());
        record.setVisit(record.getVisit()+1);

        return "home";
    }

    @PostMapping("/correct")
    @ResponseBody
    public ColorInfo CheckCorrect(InputAnswer ob){

        ColorInfo result = new ColorInfo();
        String inputAnswer = ob.getAnswer();
        Integer trynum = ob.getTrynum();


        // DB에 단어 list 확인
        if(!checkWordService.InCheckWord(inputAnswer)) {
            result.setValidWord(false);

            return result;
        }

        // 있을 시 정답이랑 비교
        result = answerToColorService.RecordColorInfo(correctAnswer,inputAnswer);
        if(correctAnswer.equals(inputAnswer)){
            record.setCorrectanswer(record.getCorrectanswer()+1);
            result.setCorrect(true);
            return result;
        }
        if(trynum == 4){
            record.setFail(record.getFail()+1);
            result.setAnswer(correctAnswer);
        }
        result.setCorrect(false);

        return result;
    }
}
