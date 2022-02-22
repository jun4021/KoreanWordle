package toy.mywordle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import toy.mywordle.domain.addcheckword;
import toy.mywordle.domain.dailyanswer;
import toy.mywordle.domain.dailyrecord;
import toy.mywordle.domain.requestword;
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
    private final RequestWordService requestWordService;
    private final DeleteWordService deleteWordService;

    private String correctAnswer;
    private dailyrecord record;

    @Autowired
    public MywordleController(DeleteWordService deleteWordService,AnswerToColorService answerToColorService, AnswerWordService answerWordService, CheckWordService checkWordService, DailyRecordService dailyRecordService, DailyAnswerService dailyAnswerService, AddCheckWordService addCheckWordService, RequestWordService requestWordService) {
        this.answerToColorService = answerToColorService;
        this.answerWordService = answerWordService;
        this.checkWordService = checkWordService;
        this.dailyRecordService = dailyRecordService;
        this.dailyAnswerService = dailyAnswerService;
        this.addCheckWordService = addCheckWordService;
        this.requestWordService = requestWordService;
        this.deleteWordService = deleteWordService;
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
    @RequestMapping(value={"/robots.txt", "/robot.txt"})
    @ResponseBody
    public String getRobotsTxt() {
        return "User-agent: *\n" +
                "Disallow: /admin\n";
    }

    @RequestMapping(value={"/sitemap.xml"})
    @ResponseBody
    public String getSiteMap() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">\n" +
                "<url>\n" +
                "  <loc>https://koreanwordle.com:443/</loc>\n" +
                "  <lastmod>2022-02-21T23:58:25+00:00</lastmod>\n" +
                "  <priority>1.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://koreanwordle.com:443/guide</loc>\n" +
                "  <lastmod>2022-02-21T23:58:25+00:00</lastmod>\n" +
                "  <priority>0.80</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://koreanwordle.com:443/request</loc>\n" +
                "  <lastmod>2022-02-21T23:58:25+00:00</lastmod>\n" +
                "  <priority>0.80</priority>\n" +
                "</url>\n" +
                "\n" +
                "\n" +
                "</urlset>";
    }

    @GetMapping("/")
    public String home(){
        // DB에서 정답 불러오기
        record.setVisit(record.getVisit()+1);

        return "home";
    }

    @GetMapping("/guide")
    public String quide(){
        // DB에서 정답 불러오기

        return "howtoplay";
    }
    @GetMapping("/request")
    public String request(){
        return "wordadd";
    }

    @GetMapping("/requestWord")
    @ResponseBody
    public boolean request(RequestWord ob){
        String word = ob.getRequest();
        // 단어가 이미 DB에 있을 시
        if(checkWordService.InCheckWord(word)){
            return false;
        }
        // 단어가 이미 삭제 단어에 있을 시
        else if(deleteWordService.FindByword(word)){
            return true;
        }
        else{
            if(requestWordService.SaveWord(word)){
                record.setRequestword(record.getRequestword()+1);
            }
            return true;
        }

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
        List<requestword> requestwordlist = requestWordService.FindAll();
        model.addAttribute("addlist",wordlist);
        model.addAttribute("requestlist", requestwordlist);

        return "add";
    }
    @PostMapping("/admin/addaction")
    public String AddAction(@RequestParam List<String> word){

        for (String c : word){

            checkWordService.InsertWord(c);
            requestWordService.DeleteWord(c);
            addCheckWordService.DeleteWord(c);
        }
       return "redirect:/admin/add";
    }
    @PostMapping("/admin/delete")
    public String DelAction(@RequestParam List<String> word){
        for(String c: word){
            requestWordService.DeleteWord(c);
            addCheckWordService.DeleteWord(c);
            deleteWordService.SaveWord(false,c);
        }
        return "redirect:/admin/add";
    }
    @PostMapping("/admin/deletewait")
    public String DelWaitAction(@RequestParam List<String> word){
        for(String c: word){
            requestWordService.DeleteWord(c);
            addCheckWordService.DeleteWord(c);
            deleteWordService.SaveWord(true,c);
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

            //CheckWord 에 없는 경우 DeleteWord에 있는지 확인하고 없을 때 AddCheckWord에 추가
            if(addCheckWordService.SaveWord(inputAnswer)) {
                record.setDetectaddword(record.getDetectaddword() + 1);
            }
            return result;
        }

        // 있을 시 정답이랑 비교
        result = answerToColorService.RecordColorInfo(correctAnswer,inputAnswer);

        if(trynum==0) {
            record.settrystart(record.gettrystart() + 1);
            if (!correctAnswer.equals(inputAnswer)) {
                checkWordService.PlusFirstCount(inputAnswer);
            }
        }
        if(trynum==3){
            record.setFourtryrun(record.getFourtryrun()+1);
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
                    record.setFourtryrun(record.getFourtryrun()-1);
                    break;
            }
            record.setCorrectanswer(record.getCorrectanswer()+1);

            result.setCorrect(true);
            return result;
        }
        if(trynum == 4){
            record.setFail(record.getFail()+1);
            record.setFourtryrun(record.getFourtryrun()-1);
            result.setAnswer(correctAnswer);
        }
        checkWordService.PlusCount1(inputAnswer);
        result.setCorrect(false);

        return result;
    }
}
