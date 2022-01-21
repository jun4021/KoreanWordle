export function ajaxTest(checkAnswer){
    $.ajax({
        url:"/correct",
        type:"POST",
        data:{
            answer:checkAnswer
        },
        success:function(data){
            alert(data.Msg);
        },
        error: function(){
            alert("error");
        }
    });
}
