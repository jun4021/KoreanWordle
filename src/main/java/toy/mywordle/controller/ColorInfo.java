package toy.mywordle.controller;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class ColorInfo {
    private ArrayList grey;
    private ArrayList yellow;
    private ArrayList green;
    private ArrayList grey_keyboard;
    private ArrayList yellow_keyboard;
    private ArrayList green_keyboard;
    private boolean correct;
    private boolean validWord = true;
    private String[] separateLetters;
    private String answer;

}