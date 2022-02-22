package toy.mywordle.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class deleteword {
    @Id
    private String word;
    private String date;
    private boolean wait;

    public boolean isWait() {
        return wait;
    }

    public void setWait(boolean wait) {
        this.wait = wait;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}

