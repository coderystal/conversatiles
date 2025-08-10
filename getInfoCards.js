function showTitle() {
    document.getElementById("question").innerHTML = "<button class='coffee' style='display: none;' onclick='event.stopPropagation()'><a href='https://buymeacoffee.com/jeyc35gujd' target='_blank' style='text-decoration: none;'>☕ Buy me a coffee</a></button>" +
        "<div class='cardq' style='width:100%;line-height:100%;margin:auto;font-size:5vh'><b>" +
        "Conversatiles</b><div style='font-size:2.5vh'>by coderystal</div>" +
        "</div>" +
        "<span class='cardatt'>" + document.getElementById("deckdd").value + " deck<span>"
    document.getElementById("question").style.color = "black"
}

function showAbout() {
    document.getElementById("question").innerHTML = "<div class='cardq' style='width:100%;margin:auto;font-size:max(1.7vh, 14px);'>" +
        "<b style='font-size:max(2vh, 18px);'>Conversatiles</b>" + "<br>" +
        "New insight, every click" + "<br>" +
        "<p style='text-align: left;margin:0;'>Uncover deeper understandings, one question at a time. Never get stuck, with 600+ thought-provoking prompts across different topics to think on and talk through.</p> " + "<br>" +
        "<b>Know what you need?</b> Find your way in with our neatly browsable library." + "<br>" +
        "<b>Ready for anything?</b> Jump right in with our inviting, no-nonsense cards." + "<br><br>" +
        "<b>A note from coderystal</b>" +
        "<p style='text-align: left;margin:0;'>For the task of getting to know a person, even yourself, I've found that pre-written questions, presented as an activity, make things less intimidating while keeping them intentional. I hope you enjoy the abundance and quality of these questions I've gathered. Good luck!" + "<br>" +
        "I've got more features in mind... Find me everywhere @coderystal for updates and new projects!" + "</p>" +
        "</div>"
    document.getElementById("question").style.color = "black"
}
function showCoderystal() {
    document.getElementById("question").innerHTML = "<div class='cardq' style='width:100%;margin:auto;font-size:max(1.7vh, 14px);'>" +
        "<b>Find me everywhere @coderystal for updates and new projects!</b>" + "<br><br>" +
        "<p style='text-align: left;margin:0;'>I love making stuff for people to use - and this is my first coding project I'm calling finished! I'm so excited you're reading this, especially if you didn't hear about it from me. I hope you enjoy Conversatiles." + "</p><br>" +
        "<b>Want to create a site of your own?</b><br>" +
        "<a onclick='event.stopPropagation();' href='https://github.com/coderystal/questiondeck/tree/main' target='_blank'>GitHub</a> (source code and documentation)" +
        "<p style='text-align: left;margin:0;'>I aim to share projects that serve as demonstrations and references, of how I bring ideas to life with code." + "</p><br>" +
        "<b>Want to see the data behind this site?</b><br>" +
        "<a onclick='event.stopPropagation();' href='https://docs.google.com/spreadsheets/d/1vhKC0KtDO12IpI9UygmImvsvWaJ1RaYuBskVht1--8w/edit?usp=sharing' target='_blank'>Google Sheets</a> (workspace for this project)" +
        "<p style='text-align: left;margin:0;'>The contents of this site were organized and decided upon here, because I prefer formulas to paragraphs.</p>" +
        "</div>"
    document.getElementById("question").style.color = "black"
}

function showInstructions() {
    document.getElementById("question").innerHTML = "<div class='cardq' style='width:100%;margin:auto;font-size:max(1.7vh, 15px);text-align: left;'>" +
        "<b>Choose your deck!</b> Browse the ten sub-categories we've divided our questions into.<br>" +
        "<b>Pick a card!</b> Enter a number or let us find a new question.<br>" +
        "<b>Let us keep track!</b> Review your history, or reset to put all cards back into play.<br>" +
        "<b>Skim them all!</b> See the full list of everything in the deck.<br>" +
        "If you're on your computer, each button can be selected using the keyboard, click any key to see." +
        "<br><br>" +
        "When you can, make the most of each question - explain, qualify, and don't hold back.<br>" +
        "<b style='color:red'>Some cards are purposely vague - see how you react, share what comes to mind!</b><br>" +
        "To relax a bit, try answering with just one word!" +
        "<br><br>" +
        "Be kind and have fun!"
    "</div>"
    document.getElementById("question").style.color = "black"
}