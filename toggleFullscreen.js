
function exitFullScreen() {
    if (document.querySelector(".coffee") != null)
        document.querySelector(".coffee").style.display = 'none'
    for (let controlElement of document.getElementsByClassName("controls")) {
        controlElement.style.display = ''
    }
    document.getElementById("question").style.height = ""
    document.getElementById("question").style.margin = ""
    document.getElementById("container").style.alignContent = ""
    document.getElementById("container").style.height = ""
    document.getElementById("container").style.maxHeight = ""
    document.getElementById("screentoggleimg").src = "fullscreen.png"
    document.getElementById("screentoggle").style.opacity = "0.5"

}
function fullScreen() {
    for (let controlElement of document.getElementsByClassName("controls")) {
        controlElement.style.display = 'none'
    }
    document.getElementById("question").style.height = "70%"
    document.getElementById("question").style.margin = "auto"
    document.getElementById("container").style.alignContent = "center"
    document.getElementById("container").style.height = "100%"
    document.getElementById("container").style.maxHeight = "100%"
    document.getElementById("screentoggleimg").src = "exitFullscreen.png"
    function reduceScreenToggleOpacityTo0(opacity) {
        if (opacity > 0) {
            setTimeout(function () {
                document.getElementById("screentoggle").style.opacity = opacity
                reduceScreenToggleOpacityTo0(opacity - 0.05)
            }, 50)
        }
    }
    reduceScreenToggleOpacityTo0(0.5)
}

function toggleScreen() {
    if (document.getElementById("screentoggleimg").src.endsWith("fullscreen.png")) {
        fullScreen()
    } else {
        exitFullScreen()
    }
}