let processing = false;
let star = document.getElementById("star");
let favExperienceForm = document.getElementById("favExperienceForm");
let favExperienceFormButton = document.getElementById("favExperienceFormButton");

window.addEventListener("load", ()=>{
    let fav = document.getElementById("setFav");
    if(fav.value === "true"){
        star.click();
    }
})

star.addEventListener("click", () => {
    let fav = document.getElementById("setFav");
    console.log(fav.value);
    if(fav.value === "true"){
        fav.value = "false"
    }else{
        fav.value = "true"
    }
    console.log(fav.value);
})

favExperienceFormButton.addEventListener("click", () => {
    if (processing) {
        return;
    }
    processing = true;
    favExperienceFormButton.disabled = true;
    favExperienceForm.submit();
    processing = false;
})
