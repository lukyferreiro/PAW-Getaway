let processing = false;
let processingCancel = false;
let editExperienceForm = document.getElementById("editExperienceForm");
let editExperienceFormButton = document.getElementById("editExperienceFormButton");
let cancelFormButton = document.getElementById("cancelFormButton");
let countryInput = document.getElementById("experienceFormCountryInput");
let cityInput = document.getElementById("experienceFormCityInput");

function validateCityIfThereIsACountry(countryInput, cityInput) {
    if (countryInput.value.length === 0) {
        cityInput.setAttribute('disabled', 'true');
        cityInput.value = "";
    } else {
        cityInput.removeAttribute('disabled');
    }
}

window.addEventListener("change", () => {
    validateCityIfThereIsACountry(countryInput, cityInput);
});

window.addEventListener("load", () => {
    validateCityIfThereIsACountry(countryInput, cityInput);
});

countryInput.addEventListener("keyup", () => {
    validateCityIfThereIsACountry(countryInput, cityInput);
});

editExperienceFormButton.addEventListener("click", () => {
    if (processing) {
        return;
    }
    processing = true;
    editExperienceFormButton.disabled = true;
    //If URL don't start with HTTP protocol, add it at the beginning
    let urlInput = document.getElementById("experienceFormUrlInput");
    if (urlInput.value.length !== 0 && !urlInput.value.startsWith("http://") && !urlInput.value.startsWith("https://")) {
        urlInput.value = "http://" + urlInput.value;
    }
    editExperienceForm.submit();
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



countryInput.addEventListener("keyup", (event) => {
    if (countryInput.value.length === 0) {
        cityInput.setAttribute('disabled', 'true');
    } else {

        cityInput.removeAttribute('disabled');
    }
});
