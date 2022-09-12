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

// let slider = document.getElementById('customRange');
// noUiSlider.create(slider, {
//     start: [20, 80],
//     connect: true,
//     step: 1,
//     orientation: 'horizontal', // 'horizontal' or 'vertical'
//     range: {
//         'min': 0,
//         'max': 100
//     },
//     format: wNumb({
//         decimals: 0
//     })
// });