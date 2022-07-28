package toy.mywordle.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AdminController {
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @GetMapping("/admin")
    public String admin(){
        return "admin";
    }

    @GetMapping("/loginForm")
    public String login(){

        return "loginForm";
    }
}
