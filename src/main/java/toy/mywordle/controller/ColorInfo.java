package toy.mywordle.controller;

import java.util.ArrayList;
import java.util.List;

public class ColorInfo {
    private ArrayList grey;
    private ArrayList yellow;
    private ArrayList green;
    private boolean correct;

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