let processing = false;
let createReviewForm = document.getElementById("createReviewForm");
let createReviewFormButton = document.getElementById("createReviewFormButton");

createReviewFormButton.addEventListener("click", () => {
    if (processing) {
        return;
    }
    processing = true;
    createReviewFormButton.disabled = true;
    createReviewForm.submit();
    processing = false;
})
