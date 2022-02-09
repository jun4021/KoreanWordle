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
    const sec = 60-now.getSeconds();
    let timetext = `${hour<10 ? `0${hour}`:hour}:${min<10 ? `0${min}`:min}:${sec<10 ? `0${sec}`:sec}`;
    $("#time").text(timetext);

}
const closebutton = $(".close");

function openModal(a){

    document.getElementsByClassName(a)[0].style.display = "flex";

}
const closeModal = () => {
    $(".modal").hide();
    window.location.reload();
}

closebutton.on("click",function(){closeModal()});
$(".header_button").on("click",function(){openModal($(this).attr("id"))});
$(".modal-overlay").on("click",function(){closeModal()});

$("#ClearTest").on("click",function(){
    localStorage.removeItem("colorData");
    localStorage.setItem("colorData",JSON.stringify(local.NewLocal()));
});

$("#score").on("click", function(){score()});

$(".share").on("click",function() {
    navigator.clipboard.writeText("http://koreanwordle.com");
    toast.toast("Copied");
});

$("#opinion").on("click",function() {
    $(location).attr("href","https://forms.gle/K54r4dB5xxLY7a7S9");
});



