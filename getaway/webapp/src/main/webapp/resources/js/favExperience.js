let favTrue = document.getElementById("favTrue");
let favFalse = document.getElementById("favFalse");
let favExperienceForm = document.getElementById("favExperienceForm");
let processing = false;

favTrue.addEventListener("click", () => {
    let favExp = document.getElementById("setFav");
    favExp.value = false;

    submit();
})

favFalse.addEventListener("click", () => {
    let favExp = document.getElementById("setFav");
    favExp.value = true;

    submit();
})


function submit() {
    if (processing) {
        return;
    }
    processing = true;
    favExperienceForm.submit();
    processing = false;
}
