let processing = false;
let deleteForm = document.getElementById("deleteForm");
let deleteFormButton = document.getElementById("deleteFormButton");

deleteFormButton.addEventListener("click", () => {
    if (processing) {
        return;
    }
    processing = true;
    deleteFormButton.disabled = true;
    deleteForm.submit();
    processing = false;
})


