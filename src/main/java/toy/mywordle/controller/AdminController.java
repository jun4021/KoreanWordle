package toy.mywordle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import toy.mywordle.domain.dailyanswer;
import toy.mywordle.domain.dailyrecord;
import toy.mywordle.service.DailyAnswerService;

import java.time.LocalDateTime;
import java.util.List;


@Controller
public class AdminController {
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private DailyAnswerService dailyAnswerService;

    @GetMapping("/admin")
    public String admin(Model model){
        LocalDateTime now = LocalDateTime.now();
        dailyanswer today = dailyAnswerService.FindWordObject(now);
        dailyanswer yesterday = dailyAnswerService.FindWordObject(now.minusDays(1));

        model.addAttribute("today",today);
        model.addAttribute("yesterday",yesterday);
        return "admin";
    }
//    Model model){
//        List<dailyrecord> records = dailyRecordService.findAll();
//        model.addAttribute("records",records);

    @GetMapping("/loginForm")
    public String login(){

        return "loginForm";
    }
}
