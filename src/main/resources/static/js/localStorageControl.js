export function NewLocal(row, colorData){

    let local = new Object();
    local.try = 0;
    local.word = [];
    local.color = [];
    local.keyboard ={"ㅂ":"","ㅈ":"","ㄷ":"","ㄱ":"","ㅅ":"","ㅛ":"","ㅕ":"","ㅑ":"","ㅐ":"","ㅔ":"",
        "ㅁ":"","ㄴ":"","ㅇ":"","ㄹ":"","ㅎ":"","ㅗ":"","ㅓ":"","ㅏ":"","ㅣ":"",
        "ㅋ":"","ㅌ":"","ㅊ":"","ㅍ":"","ㅠ":"","ㅜ":"","ㅡ":"",
        "ㅃ":"","ㅉ":"","ㄸ":"","ㄲ":"","ㅆ":"","ㅒ":"","ㅖ":""};

    return local;
}

export function writeLocal(data,Answer) {
    const nowData = JSON.parse(localStorage.getItem("colorData"));
    let colorData = new Object();
    colorData.green = data.green;
    colorData.yellow = data.yellow;
    colorData.grey = data.grey;

    ClassifyKeyboardColor(nowData,data);
    nowData.try += 1;
    nowData.word.push(Answer);
    nowData.color.push(colorData);

    localStorage.setItem("colorData",JSON.stringify(nowData));

}

function ClassifyKeyboardColor(nowData,inputData){

    const letters = inputData.separateLetters;

    for(let i of inputData.green){
        let letter = letters[i];
        nowData.keyboard[letter] = "green";
    }
    for(let i of inputData.yellow){
        let letter = letters[i];
        if(nowData.keyboard[letter] != "green") {
            nowData.keyboard[letter] = "yellow";
        }
    }
    for(let i of inputData.grey){
        let letter = letters[i];
        if((nowData.keyboard[letter] != "green") && (nowData.keyboard[letter] != "yellow")) {
            nowData.keyboard[letter] = "grey";
        }
    }

    for(let i of inputData.green_keyboard){
        nowData.keyboard[i] = "green";
    }
    for(let i of inputData.yellow_keyboard){

        if(nowData.keyboard[i] != "green") {
            nowData.keyboard[i] = "yellow";
        }
    }
    for(let i of inputData.grey_keyboard){

        if((nowData.keyboard[i] != "green") && (nowData.keyboard[i] != "yellow")) {
            nowData.keyboard[i] = "grey";
        }
    }
}

export function NewStatisticsLocal(){
    let stat = new Object();
    stat.play = 0;
    stat.success = 0;
    stat.guess ={1:0,2:0,3:0,4:0,5:0,"fail":0};
    return stat;
}

export function StatisticsEdit(correctFlag){
    const stat = JSON.parse(localStorage.getItem("statistics"));
    const data = JSON.parse(localStorage.getItem("colorData"));
    stat.play += 1;
    console.log(correctFlag);
    if(correctFlag){
        stat.success +=1;
        stat.guess[data.try] +=1;
    }
    else{
        stat.guess["fail"] +=1;
    }
    localStorage.setItem("statistics",JSON.stringify(stat));
}