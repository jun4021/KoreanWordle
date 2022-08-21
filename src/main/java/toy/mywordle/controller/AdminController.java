package toy.mywordle.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import toy.mywordle.domain.addcheckword;
import toy.mywordle.domain.dailyanswer;
import toy.mywordle.domain.dailyrecord;
import toy.mywordle.domain.requestword;
import toy.mywordle.service.*;

import java.time.LocalDateTime;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class AdminController {


    private final DailyAnswerService dailyAnswerService;
    private final DailyRecordService dailyRecordService;
    private final AddCheckWordService addCheckWordService;
    private final RequestWordService requestWordService;
    private final CheckWordService checkWordService;
    private final DeleteWordService deleteWordService;

    @GetMapping("/admin")
    public String admin(Model model){
        LocalDateTime now = LocalDateTime.now();
        dailyanswer today = dailyAnswerService.FindWordObject(now);
        dailyanswer yesterday = dailyAnswerService.FindWordObject(now.minusDays(1));

        String twit = "정답이 갱신되었습니다!\n" +
                "오늘은 정답은 무엇일까요?\n" +
                "\n" +
                "*"+now.minusDays(1).getMonthValue()+"월 "+now.minusDays(1).getDayOfMonth()+"일, 어제 정답은 ["+yesterday.getAnswer()+"]이었습니다.\n" +
                "'더보기' 페이지에서 정답률을 확인하세요!\n" +
                "\n" +
                "https://koreanwordle.com";
        model.addAttribute("twit",twit);
        model.addAttribute("today",today);
        model.addAttribute("yesterday",yesterday);
        return "admin";
    }

    @GetMapping("/loginForm")
    public String login(){

        return "loginForm";
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
}
