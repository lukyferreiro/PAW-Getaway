let processing = false;
let cityFilterForm = document.getElementById("cityFilterForm");
let cityFilterFormButton = document.getElementById("cityFilterFormButton");
cityFilterFormButton.addEventListener("click", () => {
    if(processing){
        return;
    }
    processing = true;
    cityFilterFormButton.disabled = true;
    // let priceInput = document.getElementById("experienceFormPriceInput");
    // if(priceInput.value.length === 0){
    //     priceInput.value = 0;
    // }
    cityFilterForm.submit();
    processing = false;
})
