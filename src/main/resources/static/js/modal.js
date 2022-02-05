import * as local from "./localStorageControl.js";

export function score() {
    const stat = JSON.parse(localStorage.getItem("statistics"));
    $("#play").text(stat.play);
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

const closebutton = $(".close");

function openModal(a){

    document.getElementsByClassName(a)[0].style.display = "flex";
}
const closeModal = () => {
    $(".modal").hide();
}

closebutton.on("click",function(){closeModal()});
$(".header_button").on("click",function(){openModal($(this).attr("id"))});
$(".modal-overlay").on("click",function(){closeModal()});

$("#ClearTest").on("click",function(){
    localStorage.removeItem("colorData");
    localStorage.setItem("colorData",JSON.stringify(local.NewLocal()));
});

$("#score").on("click", function(){score()});




