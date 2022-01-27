export function PaintDisplay(row, colorData){
    let rowColorTile = document.getElementById('game-board')
        .childNodes[row].childNodes[3];

    console.log(rowColorTile);
    let CHO = ["ㄱ","ㄲ","ㄴ","ㄷ","ㄸ","ㄹ","ㅁ","ㅂ","ㅃ", "ㅅ","ㅆ","ㅇ","ㅈ","ㅉ","ㅊ","ㅋ","ㅌ","ㅍ","ㅎ"];
    let JOONG = ["ㅏ","ㅐ","ㅑ","ㅒ","ㅓ","ㅔ","ㅕ","ㅖ","ㅗ","ㅘ", "ㅙ","ㅚ","ㅛ","ㅜ","ㅝ","ㅞ","ㅟ","ㅠ","ㅡ","ㅢ","ㅣ"];
    let JONG = ["","ㄱ","ㄲ","ㄳ","ㄴ","ㄵ","ㄶ","ㄷ","ㄹ","ㄺ","ㄻ","ㄼ", "ㄽ","ㄾ","ㄿ","ㅀ","ㅁ","ㅂ","ㅄ","ㅅ","ㅆ","ㅇ","ㅈ","ㅊ","ㅋ","ㅌ","ㅍ","ㅎ"];

    let letter = colorData.separateLetters;

    for(let i of colorData.grey){
        if ( document.getElementById(letter[i]) != null ) {
            document.getElementById(letter[i]).style.backgroundColor ="#787C7E";
            document.getElementById(letter[i]).style.color = "#080B14"
        }
        rowColorTile.childNodes[2*i+1].style.backgroundColor ="#787C7E";

    }
    for(let i of colorData.grey_keyboard){
        if ( document.getElementById(i) != null ) {
            document.getElementById(i).style.backgroundColor ="#787C7E";
            document.getElementById(i).style.color = "#080B14"
        }

    }

    for(let i of colorData.yellow){
        if ( document.getElementById(letter[i]) != null ) {
            document.getElementById(letter[i]).style.backgroundColor ="#BC8B2C";
            document.getElementById(letter[i]).style.color = "#080B14"
        }
        rowColorTile.childNodes[2*i+1].style.backgroundColor ="#BC8B2C";

    }
    for(let i of colorData.yellow_keyboard){
        if ( document.getElementById(i) != null ) {
            document.getElementById(i).style.backgroundColor ="#BC8B2C";
            document.getElementById(i).style.color = "#080B14"
        }
    }

    for(let i of colorData.green){
        if ( document.getElementById(letter[i]) != null ) {
            document.getElementById(letter[i]).style.backgroundColor ="#05944F";
            document.getElementById(letter[i]).style.color = "#080B14"
        }
        rowColorTile.childNodes[2*i+1].style.backgroundColor ="#05944F";

    }
    for(let i of colorData.green_keyboard){
        if ( document.getElementById(i) != null ) {
            document.getElementById(i).style.backgroundColor ="#05944F";
            document.getElementById(i).style.color = "#080B14"
        }


    }
}
