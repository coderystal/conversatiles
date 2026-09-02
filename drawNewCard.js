function drawNewCard(cardind, back) {
    if (/^\d+$/.test(cardind)) { //if cardind is a positive integer
        if (cardind < 0 || cardind >= numqs) {
            document.getElementById("question").innerHTML = "<br><div class='cardq'>#" +
                document.getElementById("enteredcardnum").value + "<br><b>does not exist</b><br>" +
                "There are only " + numqs + " cards in the " + document.getElementById("decktext").innerText + " deck."
            document.getElementById("question").style.color = "black"
            current = cardind
            return false
        }
        else {
            if (!back && current != cardind)
                stack.push(current)
            current = cardind
            cardnum = parseInt(subdeckIndexes[current]) + 1
            let qust = completequestions[subdeckIndexes[current]]
            let params = deck == "custom" ? 
                ("deck=complete&question="+(cardnum)+"&complete=true") :
                ("deck="+deck+"&question="+(current+1)+"&complete="+complete)
            document.getElementById("question").innerHTML = "<span style = 'position:absolute;top:0;margin:5px;font-size: 2vh;'>#" + cardnum + "</span>" +
                "<div class='cardq' style='color:" + ((qust[2]) ? "red" : "black") + "'><b>" + qust[0] +
                "</b></div><span class = 'cardatt'>"+
                    "<a href='cardinfo.html?"+params+"' onclick='event.stopPropagation()'><img src='images/info.png'></a>"+
                    "<a onclick='send(event)'><img src='images/send.png'></a>"+
                    "<span>"
            if (!viewedIndex[current][0])
                viewed++
            viewedIndex[current][0] = true
            updateCardsSeenText()
            if (viewed > 0)
                document.getElementById("resetbutton").disabled = false;
            if (viewed == numqs)
                document.getElementById("drawbutton").disabled = true;
            document.cookie = "deck="+deck
            document.cookie = "question="+cardnum;
            document.cookie = "complete="+complete
        }
        document.getElementById("backbtn").disabled = stack.length <= 1;
        document.getElementById("enteredcardnum").value = (current+1)
    }
    return true
}