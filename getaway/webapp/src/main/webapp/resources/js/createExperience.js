let processing = false;
let createExperienceForm = document.getElementById("createExperienceForm");
let createExperienceFormButton = document.getElementById("createExperienceFormButton");
createExperienceFormButton.addEventListener("click", () => {
    if(processing){
        return;
    }
    processing = true;
    createExperienceFormButton.disabled = true;
    createExperienceForm.submit();
    processing = false;
})


let processingCancel = false;
let cancelFormButton = document.getElementById("cancelFormButton");
cancelFormButton.addEventListener("click", () => {
    if(processingCancel){
        return;
    }
    processingCancel = true;
    cancelFormButton.disabled = true;
    history.back();
    processingCancel = false;
})

let countryInput = document.getElementById("experienceFormCountryInput");
let cityInput = document.getElementById("experienceFormCityInput");



countryInput.addEventListener("keyup", (event) => {
    if(countryInput.value.length === 0){
        cityInput.setAttribute('disabled', 'true');
    } else {
        cityInput.removeAttribute('disabled');
    }
});
