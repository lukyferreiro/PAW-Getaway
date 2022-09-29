let processing = false;
let submitForm = document.getElementById("submitForm");
let submitFormButton = document.getElementById("submitFormButton");


submitFormButton.addEventListener("click", () => {
    if (processing) {
        return;
    }
    processing = true;
    submitFormButton.disabled = true;
    submitForm.submit();
    processing = false;
})
