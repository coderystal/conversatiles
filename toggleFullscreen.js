
function exitFullScreen() {
    for (let controlElement of document.getElementsByClassName("controls")) {
        controlElement.style.display = ''
    }
    document.getElementById("question").classList.remove("questionFullscreen")
    document.getElementById("container").classList.remove("containerFullscreen")
    document.getElementById("screentoggleimg").src = "images/fullscreen.png"
    document.getElementById("screentoggle").style.opacity = "1"
    clearTimeout(screenToggleFadeTimeout)
}

function fullScreen() {
    for (let controlElement of document.getElementsByClassName("controls")) {
        controlElement.style.display = 'none'
    }
    document.getElementById("question").classList.add("questionFullscreen")
    document.getElementById("container").classList.add("containerFullscreen")
    document.getElementById("screentoggleimg").src = "images/exitFullscreen.png"
    function reduceScreenToggleOpacityTo0(opacity) {
        if (opacity > 0) {
            screenToggleFadeTimeout = setTimeout(function () {
                document.getElementById("screentoggle").style.opacity = opacity
                reduceScreenToggleOpacityTo0(opacity - 0.05)
            }, 50)
        }
    }
    reduceScreenToggleOpacityTo0(1)
}

function toggleScreen() {
    if (document.getElementById("screentoggleimg").src.endsWith("fullscreen.png")) {
        fullScreen()
    } else {
        exitFullScreen()
    }
}

let screenToggleFadeTimeout;