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

let countryInput = document.getElementById("experienceFormCountryInput");
let cityInput = document.getElementById("experienceFormCityInput");
countryInput.addEventListener("keyup", (event) => {
    if(countryInput.value.length === 0){
        cityInput.setAttribute('disabled', 'true');
    } else {

        // const httpCitiesRequest = new XMLHttpRequest();
        //
        // httpCitiesRequest.onreadystatechange = function(){
        //     if(httpCitiesRequest.readyState === 4 && httpCitiesRequest.status === 200){
        //         console.log(JSON.parse(httpCitiesRequest.response));
        //     }
        // };
        //
        // httpCitiesRequest.open("GET","http://localhost:8080/webapp_war/create_experience/get_cities",true);
        // httpCitiesRequest.send();

        cityInput.removeAttribute('disabled');
    }
});
