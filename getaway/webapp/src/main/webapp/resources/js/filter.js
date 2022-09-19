let processing = false;
let cityFilterForm = document.getElementById("cityFilterForm");
let cityFilterFormButton = document.getElementById("cityFilterFormButton");
cityFilterFormButton.addEventListener("click", () => {
    if (processing) {
        return;
    }
    processing = true;
    cityFilterFormButton.disabled = true;
    cityFilterForm.submit();
    processing = false;
})

