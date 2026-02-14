

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

function getHtmlForDeckCustomizerAdvanced() {
    let html = "<b>Customize Deck</b><br><br>" +

    //arr[1]
    "Categories:"
    for (let i in cats) {
        html += "<input type='checkbox'> " + cats[i]
    }
    html += "<br><button>select all</button><button>deselect all</button>"

    //arr[2]
    html += "<br><br>Specificity: <input type='checkbox'> purposefully vague <input type='checkbox'> generally unambiguous<br>"

    //arr[0]
    html += "<br><br>contains text: <input>"

    //arr[5]
    html += "<br><br>Details: <input type='checkbox'> includes comments/suggestions<input type='checkbox'> question only<br>"

    //arr[3]
    html += "<br><br>Sources:"
    unqSrcs.forEach(src => {
        html += "<input type='checkbox'> " + src
    })
    html += "<br><button>select all</button><button>deselect all</button>"

    //arr[4]
    html += "<br><br>Reviewed: <input type='checkbox'> edited by coderystal<input type='checkbox'> unedited<br>"

    html += "<br><br>Each question includes: 1) question, 2) category, 3) vague status, 4) source, 5) edit status, 6) comments"

    return html
}

async function send(event) {
    event.stopPropagation();
    const shareData = {
        title: "Conversatiles",
        text: "but think about this...",
        url: "https://coderystal.github.io/conversatiles/?deck="+deck+"&question="+(current+1)+"&complete="+complete
    };

    // Share must be triggered by "user activation"
    try {
        await navigator.share(shareData);
        console.log("MDN shared successfully");
    } catch (err) {
        let modalcontent = popup()
        modalcontent.innerHTML = "<b>Send this question to a friend!</b>"+
            "<button id='copyToClipboardButton' onclick='copyToClipboard(\""+shareData.url+"\")' style='margin: 10px;'>Copy</button>"+
            "<br><div style='border: 1px solid black'>"+shareData.url+"</div>"
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