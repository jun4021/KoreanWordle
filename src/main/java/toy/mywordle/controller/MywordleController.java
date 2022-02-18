package toy.mywordle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import toy.mywordle.domain.addcheckword;
import toy.mywordle.domain.dailyanswer;
import toy.mywordle.domain.dailyrecord;
import toy.mywordle.repository.AddCheckWordRepository;
import toy.mywordle.repository.DailyAnswerRepository;
import toy.mywordle.repository.DailyRecordRepository;
import toy.mywordle.service.*;

import javax.annotation.PreDestroy;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class MywordleController {
    private final AnswerToColorService answerToColorService;
    private final AnswerWordService answerWordService;
    private final CheckWordService checkWordService;
    private final DailyRecordService dailyRecordService;
    private final DailyAnswerService dailyAnswerService;
    private final AddCheckWordService addCheckWordService;

    private String correctAnswer;
    private dailyrecord record;

    @Autowired
    public MywordleController(AnswerToColorService answerToColorService, AnswerWordService answerWordService, CheckWordService checkWordService, DailyRecordService dailyRecordService, DailyAnswerService dailyAnswerService, AddCheckWordService addCheckWordService) {
        this.answerToColorService = answerToColorService;
        this.answerWordService = answerWordService;
        this.checkWordService = checkWordService;
        this.dailyRecordService = dailyRecordService;
        this.dailyAnswerService = dailyAnswerService;
        this.addCheckWordService = addCheckWordService;

        // 그 날 정답
        LocalDateTime now = LocalDateTime.now();
        correctAnswer = dailyAnswerService.FindAnswer(now);
        // 그 날 Record load
        record = dailyRecordService.RecordLoading(now);
    }


    @PreDestroy
    public void close() throws Exception{
        System.out.println("서버 종료");
        LocalDateTime now = LocalDateTime.now();
        record.setDate(now.toLocalDate().toString());
        dailyRecordService.SaveRecord(record);

    }

    @Scheduled(cron="0 0 0 * * ?")
    public void ChooseAnswer(){
        LocalDateTime now = LocalDateTime.now();
        // 단어 초기화
        Integer code = answerWordService.ChooseRandomId();
        correctAnswer = answerWordService.SelectWordByCode(code).getWord();

        // 정답 단어 DB insert
        dailyAnswerService.SaveWord(now,correctAnswer);

        // 전 날 Record DB insert
        record.setDate(now.minusDays(1).toLocalDate().toString());
        dailyRecordService.SaveRecord(record);
        record = new dailyrecord();
    }

    @GetMapping("/")
    public String home(){
        // DB에서 정답 불러오기
        record.setVisit(record.getVisit()+1);

        return "home";
    }
    @GetMapping("/admin/record")
    public String CheckRecord(Model model){
        List<dailyrecord> records = dailyRecordService.findAll();
        model.addAttribute("records",records);
        return "record";
    }

    @GetMapping("/admin/add")
    public String ShowAddList(Model model){
        List<addcheckword> wordlist = addCheckWordService.FindAll();
        model.addAttribute("addlist",wordlist);

        return "add";
    }
    @PostMapping("/admin/addaction")
    public String AddAction(@RequestParam List<String> word){

        for (String c : word){
            checkWordService.InsertWord(c);

            addCheckWordService.DeleteWord(c);
        }
       return "redirect:/admin/add";
    }
    @PostMapping("/admin/delete")
    public String DelAction(@RequestParam List<String> word){
        for(String c: word){

            addCheckWordService.DeleteWord(c);
        }
        return "redirect:/admin/add";
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

            addCheckWordService.SaveWord(inputAnswer);
            return result;
        }

        // 있을 시 정답이랑 비교
        result = answerToColorService.RecordColorInfo(correctAnswer,inputAnswer);
        checkWordService.PlusCount1(inputAnswer);
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
