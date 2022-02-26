import {CheckAnswerCorrect} from "./correct.js";

document.documentElement.addEventListener('touchstart',function (event) {
  if (event.touches.length > 1) {
    event.preventDefault();
  }
},false);

let MAX_ROW = 5;

let row;
let letters = [];


import * as correct from "./correct.js";
import * as painting from "./paintColor.js";
import * as local from "./localStorageControl.js";
import * as toast from "./toast.js";
import * as modal from "./modal.js";
import {NewWordRecord, WriteSolvedRecord, writeSolveLocal} from "./localStorageControl.js";


// 칸에 현재 입력 받은 문자열 출력
function PrintLetters(Row, lettersAssemble){
  let rowTile = document.getElementById('game-board')
      .childNodes[Row].childNodes[1]

  rowTile.childNodes[1].innerText ="";
  rowTile.childNodes[3].innerText ="";
  rowTile.childNodes[5].innerText ="";

  if(letters.length != 0) {
    rowTile.childNodes[1].innerText = lettersAssemble[0];
    if (lettersAssemble.length > 1) {
      rowTile.childNodes[3].innerText = lettersAssemble[1];
      if (lettersAssemble.length > 2) {
        rowTile.childNodes[5].innerText = lettersAssemble[2];
      }
    }
  }
}

function PrintLetter(Row, lettersAssemble){
  let rowTile = document.getElementById('game-board')
      .childNodes[Row].childNodes[1]

  rowTile.childNodes[1].innerText = lettersAssemble[0];
  rowTile.childNodes[3].innerText = lettersAssemble[1];
  rowTile.childNodes[5].innerText = lettersAssemble[2];



}

function AddLetter(letter) {
  let solved = JSON.parse(localStorage.getItem("colorData")).solved;
  if(!solved) {
    letters.push(letter);
    let lettersAssemble = Hangul.assemble(letters);

    if (lettersAssemble.length == 4) // 글자 수 제한 초과
    {
      letters.pop();
      toast.toast("글자는 3글자 제한");
      $(".add").attr("disabled", true);
      return;
    } else {

      PrintLetters(row, lettersAssemble);
    }
  }
}
function DelLetter(){
  let solved = JSON.parse(localStorage.getItem("colorData")).solved;
  if(!solved) {
    letters.pop();
    let lettersAssemble = Hangul.assemble(letters);
    PrintLetters(row, lettersAssemble);

    $(".add").attr("disabled", false);
  }
}

function EnterLetter() {
  let solved = JSON.parse(localStorage.getItem("colorData")).solved;
  if(!solved) {
    let lettersAssemble = Hangul.assemble(letters);

    if (lettersAssemble.length == 3 && Hangul.isCompleteAll(lettersAssemble)) // 정상 종료 조건
    {
      // 시간 기록
      let now = new Date();
      let recentDate = localStorage.getItem("recentDate");
      // 최근 접속 날짜와 다를 때
      if(recentDate != now.toLocaleDateString()){
        toast.toast("날짜가 변경되었습니다!\n" +
            "새로운 단어로 시작합니다");
        setTimeout(function(){location.reload();},3000);
        return;
      }

      localStorage.setItem("recentDate", now.toLocaleDateString());

      let trynum = JSON.parse(localStorage.getItem("colorData")).try
      let data = correct.CheckAnswerCorrect(trynum, lettersAssemble);

      // 단어 리스트 확인
      if (!data.validWord) {
        toast.toast("단어 리스트에 없습니다.");
        return;
      }
      // 정답 확인
      else if (data.correct) {
        toast.toast("정답입니다.");
        local.writeLocal(data, lettersAssemble);
        painting.PaintDisplay(row);

        local.WriteSolvedRecord(new Date().toLocaleDateString(),lettersAssemble,trynum);

        local.StatisticsEdit(true);
        $(".add").attr("disabled", true);
        $(".del").attr("disabled", true);
        $(".enter").attr("disabled", true);
        local.writeSolveLocal();
        letters = [];
        setTimeout(function () {
          modal.score();
          document.getElementsByClassName("score")[0].style.display = "flex";
        }, 2400);
        return;
      }

      toast.toast("정답 확인");
      local.writeLocal(data, lettersAssemble);
      painting.PaintDisplay(row);
      row += 2;
      letters = [];
      // try 종료
      if (JSON.parse(localStorage.getItem("colorData")).try == MAX_ROW) {
        local.StatisticsEdit(false);
        $(".add").attr("disabled", true);
        $(".del").attr("disabled", true);
        $(".enter").attr("disabled", true);
        toast.toast("정답: " + data.answer);
        local.writeSolveLocal();
        letters = [];

        setTimeout(function () {
          modal.score();
          document.getElementsByClassName("score")[0].style.display = "flex";
        }, 2800);


      }
    } else {
      toast.toast("완성되지 않은 글자가 있습니다");
    }
    $(".add").attr("disabled", false);

  }
}
let EnglishToKorean = {"Q":"ㅂ","W":"ㅈ","E":"ㄷ","R":"ㄱ","T":"ㅅ","Y":"ㅛ","U":"ㅕ","I":"ㅑ","O":"ㅐ","P":"ㅔ","A":"ㅁ","S":"ㄴ","D":"ㅇ","F":"ㄹ","G":"ㅎ","H":"ㅗ","J":"ㅓ","K":"ㅏ","L":"ㅣ","Z":"ㅋ","X":"ㅌ","C":"ㅊ","V":"ㅍ","B":"ㅠ","N":"ㅜ","M":"ㅡ"};
let shiftTo = {81:"ㅃ",87:"ㅉ",69:"ㄸ",82:"ㄲ",84:"ㅆ",79:"ㅒ",80:"ㅖ"};

