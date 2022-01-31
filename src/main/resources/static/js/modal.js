
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
    localStorage.clear();
});