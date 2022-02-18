package toy.mywordle.domain;

import javax.persistence.Entity;

import javax.persistence.Id;

@Entity
public class addcheckword {
    @Id
    private String word;
    private String date;



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
