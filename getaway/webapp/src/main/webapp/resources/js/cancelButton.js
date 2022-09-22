let processingCancel = false;
let cancelFormButton = document.getElementById("cancelFormButton");

cancelFormButton.addEventListener("click", () => {
    if (processingCancel) {
        return;
    }
    processingCancel = true;
    cancelFormButton.disabled = true;
    processingCancel = false;
})