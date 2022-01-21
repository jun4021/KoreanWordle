package toy.mywordle.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
public class MywordleController {
    @GetMapping("/")
    public String home(){
        return "home";
    }

    @PostMapping("/correct")
    @ResponseBody
    public HashMap<String,Integer> CheckCorrect(CheckAnswer ob){
        HashMap<String, Integer> result = new HashMap<String,Integer>();
        System.out.println(ob.getAnswer());
        result.put(ob.getAnswer(),1);
        return result;
    }
}
