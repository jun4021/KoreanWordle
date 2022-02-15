package toy.mywordle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import toy.mywordle.domain.dailyanswer;
import toy.mywordle.domain.dailyrecord;
import toy.mywordle.repository.DailyAnswerRepository;
import toy.mywordle.repository.DailyRecordRepository;
import toy.mywordle.repository.NonValidAnswerWordRepository;
import toy.mywordle.service.AnswerToColorService;
import toy.mywordle.service.AnswerWordService;
import toy.mywordle.service.CheckWordService;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.TimeZone;

@Controller
public class MywordleController {
    private final AnswerToColorService answerToColorService;
    private final AnswerWordService answerWordService;
    private final CheckWordService checkWordService;
    private final DailyRecordRepository dailyRecordRepository;
    private final DailyAnswerRepository dailyAnswerRepository;
    private final NonValidAnswerWordRepository nonValidAnswerWordRepository;
    private String correctAnswer;
    private dailyrecord record = new dailyrecord();

    @Autowired
    public MywordleController(AnswerToColorService answerToColorService, AnswerWordService answerWordService, CheckWordService checkWordService, DailyRecordRepository dailyRecordRepository, DailyAnswerRepository dailyAnswerRepository, NonValidAnswerWordRepository nonValidAnswerWordRepository) {
        this.answerToColorService = answerToColorService;
        this.answerWordService = answerWordService;
        this.checkWordService = checkWordService;
        this.dailyRecordRepository = dailyRecordRepository;
        this.dailyAnswerRepository = dailyAnswerRepository;
        this.nonValidAnswerWordRepository = nonValidAnswerWordRepository;
        // 그 날짜 DB에서 뽑아오면 됨
        LocalDateTime now = LocalDateTime.now();
        correctAnswer = dailyAnswerRepository.FindAnswer(now.toLocalDate().toString());
    }

    @Scheduled(cron="0 0 0 * * ?")
    public void ChooseAnswer(){
        Integer code = answerWordService.ChooseRandomId();
        correctAnswer = answerWordService.SelectWordByCode(code).getWord();
        LocalDateTime now = LocalDateTime.now();

        // 정답 단어 DB에 넣기
        dailyanswer answerRecord = new dailyanswer();
        answerRecord.setDate(now.toLocalDate().toString());
        answerRecord.setAnswer(correctAnswer);
        dailyAnswerRepository.SaveRecord(answerRecord);

        record.setDate(now.toString());
        dailyRecordRepository.SaveRecord(record);
        record = new dailyrecord();
    }

    @GetMapping("/")
    public String home(){
        // DB에서 정답 불러오기
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
            nonValidAnswerWordRepository.SaveWord(inputAnswer);
            return result;
        }

        // 있을 시 정답이랑 비교
        result = answerToColorService.RecordColorInfo(correctAnswer,inputAnswer);
        if(trynum==0){
            record.settrystart(record.gettrystart()+1);
        }
        if(correctAnswer.equals(inputAnswer)){
            // try 횟수에 따른 data 추가
            switch (trynum+1){
                case 1:
                    record.setOnetrycorrect(record.getOnetrycorrect()+1);
                    break;
                case 2:
                    record.setTwotrycorrect(record.getTwotrycorrect()+1);
                    break;
                case 3:
                    record.setThreetrycorrect(record.getThreetrycorrect()+1);
                    break;
                case 4:
                    record.setFourtrycorrect(record.getFourtrycorrect()+1);
                    break;
                case 5:
                    record.setFivetrycorrect(record.getFivetrycorrect()+1);
                    break;
            }
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
