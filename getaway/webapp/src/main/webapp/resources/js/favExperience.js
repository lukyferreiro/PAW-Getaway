let favExperienceForm = document.getElementById("favExperienceForm");
let processing = false;
let submitForm = document.getElementById("submitForm");

submitForm.addEventListener("click", () => {
    if (processing) {
        return;
    }
    processing = true;
    favExperienceForm.submit();
    processing = false;
})
