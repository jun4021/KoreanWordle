import {CheckAnswerCorrect} from "./correct.js";
let MAX_ROW = 5;

let row = 1;
let letters = [];


import * as correct from "./correct.js";
import * as painting from "./paintColor.js";
import * as local from "./localStorageControl.js";
import * as toast from "./toast.js";
import * as modal from "./modal.js";
import {writeSolveLocal} from "./localStorageControl.js";


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
function DelLetter(){
    letters.pop();
    let lettersAssemble = Hangul.assemble(letters);
    PrintLetters(row, lettersAssemble);

    $(".add").attr("disabled", false);
}
function SendWordRequest(word){
    let dataResult;
    $.ajax({
        url:"/requestWord",
        type:"GET",
        data:{

            request:word

        },
        async:false,
        success:function(data){

            dataResult = data;
        },
        error: function(){
            alert("error");
        }
    });
    return dataResult;
}

function EnterLetter() {

    let lettersAssemble = Hangul.assemble(letters);


    if (lettersAssemble.length == 3 && Hangul.isCompleteAll(lettersAssemble)) // 정상 종료 조건
    {

        if(SendWordRequest(lettersAssemble)) {
            toast.toast("요청 완료!");

            const data = JSON.parse(localStorage.getItem("request"));

            if (data.requestWord.length == 4) {
                data.requestWord.shift();
            }
            data.requestWord.push(lettersAssemble);
            localStorage.setItem("request", JSON.stringify(data));

            for (let i = 0; i < 4; i++) {
                if (data.requestWord[i] == null) {
                    document.getElementById("word_list_p").childNodes[2 * i + 1].style.display = "none";
                } else {
                document.getElementById("word_list_p").childNodes[2 * i + 1].style.display = "flex";
                document.getElementById("word_list_p").childNodes[2 * i + 1].innerHTML = data.requestWord[i];
                }
            }
            letters =[];
            lettersAssemble = Hangul.assemble(letters);
            PrintLetters(row, lettersAssemble);
        }
        else{
            toast.toast("이미 단어리스트에 있습니다.")
        }

    } else {
        toast.toast("완성되지 않은 글자가 있습니다");
    }
    $(".add").attr("disabled", false);


}
let EnglishToKorean = {"Q":"ㅂ","W":"ㅈ","E":"ㄷ","R":"ㄱ","T":"ㅅ","Y":"ㅛ","U":"ㅕ","I":"ㅑ","O":"ㅐ","P":"ㅔ","A":"ㅁ","S":"ㄴ","D":"ㅇ","F":"ㄹ","G":"ㅎ","H":"ㅗ","J":"ㅓ","K":"ㅏ","L":"ㅣ","Z":"ㅋ","X":"ㅌ","C":"ㅊ","V":"ㅍ","B":"ㅠ","N":"ㅜ","M":"ㅡ"};
let shiftTo = {81:"ㅃ",87:"ㅉ",69:"ㄸ",82:"ㄲ",84:"ㅆ",79:"ㅒ",80:"ㅖ"};

$(document).ready(function(){
    if(localStorage.getItem("request")==null){
        localStorage.setItem("request",JSON.stringify(local.request()));
    }

    const data = JSON.parse(localStorage.getItem("request"));
    for(let i=0;i<4;i++) {
        if (data.requestWord[i] == null) {
            document.getElementById("word_list_p").childNodes[2 * i + 1].style.display = "none";
        } else {
            document.getElementById("word_list_p").childNodes[2 * i + 1].style.display = "flex";
            document.getElementById("word_list_p").childNodes[2 * i + 1].innerHTML = data.requestWord[i];
        }
    }


    $(".add").on("click", function () {
        AddLetter($(this).text())
    });
    // 키보드 눌렀을 때
    $(document).keydown(function (event) {
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
    });

    // 글자 지우기
    $(".del").click(function () {
        DelLetter()
    });

    // 답장 전송
    $("#wordadd_enter").click(function () {
        EnterLetter()
    });

    // shift 키
    $(".shiftkey").click(function ShiftLetter() {
        $(".shift").toggle();
    });

    $(".shiftAfter").click(function () {
        $(".shift").toggle();
    });


});
