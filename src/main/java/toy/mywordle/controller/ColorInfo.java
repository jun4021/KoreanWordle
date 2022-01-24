package toy.mywordle.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ColorInfo {
    private ArrayList grey;
    private ArrayList yellow;
    private ArrayList green;
    private boolean correct;
    private String[] separateLetters;
    private ArrayList grey_keyboard;
    private ArrayList yellow_keyboard;
    private ArrayList green_keyboard;

    public ArrayList getGrey_keyboard() {
        return grey_keyboard;
    }

    public void setGrey_keyboard(ArrayList grey_keyboard) {
        this.grey_keyboard = grey_keyboard;
    }

    public ArrayList getYellow_keyboard() {
        return yellow_keyboard;
    }

    public void setYellow_keyboard(ArrayList yellow_keyboard) {
        this.yellow_keyboard = yellow_keyboard;
    }

    public ArrayList getGreen_keyboard() {
        return green_keyboard;
    }

    public void setGreen_keyboard(ArrayList green_keyboard) {
        this.green_keyboard = green_keyboard;
    }

    public String[] getSeparateLetters() {
        return separateLetters;
    }

    public void setSeparateLetters(String[] separateLetters) {
        this.separateLetters = separateLetters;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public ArrayList getGrey() {
        return grey;
    }

    public void setGrey(ArrayList grey) {
        this.grey = grey;
    }

    public ArrayList getYellow() {
        return yellow;
    }

    public void setYellow(ArrayList yellow) {
        this.yellow = yellow;
    }

    public ArrayList getGreen() {
        return green;
    }

    public void setGreen(ArrayList green) {
        this.green = green;
    }

}