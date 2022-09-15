let processing = false;
let createExperienceForm = document.getElementById("createExperienceForm");
let createExperienceFormButton = document.getElementById("createExperienceFormButton");
createExperienceFormButton.addEventListener("click", () => {
    if(processing){
        return;
    }
    processing = true;
    createExperienceFormButton.disabled = true;
    let priceInput = document.getElementById("experienceFormPriceInput");
    if(priceInput.value.length === 0){
        priceInput.value = 0;
    }
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
    processingCancel = false;
})


// async function getCities(country = ''){
//     // Default options are marked with *
//     let url=`http://localhost:8080/webapp_war_exploded/create_experience/get_cities?country=${country}`;
//
//     console.log(url)
//
//     const response = await fetch(url, {
//         method: 'GET', // *GET, POST, PUT, DELETE, etc.
//         mode: 'cors', // no-cors, *cors, same-origin
//         cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
//         credentials: 'same-origin', // include, *same-origin, omit
//         headers: {
//             'Content-Type': 'application/json'
//         }
//     });
//     let text = await response.text()
//     console.log(text)
//     return JSON.parse(text); // parses JSON response into native JavaScript objects
// }



let countryInput = document.getElementById("experienceFormCountryInput");
let cityInput = document.getElementById("experienceFormCityInput");
countryInput.addEventListener("keyup", (event) => {
    if(countryInput.value.length === 0){
        cityInput.setAttribute('disabled', 'true');
    } else {

        // getCities(countryInput.value);

        // httpCitiesRequest.open("GET","http://localhost:8080/webapp_war/create_experience/get_cities/",true);
        // httpCitiesRequest.send();

        cityInput.removeAttribute('disabled');
    }
});
