export function ajaxTest(checkAnswer){
    $.ajax({
        url:"/correct",
        type:"POST",
        data:{
            answer:checkAnswer
        },
        success:function(data){
            console.log(data);
        },
        error: function(){
            alert("error");
        }
    });
}
