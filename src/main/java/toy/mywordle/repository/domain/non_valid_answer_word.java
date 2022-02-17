package toy.mywordle.repository.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
public class non_valid_answer_word {
    @Id
    private String word;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
