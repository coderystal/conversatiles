function drawNewCard(cardind, back) {
    if (cardind == (numqs+1)) {
        //finished deck end screen

        var options = { weekday: 'long', year: 'numeric', month: 'numeric', day: 'numeric'};
        let endTime = new Date()
        let elapsTime = startTime.toLocaleString("en-US", options) + ", " + startTime.toLocaleString("en-US", {hour: '2-digit', minute: '2-digit', hour12: false}) + " - "
        if (!startTime.toDateString() === endTime.toDateString()) {
            elapsTime += endTime.toLocaleString("en-US", options) + " ";
        }
        elapsTime += endTime.toLocaleString("en-US", {hour: '2-digit', minute: '2-digit', hour12: false});

        
        document.getElementById("question").innerHTML = ""+
            "<span style = 'position:absolute;top:0;margin:5px;font-size: 2vh;'>" + elapsTime + "<br>Certified Conversatiler</span>" + 
            "<div class='cardq'>You've finished" +
            "<br>all <b>" + numqs + " cards</b> in the <br><b>" + document.getElementById("decktext").innerText + " deck</b>!" +
            "<div style='font-size:2vh'><span style = 'position: absolute; top:80%; left:20px'><span style='display: block;margin-bottom: 5px;'>Ready for more?</span><a onclick='exitFullScreen();popup();popupAdvanced()'>New deck</a></span>"+
            "<span style = 'position: absolute; top:80%; right:20px'>Have a good conversatime?<br>"+
            "<a href='https://buymeacoffee.com/jeyc35gujd' target='_blank'><img src='images/bmacoffee.png'></a><a onclick='send(event)'><img src='images/send.png'></a><a href='instagram-stories://share' class='instagram-story-btn' target='_blank'><img src='images/instagram.png'></a>"+                    
            "</span>"
        document.getElementById("question").style.color = "black"
        //see questions u missed using deckDict
        return
    }

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
            let params = (deck == "custom" || selmod == "modified") ? 
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