$(document).ready(function(){
  $(".modal").hide();

  // 처음 접속했을 때
  if(localStorage.getItem("entered") == null){
    // 초기 방법 창 띄우기
    window.location.href = "/guide"
    // 초기 local Storage 정보
    localStorage.setItem("statistics",JSON.stringify(local.NewStatisticsLocal()));
    localStorage.setItem("entered","true");
    localStorage.setItem("WordRecord",JSON.stringify(local.NewWordRecord()));
    localStorage.setItem("colorData",JSON.stringify(local.NewLocal()));
    row = 1;
  }
  // 접속 기록이 있을 때
  else{
    let recentDate = localStorage.getItem("recentDate");
    let now = new Date();
    // 최근 접속 날짜와 다를 때
    if(recentDate != now.toLocaleDateString()){
      localStorage.removeItem("colorData");
      localStorage.setItem("colorData",JSON.stringify(local.NewLocal()));
      localStorage.setItem("recentDate", now.toLocaleDateString());
    }
    // localStorage에 있는 data 적용
    let tryNumber = JSON.parse(localStorage.getItem("colorData")).try;
    let words = JSON.parse(localStorage.getItem("colorData")).word;

    row = 2*tryNumber+1;
    for(let i=0; i<tryNumber;i++){
      painting.PaintDisplay(2*i +1);
      PrintLetter(2*i +1,words[i]);
    }
  }

  let solved = JSON.parse(localStorage.getItem("colorData")).solved;
  if(solved) {
    $(".add").attr("disabled", true);
    $(".del").attr("disabled", true);
    $(".enter").attr("disabled", true);
    $(document).keypress(function(e){
      e.preventDefault();
    })
  }
  else {  // 글자 추가
    $(".add").on("click", function () {
      AddLetter($(this).text())
    });
    // 키보드 눌렀을 때
    $(document).keydown(function (event) {
      if(!solved) {
        switch (event.keyCode) {
          case 8:
            DelLetter();
            break;
          case 13:
            EnterLetter();
          default:
            break;
        }

        if ((65 <= event.keyCode) && (event.keyCode <= 90)) {
          if (event.shiftKey && Object.keys(shiftTo).indexOf(event.keyCode.toString()) != -1) {

            AddLetter(shiftTo[event.keyCode]);
          } else {
            AddLetter(EnglishToKorean[String.fromCharCode(event.keyCode)]);
          }

        }
      }
    });

    // 글자 지우기
    $(".del").click(function () {
      DelLetter()
    });

    // 답장 전송
    $(".enter").click(function () {
      EnterLetter()
    });

    // shift 키
    $(".shiftkey").click(function ShiftLetter() {
      $(".shift").toggle();
    });

    $(".shiftAfter").click(function () {
      $(".shift").toggle();
    });
  }

});
