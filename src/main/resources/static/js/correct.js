export function CheckAnswerCorrect(checkAnswer){
    let dataResult;
    $.ajax({
        url:"/correct",
        type:"POST",
        data:{
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

