function closepopup() {
    modal.style.display = "none";
}

function cre8ele(tag, innerHTML='', display='', width='', id='', regmargin=false) {
    let ele = document.createElement(tag)
    if (innerHTML)
        ele.innerHTML = innerHTML
    if (display != '') {
        ele.style.display = display
    }
    if (width != '') {
        ele.style.width = width
    } else if (!regmargin) {
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

function createTr(questionIndex, deckIndex) {
    let tr = document.createElement("tr")
    let ind = document.createElement("td")
    ind.innerHTML = questionIndex + 1
    let qust = document.createElement("td")
    qust.innerHTML = completequestions[questionIndex][0]
    qust.classList.add(completequestions[questionIndex][1])
    let deckind = document.createElement("td")
    deckind.innerHTML = "<a>" + (deckIndex + 1) + "</a>"
    deckind.onclick = () => {closepopup(); drawNewCard(deckIndex)}
    tr.appendChild(ind)
    tr.appendChild(qust)
    tr.appendChild(deckind)
    return tr
}

function popupViewed() {
    let modalcontent = popup()

    let table = document.createElement("table")

    let history = viewedIndex.filter((viewedIndex) => viewedIndex[0]).map((viewedIndex) => viewedIndex[1])
    table.innerHTML = "<tr><th colspan='3'>" + (history.length == 0 ? "No deck history." : "Deck history") + "</th></tr>"

    history.forEach(
        (viewedI) => { table.appendChild(createTr(subdeckIndexes[viewedI], viewedI)) }
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
    table.innerHTML = "<tr><th colspan='3' class='" + cat + "'>" + (subdeckIndexes.length == 0 ? "No cards in deck." : upperDeck + " Deck") + "</th></tr>"
    subdeckIndexes.forEach(
        (questionIndex, i) => { table.appendChild(createTr(questionIndex, i)) }
    )
    modalcontent.innerHTML = ""
    modalcontent.appendChild(table)
}

let deckDict

function calcCustomDeckSize() {
    deckDict = getDeckDictFromForm()
    let customsize = getSubdeckIndexesAdvanced(deckDict).length
    document.getElementById("customdeckfeedback").innerHTML = customsize
    if (customsize == 1)
        document.getElementById("pluralquestionsindic").innerHTML = ""
    else
        document.getElementById("pluralquestionsindic").innerHTML = "s"

    if (customsize == 0)
        document.getElementById("applycustomdeck").disabled = true
    else if (document.getElementById("applycustomdeck").disabled)
        document.getElementById("applycustomdeck").disabled = false
}

function updateCustomDeckKeyword(newkeyword) {
    deckDict.Keyword = newkeyword
    let customsize = getSubdeckIndexesAdvanced(deckDict).length
    document.getElementById("customdeckfeedback").innerHTML = customsize
    if (customsize == 0)
        document.getElementById("applycustomdeck").disabled = true
    else if (document.getElementById("applycustomdeck").disabled)
        document.getElementById("applycustomdeck").disabled = false
}

function getFlexDivWithLabeledCheckboxList(label, list) {
    let selectedstate = cre8ele("div", "<b>" + label + "</b>: <span id='"+label+"selectedval'>any</span>")
    selectedstate.id = label+"selectedstate"
    selectedstate.style.padding = "5px"
    let flexdiv = cre8ele("div", "<b>" + label + "</b><br>")
    

    flexdiv.style.flexWrap = "wrap"
    flexdiv.appendChild(createButton(((list.length > 2 || list.size > 2) ? "default" : "both"), () => {
        list.forEach((value, i)=>document.getElementById(label+"-"+i).checked = true); calcCustomDeckSize(); document.getElementById(label+"selectedval").innerHTML = "any"
    }, label+"-defaultbutton"))
    flexdiv.appendChild(createButton(((list.length > 2 || list.size > 2) ? "clear" : "neither"),  () => {
        list.forEach((value, i)=>document.getElementById(label+"-"+i).checked = false); calcCustomDeckSize(); document.getElementById(label+"selectedval").innerHTML = "none"
    }, label+"-clearbutton"))
    flexdiv.appendChild(cre8ele("br"))

    list.forEach((item,i) => {
        let checkboxItem = cre8ele("span")
        let checkboxInput = cre8ele("input")
        checkboxInput.id = label+"-"+i
        checkboxInput.type = "checkbox"
        checkboxInput.checked = true
        checkboxInput.onchange = calcCustomDeckSize
        checkboxItem.appendChild(checkboxInput)
        checkboxItem.appendChild(cre8ele("span", item, "", "", label+"-"+i+"val"))
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

    selectedstate.onclick = () => {
        document.getElementById("customizedetaildiv").innerHTML = ""
        document.getElementById("customizedetaildiv").appendChild(flexdiv)

        let allCheckboxDict = [...sec1CheckboxDict, ...sec2CheckboxDict]
        allCheckboxDict.forEach((checkboxSetTuple)=>{
            let labelitr = checkboxSetTuple[0]
            if (labelitr == label) {
                selectedstate.style.border = "3px solid blue"
            }
            else
                document.getElementById(labelitr+"selectedstate").style.border = "0px"
        })
        document.getElementById("Keywordselectedstate").style.border = "0px"

    }
    return selectedstate
}

function createKeywordDiv() {
    let selectedstate = cre8ele("div", "<b>Keyword</b>: <span id='Keywordselectedval'><i>any</i></span>")
    selectedstate.id = "Keywordselectedstate"
    selectedstate.style.padding = "5px"

    let keyworddiv = document.createElement("div")
    keyworddiv.appendChild(cre8ele("span", "<b>Keyword</b><br>"))
    keyworddiv.appendChild(createButton("clear",  () => {
        document.getElementById('keywordinput').value = ""
        document.getElementById("Keywordselectedval").innerHTML = "<i>any<i>"
        calcCustomDeckSize()
    }, "Keyword-clearbutton"))
    keyworddiv.appendChild(cre8ele("span", "<br>contains text:"))
    let keywordinputele = cre8ele("input", '', '', '', "keywordinput")
    keywordinputele.oninput = () => {
        let newkeyword = keywordinputele.value
        updateCustomDeckKeyword(newkeyword)
        document.getElementById("Keywordselectedval").innerHTML = (newkeyword == "") ? "<i>any<i>" : newkeyword
    }
    keyworddiv.appendChild(keywordinputele)
    keyworddiv.appendChild(cre8ele("span", "<br>try:"))
    commonwords.forEach((word)=>{
        let atag = cre8ele("a", word)
        atag.onclick = () => {
            document.getElementById('keywordinput').value = word
            document.getElementById("Keywordselectedval").innerHTML = word
            calcCustomDeckSize()
        }
        atag.style.color = "blue"
        keyworddiv.appendChild(atag)
    })
    
    selectedstate.onclick = () => {
        document.getElementById("customizedetaildiv").innerHTML = ""
        document.getElementById("customizedetaildiv").appendChild(keyworddiv)

        let allCheckboxDict = [...sec1CheckboxDict, ...sec2CheckboxDict]
        allCheckboxDict.forEach((checkboxSetTuple)=>{
            let labelitr = checkboxSetTuple[0]
            document.getElementById(labelitr+"selectedstate").style.border = "0px"
        })
        selectedstate.style.border = "3px solid blue"

    }

    return selectedstate
}


let sec1CheckboxDict = [
    ["Category",cats],                                              //arr[1]
    ["Intensity",intensityDict.slice(1)],                           //arr[6]
    ["Specificity",["purposefully vague","generally unambiguous"]]  //arr[2]
]
let sec2CheckboxDict = [
    ["Details",["includes comments/suggestions","question only"]],  //arr[5]
    ["Source", unqSrcsArr],                                         //arr[3]
    ["Conversatility",["edited by coderystal","unedited"]]          //arr[4]
]
let commonwords = ["change", "love", "family", "favorite"]




function setcustom(complete) {
    let curlabel = document.getElementById("customizedetaildiv").innerText.split("\n")[0]

    let allCheckboxDict = [...sec1CheckboxDict, ...sec2CheckboxDict]

    allCheckboxDict.forEach((checkboxSetTuple)=>{
        let label = checkboxSetTuple[0]
        document.getElementById(label+"selectedstate").click()
        checkboxSetTuple[1].forEach((value, i)=>document.getElementById(label+"-"+i).checked = complete)
        document.getElementById(label+"selectedval").innerHTML = (complete ? "any" : "none")
    })

    
    document.getElementById("Keywordselectedstate").click()
    document.getElementById("keywordinput").value=''
    document.getElementById("Keywordselectedval").innerHTML = ("<i>any</i>")

    if (curlabel)
        document.getElementById(curlabel+"selectedstate").click()
    calcCustomDeckSize()
}

function popupAdvanced() {
    let modalcontent = document.querySelector(".modal-content")
    modalcontent.innerHTML = ""

    modalcontent.appendChild(cre8ele("b", "Customize Deck"))
    modalcontent.appendChild(createButton("Use this custom deck!", submitcustomdeck, "applycustomdeck"))
    modalcontent.appendChild(cre8ele("span", "it has"))
    modalcontent.appendChild(cre8ele("span", "", "", "", "customdeckfeedback"))
    modalcontent.appendChild(cre8ele("span", "question", "", "", "", true))
    modalcontent.appendChild(cre8ele("span", "s", "", "", "pluralquestionsindic"))
    modalcontent.appendChild(document.createElement("br"))

    modalcontent.appendChild(createButton("Set to Complete", () => {setcustom(true)}))
    modalcontent.appendChild(createButton("Set to Empty", () => {setcustom(false)}))
    modalcontent.appendChild(cre8ele("span", "or select cards based on the questions'..."))
    
    let div1 = cre8ele("div", "", 'inline-block', '50%')

    
    sec1CheckboxDict.forEach((checkboxSetTuple)=>{
        div1.appendChild(getFlexDivWithLabeledCheckboxList(
            checkboxSetTuple[0], checkboxSetTuple[1]))
    })

    //arr[0]
    div1.appendChild(createKeywordDiv())

    sec2CheckboxDict.forEach((checkboxSetTuple)=>{
        div1.appendChild(getFlexDivWithLabeledCheckboxList(
            checkboxSetTuple[0], checkboxSetTuple[1]))
    })


    let div2 = cre8ele("div", "", 'inline-block', '50%', 'customizedetaildiv')

    modalcontent.appendChild(div1)
    modalcontent.appendChild(div2)
    calcCustomDeckSize()

    modalcontent.appendChild(cre8ele("br"))
    modalcontent.appendChild(cre8ele("br"))
    let returna = cre8ele("a", "discard customizations and return to preset deck selections")
    returna.onclick = popupDeckCustomizer
    modalcontent.appendChild(returna)
}

function getDeckDictFromForm() {
    let deckDict = {}
    let allCheckboxDict = [...sec1CheckboxDict, ...sec2CheckboxDict]

    let curlabel = document.getElementById("customizedetaildiv").innerText.split("\n")[0]

    allCheckboxDict.forEach((checkboxSetTuple)=>{
        let label = checkboxSetTuple[0]
        document.getElementById(label+"selectedstate").click()

        let checkboxVals = checkboxSetTuple[1].map((value, i)=>document.getElementById(label+"-"+i).checked) //eg id = Categories-0

        
        if (checkboxVals.filter(x => x===true).length == checkboxVals.length)
            document.getElementById(label+"selectedval").innerHTML = "any"
        else if (checkboxVals.filter(x => x===false).length == checkboxVals.length)
            document.getElementById(label+"selectedval").innerHTML = "none"
        else {
            let inclTexts = checkboxVals.map((checkval, i) => {
                if (checkval)
                    return document.getElementById(label+"-"+i+"val").innerText.split(" -")[0]
                else
                    return null
            }).filter(x=>x!=null)
            document.getElementById(label+"selectedval").innerHTML = inclTexts.join(", ")
        }

        deckDict[label] = checkboxVals
    })
    document.getElementById("Keywordselectedstate").click()
    deckDict["Keyword"] = document.getElementById("keywordinput").value

    if (curlabel)
        document.getElementById(curlabel+"selectedstate").click()


    if (deckDict["Category"].filter(x => x===true).length == 1) {
        for (let i = 0; i < cats.length; i++) {
            if (deckDict.Category[i]) {
                document.getElementById("deckmodtext").innerHTML = (
                    deckDict.Intensity.filter(x => x===false).length > 0 ||
                    deckDict.Specificity.filter(x => x===false).length > 0 ||
                    deckDict.Details.filter(x => x===false).length > 0 ||
                    deckDict.Source.filter(x => x===false).length > 0 ||
                    deckDict.Conversatility.filter(x => x===false).length > 0 ||
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
            (deck == "custom" ? 
                ("deck=complete&question="+(cardnum)+"&complete=true") :
                ("deck="+deck+"&question="+(current+1)+"&complete="+complete))
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