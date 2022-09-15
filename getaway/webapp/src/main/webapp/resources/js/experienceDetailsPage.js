let processingBack = false;
let goBackButton = document.getElementById("goBackButton");
goBackButton.addEventListener("click", () => {
    if (processingBack) {
        return;
    }
    processingBack = true;
    goBackButton.disabled = true;
    history.back();
    goBackButton = false;
})