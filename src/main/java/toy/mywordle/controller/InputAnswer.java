package toy.mywordle.controller;

public class InputAnswer {
    private String answer;
    private Integer trynum;

    public Integer getTrynum() {
        return trynum;
    }

    public void setTrynum(Integer trynum) {
        this.trynum = trynum;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
