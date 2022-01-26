export function PaintDisplay(row, colorData){
    let rowColorTile = document.getElementById('game-board')
        .childNodes[row]

    let CHO = ["ㄱ","ㄲ","ㄴ","ㄷ","ㄸ","ㄹ","ㅁ","ㅂ","ㅃ", "ㅅ","ㅆ","ㅇ","ㅈ","ㅉ","ㅊ","ㅋ","ㅌ","ㅍ","ㅎ"];
    let JOONG = ["ㅏ","ㅐ","ㅑ","ㅒ","ㅓ","ㅔ","ㅕ","ㅖ","ㅗ","ㅘ", "ㅙ","ㅚ","ㅛ","ㅜ","ㅝ","ㅞ","ㅟ","ㅠ","ㅡ","ㅢ","ㅣ"];
    let JONG = ["","ㄱ","ㄲ","ㄳ","ㄴ","ㄵ","ㄶ","ㄷ","ㄹ","ㄺ","ㄻ","ㄼ", "ㄽ","ㄾ","ㄿ","ㅀ","ㅁ","ㅂ","ㅄ","ㅅ","ㅆ","ㅇ","ㅈ","ㅊ","ㅋ","ㅌ","ㅍ","ㅎ"];

    let letter = colorData.separateLetters;

    for(let i of colorData.grey){
        if ( document.getElementById(letter[i]) != null ) {
            document.getElementById(letter[i]).style.backgroundColor ="grey";
            document.getElementById(letter[i]).style.color = "white"
        }
        rowColorTile.childNodes[2*i+1].style.backgroundColor ="grey";

    }
    for(let i of colorData.grey_keyboard){
        if ( document.getElementById(i) != null ) {
            document.getElementById(i).style.backgroundColor ="grey";
            document.getElementById(i).style.color = "white"
        }

    }

    for(let i of colorData.yellow){
        if ( document.getElementById(letter[i]) != null ) {
            document.getElementById(letter[i]).style.backgroundColor ="yellow";
            document.getElementById(letter[i]).style.color = "black"
        }
        rowColorTile.childNodes[2*i+1].style.backgroundColor ="yellow";

    }
    for(let i of colorData.yellow_keyboard){
        if ( document.getElementById(i) != null ) {
            document.getElementById(i).style.backgroundColor ="yellow";
            document.getElementById(i).style.color = "black"
        }
    }

    for(let i of colorData.green){
        if ( document.getElementById(letter[i]) != null ) {
            document.getElementById(letter[i]).style.backgroundColor ="green";
            document.getElementById(letter[i]).style.color = "white"
        }
        rowColorTile.childNodes[2*i+1].style.backgroundColor ="green";

    }
    for(let i of colorData.green_keyboard){
        if ( document.getElementById(i) != null ) {
            document.getElementById(i).style.backgroundColor ="green";
            document.getElementById(i).style.color = "white"
        }


    }
}
