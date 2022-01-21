package toy.mywordle.service;

import toy.mywordle.controller.ColorInfo;

import java.util.ArrayList;

public class AnswerToColor {

    public ColorInfo RecordColorInfo(String correctAnswer, String InputAnswer){

        ColorInfo colorinfo = new ColorInfo();
        ArrayList yellow = new ArrayList();
        ArrayList green = new ArrayList();
        ArrayList grey = new ArrayList();
        String[] correct = SeparateLetter(correctAnswer);
        String[] input = SeparateLetter(InputAnswer);
        ArrayList NoCheckCorrect = new ArrayList<>();
        ArrayList NoCheckInput = new ArrayList<>();

        // Green Check
        for(int i=0;i<9;i++) {
            if (input[i] == "") {

            } else if (input[i] == correct[i]) {
                green.add(i);
                NoCheckCorrect.add(i);
                NoCheckInput.add(i);
            }
        }
        // Yellow Check
        for(int i=0;i<9;i++) {
            if (input[i] == "" || NoCheckInput.indexOf(i)!=-1) {

            }
            else {
                for (int j = 0; j < 9; j++) {
                    if (input[i] == correct[j] && NoCheckCorrect.indexOf(j)==-1) {
                        yellow.add(i);
                        NoCheckCorrect.add(j);
                        break;
                    }
                }
            }
        }
        // Grey Check
        for(int i=0;i<9;i++){
            if(input[i]!="" && yellow.indexOf(i)==-1 && green.indexOf(i)==-1){
                grey.add(i);
            }
        }

        colorinfo.setYellow(yellow);
        colorinfo.setGreen(green);
        colorinfo.setGrey(grey);
        return colorinfo;
    }
    private String[] SeparateLetter(String str){
        String[] result = new String[9];
        String[] CHO = {"ㄱ","ㄲ","ㄴ","ㄷ","ㄸ","ㄹ","ㅁ","ㅂ","ㅃ", "ㅅ","ㅆ","ㅇ","ㅈ","ㅉ","ㅊ","ㅋ","ㅌ","ㅍ","ㅎ"};
        String[] JOONG = {"ㅏ","ㅐ","ㅑ","ㅒ","ㅓ","ㅔ","ㅕ","ㅖ","ㅗ","ㅘ", "ㅙ","ㅚ","ㅛ","ㅜ","ㅝ","ㅞ","ㅟ","ㅠ","ㅡ","ㅢ","ㅣ"};
        String[] JONG = {"","ㄱ","ㄲ","ㄳ","ㄴ","ㄵ","ㄶ","ㄷ","ㄹ","ㄺ","ㄻ","ㄼ", "ㄽ","ㄾ","ㄿ","ㅀ","ㅁ","ㅂ","ㅄ","ㅅ","ㅆ","ㅇ","ㅈ","ㅊ","ㅋ","ㅌ","ㅍ","ㅎ"};

        for(int i=0;i<3;i++){
            char cho = (char)((str.charAt(i)-0xAC00)/28/21);
            char joong = (char)((str.charAt(i)-0xAC00)/28%21);
            char jong = (char)((str.charAt(i)-0xAC00)%28);
            result[3*i] = (CHO[(int)cho]);
            result[3*i+1] = (JOONG[(int)joong]);
            result[3*i+2] = (JONG[(int)jong]);
        }
        return result;
    }

}
