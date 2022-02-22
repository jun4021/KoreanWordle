import * as local from "./localStorageControl.js";
import * as toast from "./toast.js";

export function score() {
    const stat = JSON.parse(localStorage.getItem("statistics"));
    $("#play").text(stat.play);
    getTime();
    setInterval(getTime,1000);
    if(stat.success != 0) {
        $("#winRate").text(Math.round(stat.success / stat.play * 100) + "%");
        document.getElementById("graph1").max = stat.success;
        document.getElementById("graph2").max = stat.success;
        document.getElementById("graph3").max = stat.success;
        document.getElementById("graph4").max = stat.success;
        document.getElementById("graph5").max = stat.success;

        document.getElementById("graph1").value = stat.guess[1];
        document.getElementById("graph2").value = stat.guess[2];
        document.getElementById("graph3").value = stat.guess[3];
        document.getElementById("graph4").value = stat.guess[4];
        document.getElementById("graph5").value = stat.guess[5];

        $("#combo").text(stat.combo);
        $("#maxcombo").text(stat.maxcombo);


    }
}

function getTime(){
    const now = new Date();
    const hour = 23-now.getHours();
    const min = 59-now.getMinutes();
    const sec = 59-now.getSeconds();
    let timetext = `${hour<10 ? `0${hour}`:hour}:${min<10 ? `0${min}`:min}:${sec<10 ? `0${sec}`:sec}`;
    $("#time").text(timetext);

}
const closebutton = $(".close");

function openModal(a){

    document.getElementsByClassName(a)[0].style.display = "flex";
    history.pushState({page:"modal"},document.title);

}
const closeModal = () => {
    $(".modal").hide();
};

window.addEventListener("popstate",closeModal);

closebutton.on("click",function(){closeModal()});
$(".header_button").on("click",function(){openModal($(this).attr("id"))});
$(".modal-overlay").on("click",function(){closeModal()});

// $("#ClearTest").on("click",function(){
//     localStorage.removeItem("colorData");
//     localStorage.setItem("colorData",JSON.stringify(local.NewLocal()));
// });

$("#score").on("click", function(){score()});

function dateFormat(date){
    let year = date.getFullYear();
    let month = date.getMonth()+1;
    let day = date.getDate();
    return year+". "+month+". "+day+" ";
}
function copyToClipboard(val) {
    let t = document.createElement("textarea");
    document.body.appendChild(t);
    t.value = val;
    t.select();
    document.execCommand('copy');
    document.body.removeChild(t);
}

$(".share").on("click",function() {
    let solved = JSON.parse(localStorage.getItem("colorData")).solved;
    let copyInfo;
    if(solved) {
        const data = JSON.parse(localStorage.getItem("colorData"));
        const date = dateFormat(new Date());
        copyInfo = "#한글 워들 " + date + data.try + "/5" + "\n" + "https://koreanwordle.com" + "\n";
        for (let i = 0; i < data.try; i++) {
            let colorRow = "⬜⬜⬜⬜⬜⬜⬜⬜⬜";
            colorRow = Array.from(colorRow);
            const colorData = data.color[i];

            for (let i of colorData["grey"]) {
                colorRow[i] = "⬛";
            }
            for (let i of colorData["yellow"]) {
                colorRow[i] = "🟨";
            }
            for (let i of colorData["green"]) {
                colorRow[i] = "🟩";
            }
            let addColorRow = [];
            for (let i = 0; i < 9; i++) {
                if ((i == 3) || (i == 6)) {
                    addColorRow.push(" ");
                }
                addColorRow.push(colorRow[i]);
            }
            colorRow = addColorRow.join('');

            copyInfo = copyInfo + colorRow + "\n";
        }

    }
    else{
        copyInfo = "#한글 워들 " + "https://koreanwordle.com";
    }
    //navigator.clipboard.writeText(copyInfo);
    copyToClipboard(copyInfo);
    toast.toast("복사 완료!");



});


$("#opinion").on("click",function() {
    $(location).attr("href","https://forms.gle/K54r4dB5xxLY7a7S9");
});



