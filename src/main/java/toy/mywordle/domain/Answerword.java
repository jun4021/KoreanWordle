package toy.mywordle.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Answerword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB 스스로 생성
    private Integer id; // system 내의 id 코드
    private String word;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
