package toy.mywordle.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Checkword {
    @Id
    private String word;
    private Integer count;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
