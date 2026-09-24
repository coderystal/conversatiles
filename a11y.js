document.onkeydown = function (e) {
    if (e.target != document.getElementById("enteredcardnum") && e.target.id != "keywordinput") {
        if (/\d/.test(e.key) && !e.key.startsWith("F")) {
            console.log("type " + e.key)
            document.getElementById("enteredcardnum").value += e.key
        } else if (e.key == 'Enter') {
            if (document.getElementById("enteredcardnum").value.trim() != '')
                drawNewCard(parseInt(document.getElementById("enteredcardnum").value.trim()) - 1)
        } else if (e.key == 'Backspace') {
            document.getElementById("enteredcardnum").value = document.getElementById("enteredcardnum").value.slice(0, -1)
        } else if (e.key == 'Escape') {
            if (document.getElementById('infomodal').style.display != "none")
                closepopup();
            else
                exitFullScreen()
        } else
            switch (e.key.toLowerCase()) {
                case 'r': resetDeck(); break;
                case ' ': drawNewCard(getCardInd()); break; //don't use enter, expected behaviors
                case 'b': case 'arrowleft': drawNewCard(stack.pop(), true); break;
                case 'h': popupViewed(); break;
                case 'd': popupAll(); break;
                case 'a': showAbout(); break;
                case 't': showTitle(); break;
                case 'i': showInstructions(); break;
                case 'c': showCoderystal(); break;
                case 'o': showOrganization(); break;
                case 'f11': toggleScreen(); break;
                default: console.log(e.key, "is not a designated shortcut"); showA11y();
            }
    }
}

function logA11y() {
    console.log("If you're not trying to Reset, draw new card, go <-Back, see History, see Deck, "+
                "show About, show Title, show Instructions, show Coderystal info, show Organization, or F11 toggle screen. "+
                "then I have not yet implemented a keyboard shortcut for what you're trying to do. Let me know @coderystal")
}

function showA11y() {
    let currentlyFullscreen = document.getElementById("screentoggleimg").src.endsWith("exitFullscreen.png")
    if (currentlyFullscreen)
        exitFullScreen()
    document.getElementById("resetbutton").innerHTML = "Reset Deck (R)"
    document.getElementById("drawbutton").innerHTML = "Draw New Card (space)"
    document.getElementById("backbtn").innerHTML = "Back (B)"
    document.getElementById("numviewed").innerHTML = viewed + " cards seen (H)"
    document.getElementById("numcards").innerHTML = numqs + " cards (D)"
    document.getElementById("about").innerHTML = "About (A)"
    document.getElementById("title").innerHTML = "Title (T)"
    document.getElementById("instructions").innerHTML = "Instructions (I)"
    document.getElementById("coderystal").innerHTML = "Coderystal (C)"
    document.getElementById("screentoggleA11y").innerHTML = "F11"
    setTimeout(() => {
        if (currentlyFullscreen)
            fullScreen()
        document.getElementById("resetbutton").innerHTML = "Reset Deck"
        document.getElementById("drawbutton").innerHTML = "Draw New Card"
        document.getElementById("backbtn").innerHTML = "Back"
        document.getElementById("numviewed").innerHTML = viewed + " cards seen"
        document.getElementById("numcards").innerHTML = numqs + " cards"
        document.getElementById("about").innerHTML = "About"
        document.getElementById("title").innerHTML = "Title"
        document.getElementById("instructions").innerHTML = "Instructions"
        document.getElementById("coderystal").innerHTML = "Coderystal"
        document.getElementById("screentoggleA11y").innerHTML = ""
    }, 1000)
}