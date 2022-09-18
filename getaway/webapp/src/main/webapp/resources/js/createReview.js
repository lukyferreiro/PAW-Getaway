let processing = false;
let processingCancel = false;
let createReviewForm = document.getElementById("createReviewForm");
let createReviewFormButton = document.getElementById("createReviewFormButton");
let cancelFormButton = document.getElementById("cancelFormButton");

createReviewFormButton.addEventListener("click", () => {
    if (processing) {
        return;
    }
    processing = true;
    createReviewFormButton.disabled = true;
    createReviewForm.submit();
    processing = false;
})

cancelFormButton.addEventListener("click", () => {
    if (processingCancel) {
        return;
    }
    processingCancel = true;
    cancelFormButton.disabled = true;
    processingCancel = false;
})