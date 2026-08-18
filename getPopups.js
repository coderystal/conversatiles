function cre8ele(tag, innerHTML='', display='', width='', id='') {
    let ele = document.createElement(tag)
    if (innerHTML)
        ele.innerHTML = innerHTML
    if (display != '') {
        ele.style.display = display
    }
    if (width != '') {
        ele.style.width = width
    } else {
        ele.style.marginRight = "10px"
    }
    if (id != '') {
        ele.id = id
    }
    return ele
}

function createButton(btntext, btnfunc = ()=>{}, btnid='') {
    let button = cre8ele("button", btntext)
    button.onclick = btnfunc
    if (btnid != '')
        button.id = btnid
    return button
}

function popup() {
    let modalcontent = document.querySelector(".modal-content")
    modal.style.display = 'flex'
    modalcontent.innerHTML = "..."
    return modalcontent
}

function createTr(questionIndex) {
    let tr = document.createElement("tr")
    let ind = document.createElement("td")
    ind.innerHTML = questionIndex + 1
    let qust = document.createElement("td")
    qust.innerHTML = completequestions[questionIndex][0]
    qust.classList.add(completequestions[questionIndex][1])
    tr.appendChild(ind)
    tr.appendChild(qust)
    return tr
}

function popupViewed() {
    let modalcontent = popup()

    let table = document.createElement("table")

    let history = viewedIndex.filter((viewedIndex) => viewedIndex[0]).map((viewedIndex) => viewedIndex[1])
    table.innerHTML = "<tr><th colspan='2'>" + (history.length == 0 ? "No deck history." : "Deck history") + "</th></tr>"

    history.forEach(
        (viewedI) => { table.appendChild(createTr(subdeckIndexes[viewedI])) }
    )
    modalcontent.innerHTML = ""
    modalcontent.appendChild(table)
}

function upperCase(str) {
    return str.charAt(0).toUpperCase() + str.slice(1)
}

function popupAll() {
    let modalcontent = popup()

    let table = document.createElement("table")
    let mod = document.getElementById("deckmodtext").innerHTML
    let cat = document.getElementById("deckcattext").innerHTML
    upperDeck = (mod=="" ? "" : (upperCase(mod) + " ")) + upperCase(cat)
    table.innerHTML = "<tr><th colspan='2' class='" + cat + "'>" + (subdeckIndexes.length == 0 ? "No cards in deck." : upperDeck + " Deck") + "</th></tr>"
    subdeckIndexes.forEach(
        (questionIndex) => { table.appendChild(createTr(questionIndex)) }
    )
    modalcontent.innerHTML = ""
    modalcontent.appendChild(table)
}

let deckDict

function calcCustomDeckSize() {
    deckDict = getDeckDictFromForm()
    let customsize = getSubdeckIndexesAdvanced(deckDict).length
    document.getElementById("customdeckfeedback").innerHTML = customsize
    if (customsize == 0)
        document.getElementById("applycustomdeck").disabled = true
    else if (document.getElementById("applycustomdeck").disabled)
        document.getElementById("applycustomdeck").disabled = false
}

function getFlexDivWithLabeledCheckboxList(label, list) {
    let flexdiv = cre8ele("div", "<b>" + label + "</b>: " + (label == "Intensity" ? "<br>" : ""))
    

    flexdiv.style.flexWrap = "wrap"
    if (list.length > 2 || list.size > 2) {
        flexdiv.appendChild(createButton("default", () => {
            list.forEach((value, i)=>document.getElementById(label+"-"+i).checked = true); calcCustomDeckSize()
        }, label+"-defaultbutton"))
        flexdiv.appendChild(createButton("clear",  () => {
            list.forEach((value, i)=>document.getElementById(label+"-"+i).checked = false); calcCustomDeckSize()
        }, label+"-clearbutton"))
        flexdiv.appendChild(cre8ele("br"))
    }
    list.forEach((item,i) => {
        let checkboxItem = cre8ele("span")
        let checkboxInput = cre8ele("input")
        checkboxInput.id = label+"-"+i
        checkboxInput.type = "checkbox"
        checkboxInput.checked = true
        checkboxInput.onchange = calcCustomDeckSize
        checkboxItem.appendChild(checkboxInput)
        checkboxItem.appendChild(cre8ele("span", item))
        checkboxItem.classList.add('checkboxItem')
        checkboxItem.onclick = (event) => {
            if (event.target != checkboxInput) {
                checkboxInput.checked = !checkboxInput.checked
                calcCustomDeckSize()
                event.stopPropagation();
            }
        }
        flexdiv.appendChild(checkboxItem) 


        if (label=="Intensity") {
            checkboxItem.style.marginBottom = "8px"
            // flexdiv.appendChild(document.createElement("br"))
            // flexdiv.appendChild(document.createElement("br"))
        }
        // } else {
        //     checkboxItem.appendChild(document.createElement("br"))
        //     "&emsp;"
        // }
    })
    return flexdiv
}


