export function PaintDisplay(row){
    let rowColorTile = document.getElementById('game-board')
        .childNodes[row].childNodes[3];

    const keyboardColorData = JSON.parse(localStorage.getItem("colorData")).keyboard;

    for(let key in keyboardColorData){
        if(keyboardColorData[key]=="grey"){
            if ( document.getElementById(key) != null ) {
                document.getElementById(key).style.backgroundColor = "#787C7E";
                document.getElementById(key).style.color = "#080B14";
            }
        }
        else if(keyboardColorData[key]=="yellow"){
            if ( document.getElementById(key) != null ) {
                document.getElementById(key).style.backgroundColor = "#BC8B2C";
                document.getElementById(key).style.color = "#080B14";
            }
        }
        else if(keyboardColorData[key]=="green"){
            if ( document.getElementById(key) != null ) {
                document.getElementById(key).style.backgroundColor = "#05944F";
                document.getElementById(key).style.color = "#080B14";
            }
        }
    }

    const colorData = JSON.parse(localStorage.getItem("colorData")).color[(row-1)/2];

    for(let i of colorData["grey"]){
        rowColorTile.childNodes[2*i+1].style.backgroundColor ="#787C7E";
    }
    for(let i of colorData["yellow"]){
        rowColorTile.childNodes[2*i+1].style.backgroundColor ="#BC8B2C";
    }
    for(let i of colorData["green"]){
        rowColorTile.childNodes[2*i+1].style.backgroundColor ="#05944F";
    }

}
