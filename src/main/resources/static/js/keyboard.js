import {CheckAnswerCorrect} from "./correct.js";
let MAX_ROW = 5;

let row = 1;
let letters = [];
let shiftFlag = false;

import * as correct from "./correct.js";
import * as painting from "./paintColor.js";

// 칸에 현재 입력 받은 문자열 출력
function PrintLetters(lettersAssemble){
  let rowTile = document.getElementById('game-board')
      .childNodes[row].childNodes[1]


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

function AddLetter(letter) {

  letters.push(letter);
  let lettersAssemble = Hangul.assemble(letters)

  if (lettersAssemble.length == 4) // 글자 수 제한 초과
  {
    letters.pop();
    alert("글자는 3글자 제한");
    $(".add").attr("disabled", true);
    return;
  } else {
    PrintLetters(lettersAssemble);
  }
}
function DelLetter(){
  letters.pop();
  let lettersAssemble = Hangul.assemble(letters);
  PrintLetters(lettersAssemble);

  $(".add").attr("disabled",false);
}

function EnterLetter() {
  let lettersAssemble = Hangul.assemble(letters);

  if (lettersAssemble.length == 3 && Hangul.isCompleteAll(lettersAssemble)) // 정상 종료 조건
  {

    alert("정답 체크");
    let data = correct.CheckAnswerCorrect(lettersAssemble);
    if (!data.validWord) {
      alert("단어 리스트에 없습니다.")

      return;
    } else if (data.correct) {
      alert("정답입니다.");
      painting.PaintDisplay(row, data);
      $(".add").attr("disabled", true);
      $(".del").attr("disabled", true);
      $(".enter").attr("disabled", true);
      return;
    }

    painting.PaintDisplay(row, data);
    row += 2;
    letters = [];
  } else {
    alert("완성되지 않은 글자가 있습니다");
  }
  $(".add").attr("disabled", false);

  // try 횟수 끝
  if (row > MAX_ROW * 4 - 1) {
    $(".add").attr("disabled", true);
    $(".del").attr("disabled", true);
    $(".enter").attr("disabled", true);
    alert("END");

  }
}
let EnglishToKorean = {"Q":"ㅂ","W":"ㅈ","E":"ㄷ","R":"ㄱ","T":"ㅅ","Y":"ㅛ","U":"ㅕ","I":"ㅑ","O":"ㅐ","P":"ㅔ","A":"ㅁ","S":"ㄴ","D":"ㅇ","F":"ㄹ","G":"ㅎ","H":"ㅗ","J":"ㅓ","K":"ㅏ","L":"ㅣ","Z":"ㅋ","X":"ㅌ","C":"ㅊ","V":"ㅍ","B":"ㅠ","N":"ㅜ","M":"ㅡ"};
let shiftTo = {81:"ㅃ",87:"ㅉ",69:"ㄸ",82:"ㄲ",84:"ㅆ",79:"ㅒ",80:"ㅖ"};

$(document).ready(function(){
  $(".modal").hide();
  // 글자 추가
  $(".add").on("click",function(){AddLetter($(this).text())});

  $(document).keydown(function(event){

    switch (event.keyCode){
      case 8:
        DelLetter();
        break;
      case 13:
        EnterLetter();
      default:
        break;
    }

    if((65 <= event.keyCode) && (event.keyCode <= 90)) {
      if (event.shiftKey && Object.keys(shiftTo).indexOf(event.keyCode.toString())!=-1){


        AddLetter(shiftTo[event.keyCode]);
      }
      else {
        AddLetter(EnglishToKorean[String.fromCharCode(event.keyCode)]);
      }

    }
  });

  // 글자 지우기
  $(".del").click(function(){DelLetter()});

  // 답장 전송
  $(".enter").click(function() {EnterLetter()});


// shift 키
  $(".shiftkey").click(function ShiftLetter(){
    $(".shift").toggle();
  });

  $(".shiftAfter").click(function(){
    $(".shift").toggle();
  });


});
