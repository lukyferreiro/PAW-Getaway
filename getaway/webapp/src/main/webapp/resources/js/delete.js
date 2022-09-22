let processing = false;
// let processingCancel = false;
let deleteForm = document.getElementById("deleteForm");
let deleteFormButton = document.getElementById("deleteFormButton");
// let cancelFormButton = document.getElementById("cancelFormButton");

deleteFormButton.addEventListener("click", () => {
    if (processing) {
        return;
    }
    processing = true;
    deleteFormButton.disabled = true;
    deleteForm.submit();
    processing = false;
})

// cancelFormButton.addEventListener("click", () => {
//     if (processingCancel) {
//         return;
//     }
//     processingCancel = true;
//     cancelFormButton.disabled = true;
//     processingCancel = false;
// })


