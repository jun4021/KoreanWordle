package toy.mywordle.service;

import toy.mywordle.controller.ColorInfo;

import java.util.*;


public class AnswerToColor {

    public ColorInfo RecordColorInfo(String correctAnswer, String InputAnswer){

        ColorInfo colorinfo = new ColorInfo();
        ArrayList yellow = new ArrayList();
        ArrayList green = new ArrayList();
        ArrayList grey = new ArrayList();
        ArrayList yellow_k = new ArrayList();
        ArrayList green_k = new ArrayList();
        ArrayList grey_k = new ArrayList();
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



        colorinfo.setSeparateLetters(input);
        colorinfo.setYellow(yellow);
        colorinfo.setGreen(green);
        colorinfo.setGrey(grey);

        // 겹자음, 겹모음 확인
        Set letters = CheckColorOfDouble(correct,input).keySet();

        Iterator<String> iterator = letters.iterator();
        while(iterator.hasNext()){
            String key = iterator.next();
            String color = CheckColorOfDouble(correct,input).get(key);
            if(color.equals("green")){
                green_k.add(key);
            }
            else if(color.equals("yellow")){
                yellow_k.add(key);
            }
            else{
                grey_k.add(key);
            }
        }
        colorinfo.setYellow_keyboard(yellow_k);
        colorinfo.setGreen_keyboard(green_k);
        colorinfo.setGrey_keyboard(grey_k);
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


    private Map<String,String> CheckColorOfDouble(String[] correct, String[] strlist){
        ArrayList pos = CheckDoubleLetters(strlist);
        Map<String,String> keymap= new HashMap<String,String>();
        String defaultColor = "grey";

        if(pos.size() != 0) {
            for (int i = 0; i < pos.size(); i++) {
                defaultColor = "grey";
                String letter = strlist[(int)pos.get(i)]; // Double

                // letter가 correct안에 있는지 확인 후 단모음의 default 색 변경
                if(Arrays.asList(correct).contains(letter)){

                    if(correct[(int)pos.get(i)].equals(letter)){
                        defaultColor = "green";
                    }
                    else{
                        defaultColor = "yellow";
                    }
                    System.out.println(pos.size() + defaultColor);

                }

                //(Ex){"ㄱ","ㅅ","ㄳ"}
                char[] result = SeparateDouble(letter);

                for(int res=0;res<2;res++){
                    boolean greyflag = true;
                    // Green

                    if(defaultColor.equals("green")){
                        keymap.put(Character.toString(result[0]), "green");
                        keymap.put(Character.toString(result[1]), "green");
                        greyflag = false;
                        break;
                    }

                    if(correct[(int)pos.get(i)].equals(Character.toString(result[res]))) {
                        keymap.put(Character.toString(result[res]), "green");
                        greyflag = false;
                        break;
                    }
                    for(int j=0;j<correct.length;j++){
                        if(defaultColor.equals("yellow")){
                            keymap.put(Character.toString(result[0]), "yellow");
                            keymap.put(Character.toString(result[1]), "yellow");
                            greyflag = false;
                            break;
                        }
                        if(correct[j].equals(Character.toString(result[res]))) {
                            // Yellow
                            keymap.put(Character.toString(result[res]), "yellow");
                            greyflag = false;
                            break;
                        }
                    }
                    // Grey
                    if(greyflag){
                        keymap.put(Character.toString(result[res]), "grey");
                    }
                }
            }
        }
        return keymap;
    }

    private ArrayList CheckDoubleLetters(String[] strlist){
        String[] doubleLetters= {"ㅘ", "ㅙ","ㅚ","ㅝ","ㅞ","ㅟ","ㅢ","ㄳ","ㄵ","ㄶ","ㄺ","ㄻ","ㄼ", "ㄽ","ㄾ","ㄿ","ㅀ","ㅄ"};
        ArrayList doubleLettersPoistion=new ArrayList<>();
        for(int i=0;i<9;i++){
            if(Arrays.asList(doubleLetters).contains(strlist[i])){
                doubleLettersPoistion.add(i);
            }
        }
        return doubleLettersPoistion;
    }

    private char[] SeparateDouble(String str){
        char[][] doubleLetters ={{'ㄱ', 'ㅅ', 'ㄳ'}, {'ㄴ', 'ㅈ', 'ㄵ'},{'ㄴ', 'ㅎ', 'ㄶ'},
                {'ㄹ', 'ㄱ', 'ㄺ'}, {'ㄹ', 'ㅁ', 'ㄻ'}, {'ㄹ', 'ㅂ', 'ㄼ'}, {'ㄹ', 'ㅅ', 'ㄽ'},
                {'ㄹ', 'ㅌ', 'ㄾ'}, {'ㄹ', 'ㅍ', 'ㄿ'}, {'ㄹ', 'ㅎ', 'ㅀ'}, {'ㅂ', 'ㅅ', 'ㅄ'},
                {'ㅗ', 'ㅏ', 'ㅘ'}, {'ㅗ', 'ㅐ', 'ㅙ'}, {'ㅗ', 'ㅣ', 'ㅚ'}, {'ㅜ', 'ㅓ', 'ㅝ'},
                {'ㅜ', 'ㅔ', 'ㅞ'}, {'ㅜ', 'ㅣ', 'ㅟ'}, {'ㅡ', 'ㅣ', 'ㅢ'}};

        for(int i=0;i<18;i++){
            if(str.equals(Character.toString(doubleLetters[i][2]))){
                return(doubleLetters[i]);
            }
        }
        return null;
    }
}