let sec1CheckboxDict = [
    ["Categories",cats],                                            //arr[1]
    ["Intensity",intensityDict.slice(1)],                           //arr[6]
    ["Specificity",["purposefully vague","generally unambiguous"]]  //arr[2]
]
let sec2CheckboxDict = [
    ["Details",["includes comments/suggestions","question only"]],  //arr[5]
    ["Sources", unqSrcsArr],                                        //arr[3]
    ["Reviewed",["edited by coderystal","unedited"]]                //arr[4]
]
let commonwords = ["change", "love", "family", "favorite"]




function setcustom(complete) {
    sec1CheckboxDict.forEach((checkboxSetTuple)=>{
        let label = checkboxSetTuple[0]
        
        checkboxSetTuple[1].forEach((value, i)=>document.getElementById(label+"-"+i).checked = complete)
    })

    
    document.getElementById("keywordinput").value=''

    sec2CheckboxDict.forEach((checkboxSetTuple)=>{
        let label = checkboxSetTuple[0]
        
        checkboxSetTuple[1].forEach((value, i)=>document.getElementById(label+"-"+i).checked = complete)
    })

    calcCustomDeckSize()
}

function popupWarnAdvancedInProgress() {
    let modalcontent = document.querySelector(".modal-content")
    modalcontent.innerHTML = "<b>I would love for you to assemble the perfect deck for your current context</b><br>"+
        "And you can... if you really want to... but please be warned...<br>"+
        "I've enabled this feature through a very basic form... it is... barely functional...<br>"+
        "I want those who want to manipulate this deck in all possible ways to be able to, and not have to wait until I someday make it look nice,<br>"+
        "but if the presets suffice for you, please spare yourself, and let me save face, by not proceeding to the next page, which is in progress to become more user-friendly.<br>"+
        "Consider yourself warned.<br>" + 
        "(if u can find any bugs pls let me know)<br>"
    modalcontent.appendChild(createButton("Use a preset deck", popupDeckCustomizer))
    modalcontent.appendChild(createButton("Continue to custom deck form", popupAdvanced))
}

function popupAdvanced() {
    let modalcontent = document.querySelector(".modal-content")
    modalcontent.innerHTML = ""

    modalcontent.appendChild(cre8ele("b", "Customize Deck"))
    modalcontent.appendChild(createButton("Use this custom deck!", submitcustomdeck, "applycustomdeck"))
    modalcontent.appendChild(cre8ele("span", "it has"))
    modalcontent.appendChild(cre8ele("span", "", "", "", "customdeckfeedback"))
    modalcontent.appendChild(cre8ele("span", "questions"))
    modalcontent.appendChild(document.createElement("br"))
    
    let div1 = cre8ele("div", "", 'inline-block', '50%')
    sec1CheckboxDict.forEach((checkboxSetTuple)=>{
        div1.appendChild(document.createElement("br"))
        div1.appendChild(document.createElement("br"))
        div1.appendChild(getFlexDivWithLabeledCheckboxList(
            checkboxSetTuple[0], checkboxSetTuple[1]))
    })

    //arr[0]
    div1.appendChild(document.createElement("br"))
    div1.appendChild(document.createElement("br"))
    div1.appendChild(cre8ele("span", "contains text:"))
    let keywordinputele = cre8ele("input", '', '', '', "keywordinput")
    keywordinputele.oninput = calcCustomDeckSize
    div1.appendChild(keywordinputele)
    div1.appendChild(document.createElement("br"))
    div1.appendChild(cre8ele("span", "try:"))
    commonwords.forEach((word)=>{
        let atag = cre8ele("a", word)
        atag.onclick = () => {
            document.getElementById('keywordinput').value = word
            calcCustomDeckSize()
        }
        div1.appendChild(atag)
    })



    let div2 = cre8ele("div", "", 'inline-block', '50%')
    div2.appendChild(createButton("Set to Complete", () => {setcustom(true)}))
    div2.appendChild(createButton("Set to Empty", () => {setcustom(false)}))
    for (let i = 0; i < 8; i++)
        div2.appendChild(cre8ele("br"))
    sec2CheckboxDict.forEach((checkboxSetTuple)=>{
        div2.appendChild(document.createElement("br"))
        div2.appendChild(document.createElement("br"))
        div2.appendChild(getFlexDivWithLabeledCheckboxList(
            checkboxSetTuple[0], checkboxSetTuple[1]))
    })

    modalcontent.appendChild(div1)
    modalcontent.appendChild(div2)
    modalcontent.appendChild(cre8ele("span", "<br><br>Each question includes: 1) question, 2) category, 3) vague status, 4) source, 5) edit status, 6) comments, 7) intensity"))
    calcCustomDeckSize()
}

