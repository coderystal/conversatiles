
function exitFullScreen() {
    for (let controlElement of document.getElementsByClassName("controls")) {
        controlElement.style.display = ''
    }
    document.getElementById("question").classList.remove("questionFullscreen")
    document.getElementById("container").classList.remove("containerFullscreen")
    document.getElementById("screentoggleimg").src = "fullscreen.png"
    document.getElementById("screentoggle").style.opacity = "0.5"
    clearTimeout(screenToggleFadeTimeout)
}

function fullScreen() {
    for (let controlElement of document.getElementsByClassName("controls")) {
        controlElement.style.display = 'none'
    }
    document.getElementById("question").classList.add("questionFullscreen")
    document.getElementById("container").classList.add("containerFullscreen")
    document.getElementById("screentoggleimg").src = "exitFullscreen.png"
    function reduceScreenToggleOpacityTo0(opacity) {
        if (opacity > 0) {
            screenToggleFadeTimeout = setTimeout(function () {
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

let screenToggleFadeTimeout;