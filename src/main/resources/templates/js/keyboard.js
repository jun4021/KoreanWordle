let row = 1;
let MAX_ROW = 2 *2-1
let letters = [];

import * as correct from "./correct.js";

$(document).onload(function(){
  // 칸에 현재 입력 받은 문자열 출력
  function PrintLetters(lettersAssemble){
    let rowTile = document.getElementById('game-board')
        .childNodes[row]

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
  // 글자 추가
  $(".add").click(function AddLetter(){

    letters.push($(this).text());
    let lettersAssemble = Hangul.assemble(letters)

    if(lettersAssemble.length == 4) // 글자 수 제한 초과
    {
      letters.pop();
      alert("글자는 3글자 제한");
      $(".add").attr("disabled",true);
      return;
    }
    else
    {
      PrintLetters(lettersAssemble);
    }
  });
  // 글자 지우기
  $(".del").click(function AddLetter(){
    letters.pop();
    let lettersAssemble = Hangul.assemble(letters);
    PrintLetters(lettersAssemble);

    $(".add").attr("disabled",false);
  });

  // 답장 전송
  $(".enter").click(function AddLetter(){
    let lettersAssemble = Hangul.assemble(letters);

    if(lettersAssemble.length == 3 && Hangul.isCompleteAll(lettersAssemble)) // 정상 종료 조건
    {
      letters = [];
      row += 2
      alert("정답 체크");

      correct.ajaxTest(lettersAssemble);
    }
    else{
      alert("완성되지 않은 글자가 있습니다");
    }
    $(".add").attr("disabled",false);
    // try 횟수 끝
    if(row > MAX_ROW){
      $("button").attr("disabled",true);
      alert("END");

    }

  });

});
