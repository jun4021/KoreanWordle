#모아워들

## 목차

## 목표
1. 전반적인 Spring을 이용한 웹 서버 구축하기
2. AWS를 이용해 직접적인 서비스 구현하기
3. JPA를 이용해 MYSQL과 연동하기

## 전반적인 구조
* 클라이언트(플레이어)가 세 글자로 이루어진 단어를 쳐 확인하기 버튼을 눌러 서버에게 단어와 현재 트라이 횟수를 보낸다.
```javascript
$.ajax({
        url:"/correct",
        type:"POST",
        data:{
            trynum:trynum,
            answer:checkAnswer
        },
        async:false,
        success:function(data){

            dataResult = data;
        },
        error: function(){
            alert("error");
        }
    });
```
* 서버에서는 이 단어를 받아서 다음 순서도에 따라 처리한다.
  ![단어검사시알고리즘](https://user-images.githubusercontent.com/86395683/157030374-1e34c8d3-0c58-4a26-9458-b8732b45a4da.PNG)
  * 이때 response 되는 output의 형태는 다음과 같다.
  ```java
  public class ColorInfo {
    private ArrayList grey; // Display 상의 회색인 index
    private ArrayList yellow; // Display 상의 노란색인 index
    private ArrayList green; // Display 상의 초록색인 index
    private ArrayList grey_keyboard; // Keyboard 상의 회색인 글자
    private ArrayList yellow_keyboard; // Keyboard 상의 노란색인 글자
    private ArrayList green_keyboard; // Keyboard 상의 초록색인 글자
    private boolean correct; // 정답인지 여부
    private boolean validWord = true; // 단어 리스트에 있는지
    private String[] separateLetters; // 입력 단어를 초,중,종성으로 쪼갠 것
    private String answer; // 5회 시도 후 정답 안내
  }
  ```
* 클라이언트는 response 받은 Data를 이용해 각각의 요소에 알맞은 반응을 취한다.
  ![image](https://user-images.githubusercontent.com/86395683/157032195-53a0b33f-6a17-4839-bbc4-fabd322c0987.png)
  ![image](https://user-images.githubusercontent.com/86395683/157032299-8e86d5eb-e34e-48ee-9104-d8213ac900c4.png)
* 정답을 맞추었거나 5회 시도 종료시 안내 toast 창을 띄운 후 통계창을 띄워준다.
  ![image](https://user-images.githubusercontent.com/86395683/157032475-4bda0d61-1f6c-49c9-b581-7a2eb550073a.png)

## 