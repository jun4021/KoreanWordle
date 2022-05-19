# 모아워들 
Notion : https://cottony-perch-6eb.notion.site/08dce7a261234e49af1ce1dafd90d1bf

## 목표
1. 전반적인 Spring을 이용한 웹 서버 구축하기
2. AWS를 이용해 직접적인 서비스 구현하기

## 프로젝트 환경 및 사용 기술 스택
* Spring boot, MYSQL 사용.
* AWS를 이용해 서버 구축, 이때 데이터베이스 역시 Amazon RDS를 이용해 구축.
* Front-End는 간략하게 html,css,js를 사용해 구현.

## 구조
### 1. 단어 검사
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

### 2. 확인 단어 색깔 정보 부여 (ColorInfo)
* ```AnswerToColorService``` 의 ```ReadColorInfo``` method 호출
* ```correctAnswer(정답단어)```와 ```InputAnswer(확인단어)```를 비교해 필요한 ```ColorInfo```를 부여하는 것이 목적

* Display 의 색 규칙

  * 초성, 중성, 종성으로 분리해 각 자소를 규칙에 맞게 확인
  * 초록색 : 해당 자소가 정답 단어와 자리까지 일치할 때. ex) 첫 번째 글자 초성은 'ㅌ'이다.
  * 노란색 : 해당 자소가 정답 단어와 자리는 일치하지 않지만 존재할 때. ex) 두 번째 글자의 중성은 'ㅕ'는 아니지만 다른 글자에 'ㅕ'가 존재한다.
  * 검정색 : 해당 자소가 정답 단어에 존재하지 않을 때. ex) 'ㅍ'는 정답에 존재하지 않는다.
  
  ![image](https://user-images.githubusercontent.com/86395683/164882577-9ade712d-b1e2-46ac-bb5f-cb774207d116.png)

* 이를 표현하기 위해 필연적으로 자소를 분리해야 한다. 
* 한글의 유니코드를 이용해 다음과 같이 초성,중성 종성을 분해할 수 있다.
```java
  
private String[] SeparateLetter(String str){
        String[] result = new String[9];
        String[] CHO = {"ㄱ","ㄲ","ㄴ","ㄷ","ㄸ","ㄹ","ㅁ","ㅂ","ㅃ", "ㅅ","ㅆ","ㅇ","ㅈ","ㅉ","ㅊ","ㅋ","ㅌ","ㅍ","ㅎ"};
        String[] JOONG = {"ㅏ","ㅐ","ㅑ","ㅒ","ㅓ","ㅔ","ㅕ","ㅖ","ㅗ","ㅘ", "ㅙ","ㅚ","ㅛ","ㅜ","ㅝ","ㅞ","ㅟ","ㅠ","ㅡ","ㅢ","ㅣ"};
        String[] JONG = {"","ㄱ","ㄲ","ㄳ","ㄴ","ㄵ","ㄶ","ㄷ","ㄹ","ㄺ","ㄻ","ㄼ", "ㄽ","ㄾ","ㄿ","ㅀ","ㅁ","ㅂ","ㅄ","ㅅ","ㅆ","ㅇ","ㅈ","ㅊ","ㅋ","ㅌ","ㅍ","ㅎ"};

        for(int i=0;i<3;i++){
            char cho = (char)((str.charAt(i)-0xAC00)/28/21);
            char joong = (char)((str.charAt(i)-0xAC00)/28%21);
            char jong = (char)((str.charAt(i)-0xAC00)%28);
            result[3*i] = (CHO[(int)cho]);
            result[3*i+1] = (JOONG[(int)joong]);
            result[3*i+2] = (JONG[(int)jong]);
        }
        return result;
    }
```

* 그 후 Display의 색 부여는 다음과 같이 단순한 작업을 통해 이루어진다.
```java
// Green Check
for(int i=0;i<9;i++) {
    if (input[i] == "") {

    } else if (input[i] == correct[i]) {
        green.add(i);
        NoCheckCorrect.add(i);
        NoCheckInput.add(i);
    }
}
// Yellow Check
for(int i=0;i<9;i++) {
    if (input[i] == "" || NoCheckInput.indexOf(i)!=-1) {

    }
    else {
        for (int j = 0; j < 9; j++) {
            if (input[i] == correct[j] && NoCheckCorrect.indexOf(j)==-1) {
                yellow.add(i);
                NoCheckCorrect.add(j);
                break;
            }
        }
    }
}
// Grey Check
for(int i=0;i<9;i++){
    if(input[i]!="" && yellow.indexOf(i)==-1 && green.indexOf(i)==-1){
        grey.add(i);
    }
}
```
* 하지만 한 가지 문제점이 발생한다. 한글은 ```ㄳ```나 ```ㅘ```처럼 두가지 자소가 합쳐지는 경우도 있다.
이는 화면 하단에 보이는 키보드의 색 부여에 불편함을 야기한다. 
* 예를 들어 정답이 ```과```, 확인을 ```라```라고 하였을 때 ```ㅘ```와 ```ㅏ```는 같지 않으므로 회색으로 표기해야 하나
```ㅏ```는 정답을 작성할 때 사용되기에 키보드의 불을 끄게 된다면 사용자에게 헷갈리는 or 정답을 맞출 수 없는 부정적인 경험을 야기할 것이다.
* 따라서 display 와 keyboard의 색 부여 방식을 다르게 하여 이를 케어하도록 하였다.
```java
private ArrayList CheckDoubleLetters(String[] strlist){
        String[] doubleLetters= {"ㅘ", "ㅙ","ㅚ","ㅝ","ㅞ","ㅟ","ㅢ","ㄳ","ㄵ","ㄶ","ㄺ","ㄻ","ㄼ", "ㄽ","ㄾ","ㄿ","ㅀ","ㅄ"};
        ArrayList doubleLettersPoistion=new ArrayList<>();
        for(int i=0;i<9;i++){
            if(Arrays.asList(doubleLetters).contains(strlist[i])){
                doubleLettersPoistion.add(i);
            }
        }
        return doubleLettersPoistion;
}
```

* 단어가 들어오면 디스플레이 용 색깔 검사 → ```DoubleLetter```가 있는지 확인. → ```DoubleLetter```를 분리 후 각각의 자소에 대해 검사. 
→ 그때 검사 된 자소의 색을 함께 JSON 파일에 작성(이때, 디스플레이에서의 색깔이 각각의 자소의 Default 값으로 설정하여 덮어쓰이는 것을 방지, 우선순위 초록>노랑>회색)
* 예를 들어 ```과```가 정답이고 확인을 ```라```라고 하였을 때, display는 아까랑 마찬가지로 회색이 뜨나 키보드에서의 ```ㅏ```는 초록색이 된다.
* 자세한 내용은 ```CheckColorOfDouble``` 참조

### 3. 단어 리스트 보완 시스템
* 며칠의 테스트 결과 사용자가 확인 가능한 단어 리스트 목록이 보완이 필요하고 느낌
* 크게 두 가지 방안을 모색
  1. 유저가 직접 참여하여 단어 요청
  2. 유저들의 단어 검사 기록을 수집
-------
* 단어 요청하기

  ![image](https://user-images.githubusercontent.com/86395683/164888095-d250fab8-f0a8-48a0-af75-ba8063382670.png)
  * 단어를 입력 후 ```추가하기```를 누르면 단어를 요청 가능하다. 이후 소개할 ```requestword``` 에 저장되고 확인 가능하다.
  이때 이미 있는 단어거나 완성되지 않았을 경우 요청이 불가능하다.
* 단어 기록 수집하기
  * 유저가 검사한 단어지만 단어 리스트에 없는 경우 별도로 기록한다. 이 단어를 다수가 검사하였을 경우 단순 오타가 아닌 단어 리스트의 부재로 판단해 추가 여부를 판단한다.
  * 현재 5번 이상 검사받을 경우 별도의 확인을 하는 중이다.
  * 만약 이 검사 과정에서 문제가 있는 단어(욕설, 비속어 등) 또는 단순 오타의 경우 삭제하여 다시 기록이 되지 않도록 한다.
-------
* 두 방법을 통해 검수가 필요한 단어 리스트를 쉽게 보기 위한 admin 페이지를 작성하였다. 

![image](https://user-images.githubusercontent.com/86395683/164888679-b5a4e6d2-50e8-487c-ac25-9bf53e261826.png)

### 4. DB Table

* answerword
  * 매일 0시에 초기화 되는 정답 단어를 모아둔 table
  * ```id``` : 랜덤하게 선정하기 위해 설정
  * ```word``` : 세글자로 이루어진 정답 단어
* checkword
  * 사용자가 확인할 수 있는 세글자 단어 table (출처: 국립국어원 한국어 기초사전)
  * ```word```: 세글자 단어
  * ```adddate```: 해당 단어를 추가한 날짜. 혹여 잘못 추가했거나 로그가 필요할 때를 대비
  * ```count```: 사람들이 해당 단어를 입력한 횟수. 
  * ```firstcount```: 사람들이 해당 단어를 첫 번째 시도에 입력한 횟수.
* dailyanswer
  * 그날그날 날짜별로 정답을 확인하기 위한 table
  * ```date```: 해당하는 날짜
  * ```answer```: 해당하는 날짜의 정답 단어
* dailyrecord
  * 날짜별로 사용자의 수, 정답률 등 여러 record를 보기 위해 추가한 로그 table
  * ```date```: 해당 날짜
  * ```correctanswer```:정답을 맞춘 수
  * ```fail```:5번의 시도를 하였으나 맞추지 못한 수
  * ```one~five trycorrect```: 1~5번 각각의 시도만에 맞춘 유저의 수
  * ```trystart```: 첫 번째 시도를 한 유저의 수(시도를 끝내지 않고 이탈하는 유저를 확인하기 위함)
* addcheckword
  * 사용자가 입력하였으나 ```checkword```에 없는 경우(단어리스트에 없습니다.) 부족한 단어를 채우기 위함
  * ```word```: 단어
  * ```date```: 입력 날짜
  * ```count``` : 해당 단어를 몇 번 입력하였는지
* requestword 
  * 사용자가 별도로 추가 요청한 단어
  * ```word```: 단어
  * ```date```: 입력 날짜
* deleteword
  * ```addcheckword```에서 확인 후 삭제한 단어, 이후 사용자의 입력을 받아도 addcheckword에 추가되지 않음


## 이슈
### 02/17
- 서버 종료시 그 날 Record가 저장이 안되는 문제 발생 (매일 0시에 Record가 저장되기 때문)
- 스프링 컨테이너가 빈을 제거하기 전(서버 종료 전) 그 날 Record를 저장하는 코드 작성.
- 서버 시작 시 그 날 Record가 DB에 존재할 경우 그것을 불러와 Record에 이어서 작성한다.
```java
@PreDestroy
public void close() throws Exception{
    System.out.println("서버 종료");
    LocalDateTime now = LocalDateTime.now();
    record.setDate(now.toLocalDate().toString());
    dailyRecordRepository.SaveRecord(record);
}
```
### 02/26
- 15:28 : 더보기 페이지를 수정하여 배포함.
- 15:29 : css, js 파일이 업데이트가 안되는 것을 확인 → 더보기를 눌렀을 때 현재까지 맞춘 단어 리스트를 불러오는 과정에서 에러 발생
- 15:48 : 버그 상황 파악 후 서버를 내림.
- 16:11 : js, css 등 정적 파일이 캐시에 저장되어 있어 수정사항이 변경되려면 ver 표시해야 한다는 것을 깨달음.
- 16:24: 다시 서버 정상화 ( 1.0.2 업데이트 완료 )

### 03/16
- 다크모드 테스트 중에 남은 로그 Record에 의해 3/15 dailyRecord가 날아감.
  - 문제 원인 : 그 날 Record를 남기기 위해 서버 종료 시 DB에 insert 함. 이때 PK가 date기 때문에 중복된 PK(날짜)가 있으면 insert가 되지 않고 오류가 발생. 따라서 기존에 있던 Local에서 테스트 중에 생긴 Record가 남아버림.
  - 해결 방안 : 그 날짜의 Record가 이미 있을 때 덮어쓰기 or 병합 or (체크)하는 코드 필요.

## 결과물
* AWS를 이용한 웹 서비스 구현
* HTTPS 적용
* Google Analytics, Google Search Console, Naver search Advisor 연동
* JPA를 이용한 간단한 DB 조작 방법

## 이후 추가요소
* 현재 배포 시 서버를 내리고 배포를 하기 때문에 그 시간 사용자의 경험에 부정적인 영향을 미침
중단 없이 배포하는 방안 탐색. 
* 테스트 코드의 중요성을 절실하게 느낌. 테스트 코드 작성 필요. 
