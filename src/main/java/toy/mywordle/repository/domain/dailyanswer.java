package toy.mywordle.repository.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class dailyanswer {

    @Id
    private String date; // system 내의 id 코드
    private String answer;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
