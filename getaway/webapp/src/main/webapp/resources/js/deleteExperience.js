let processing = false;
let processingCancel = false;
let deleteExperienceForm = document.getElementById("deleteExperienceForm");
let deleteExperienceFormButton = document.getElementById("deleteExperienceFormButton");
let cancelFormButton = document.getElementById("cancelFormButton");

deleteExperienceFormButton.addEventListener("click", () => {
    if (processing) {
        return;
    }
    processing = true;
    deleteExperienceFormButton.disabled = true;

    deleteExperienceForm.submit();
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


