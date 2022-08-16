package toy.mywordle.service;

import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

//https://chung-develop.tistory.com/31
//https://developers.naver.com/docs/papago/papago-nmt-api-reference.md
@Service
public class TranslateWordService {
    public String getTransWord(String word){
        String clientID = "";
        String clientSecret ="";

        String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
        String text;
        try{
            text = URLEncoder.encode(word,"UTF-8");
        }
        catch (UnsupportedEncodingException e){
            throw new RuntimeException("인코딩 에러",e);
        }
        Map<String,String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id",clientID);
        requestHeaders.put("X-Naver-Client-Secret",clientSecret);


        return word;
    }
}
