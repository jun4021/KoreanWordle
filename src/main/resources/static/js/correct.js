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
            console.log(data);
            console.log(data.grey);
            console.log(data.yellow);
            console.log(data.green);
            dataResult = data;

        },
        error: function(){
            alert("error");
        }
    });
    return dataResult;
}

