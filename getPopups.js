

function popup() {
    let modalcontent = document.querySelector(".modal-content")
    modal.style.display = 'flex'
    modalcontent.innerHTML = "..."
    return modalcontent
}

function createTr(question, i) {
    let tr = document.createElement("tr")
    let ind = document.createElement("td")
    ind.innerHTML = i + 1
    let qust = document.createElement("td")
    qust.innerHTML = question[0]
    qust.classList.add(question[1])
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
        (viewedI) => { table.appendChild(createTr(questions[viewedI], viewedI)) }
    )
    modalcontent.innerHTML = ""
    modalcontent.appendChild(table)
}
function popupAll() {
    let modalcontent = popup()

    let table = document.createElement("table")
    let deck = document.getElementById("deckdd").value
    upperDeck = deck.charAt(0).toUpperCase() + deck.slice(1)
    table.innerHTML = "<tr><th colspan='2' class='" + deck + "'>" + (questions.length == 0 ? "No cards in deck." : upperDeck + " Deck") + "</th></tr>"
    questions.forEach(
        (question, i) => { table.appendChild(createTr(question, i)) }
    )
    modalcontent.innerHTML = ""
    modalcontent.appendChild(table)
}