function getDeckDictFromForm() {
    let deckDict = {}
    let allCheckboxDict = [...sec1CheckboxDict, ...sec2CheckboxDict]
    allCheckboxDict.forEach((checkboxSetTuple)=>{
        let label = checkboxSetTuple[0]
        let checkboxVals = checkboxSetTuple[1].map((value, i)=>document.getElementById(label+"-"+i).checked) //eg id = Categories-0

        deckDict[label] = checkboxVals
    })
    deckDict["Keyword"] = document.getElementById("keywordinput").value

    if (deckDict["Categories"].filter(x => x===true).length == 1) {
        for (let i = 0; i < cats.length; i++) {
            if (deckDict.Categories[i]) {
                document.getElementById("deckmodtext").innerHTML = (
                    deckDict.Intensity.filter(x => x===false).length > 0 ||
                    deckDict.Specificity.filter(x => x===false).length > 0 ||
                    deckDict.Details.filter(x => x===false).length > 0 ||
                    deckDict.Sources.filter(x => x===false).length > 0 ||
                    deckDict.Reviewed.filter(x => x===false).length > 0 ||
                    deckDict.Keyword != ""
                ) ? "modified" : ""
                document.getElementById("deckcattext").innerHTML = cats[i]
                deck = cats[i]
                break;
            }
        }
    } else {
        document.getElementById("deckmodtext").innerHTML = ""
        document.getElementById("deckcattext").innerHTML = "custom"
        deck = "custom"
    }

    return deckDict
}

async function send(event) {
    event.stopPropagation();
    const shareData = {
        title: "Conversatiles",
        text: "but think about this...",
        url: "https://coderystal.github.io/conversatiles/?"+
            deck == "custom" ? 
                ("deck=complete&question="+(cardnum)+"&complete=true") :
                ("deck="+deck+"&question="+(current+1)+"&complete="+complete)
    };

    // Share must be triggered by "user activation"
    try {
        await navigator.share(shareData);
        console.log("MDN shared successfully");
    } catch (err) {
        let modalcontent = popup()
        let questiontext = question.innerText.split("\n")[1]
        modalcontent.innerHTML = "<b>Send this question to a friend!</b>"+
            "<button id='copyToClipboardButton' onclick='copyToClipboard(\""+shareData.url+"\\n"+questiontext+"\")' style='margin: 10px;'>Copy</button>"+
            "<br><div style='border: 1px solid black; padding: 5px;'>"+
            questiontext + "<br><span id='urltocopy'>" + shareData.url+"</span></div>"
    }
}

async function copyToClipboard(text) {
    try {
        await navigator.clipboard.writeText(text);
        document.getElementById("copyToClipboardButton").innerHTML = "Copied!"
        setTimeout(()=>{document.getElementById("copyToClipboardButton").innerHTML = "Copy"}, 1000)
    } catch (err) {
        document.getElementById("copyToClipboardButton").innerHTML = "Copy failed :("
    }
}