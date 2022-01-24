export function PaintDisplay(letter, row, colorData){
    let rowColorTile = document.getElementById('game-board')
        .childNodes[row]


    for(let i of colorData.green){
        rowColorTile.childNodes[2*i+1].style.backgroundColor ="green";
        document.getElementById(letter[i]).style.backgroundColor ="green";
        document.getElementById(letter[i]).style.color = "white"
    }
    for(let i of colorData.yellow){
        rowColorTile.childNodes[2*i+1].style.backgroundColor ="yellow";
        document.getElementById(letter[i]).style.backgroundColor ="yellow";
        document.getElementById(letter[i]).style.color = "black"
    }
    for(let i of colorData.grey){
        rowColorTile.childNodes[2*i+1].style.backgroundColor ="grey";
        document.getElementById(letter[i]).style.backgroundColor ="grey";
        document.getElementById(letter[i]).style.color = "white"
    }
}
