export function CheckAnswerCorrect(trynum,checkAnswer){
    let dataResult;
    $.ajax({
        url:"/correct",
        type:"POST",
        data:{
            trynum:trynum,
            answer:checkAnswer

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

