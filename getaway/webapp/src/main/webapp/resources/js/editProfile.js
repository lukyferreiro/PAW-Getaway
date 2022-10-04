let editProfileForm = document.getElementById("editProfileForm")
let processing = false;
let editProfileFormButton = document.getElementById("editProfileFormButton");
editProfileFormButton.addEventListener("click", () => {
    if (processing) {
        return;
    }
    processing = true;
    editProfileFormButton.disabled = true;
    editProfileForm.submit();
    processing = false;
})