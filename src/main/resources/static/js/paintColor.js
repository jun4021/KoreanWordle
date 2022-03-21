export function PaintDisplay(row){
    let green;let yellow;let grey;let defaultColor;
    if(localStorage.getItem("dark") == "true"){
        green = "#058649";
        yellow = "#AA7E2A";
        grey = "#3A3A3C";
        defaultColor = "#E4E5E7";
    }
    else{
        green = "#05944F";
        yellow = "#BC8B2C";
        grey = "#787C7E";
        defaultColor = "#080B14";
    }

    let rowColorTile = document.getElementById('game-board')
        .childNodes[row].childNodes[3];

    const keyboardColorData = JSON.parse(localStorage.getItem("colorData")).keyboard;

    for(let key in keyboardColorData){
        if(keyboardColorData[key]=="grey"){
            if ( document.getElementById(key) != null ) {
                document.getElementById(key).style.backgroundColor = grey;
                document.getElementById(key).style.color = defaultColor;
            }
        }
        else if(keyboardColorData[key]=="yellow"){
            if ( document.getElementById(key) != null ) {
                document.getElementById(key).style.backgroundColor = yellow;
                document.getElementById(key).style.color = defaultColor;
            }
        }
        else if(keyboardColorData[key]=="green"){
            if ( document.getElementById(key) != null ) {
                document.getElementById(key).style.backgroundColor = green;
                document.getElementById(key).style.color = defaultColor;
            }
        }
    }

    const colorData = JSON.parse(localStorage.getItem("colorData")).color[(row-1)/2];

    for(let i of colorData["grey"]){
        rowColorTile.childNodes[2*i+1].style.backgroundColor =grey;
    }
    for(let i of colorData["yellow"]){
        rowColorTile.childNodes[2*i+1].style.backgroundColor =yellow;
    }
    for(let i of colorData["green"]){
        rowColorTile.childNodes[2*i+1].style.backgroundColor =green;
    }

}
