let processing = false;
// let processingCancel = false;
let editReviewForm = document.getElementById("editReviewForm");
let editReviewFormButton = document.getElementById("editReviewFormButton");
// let cancelFormButton = document.getElementById("cancelFormButton");
let star1 = document.getElementById("star1");
let star2 = document.getElementById("star2");
let star3 = document.getElementById("star3");
let star4 = document.getElementById("star4");
let star5 = document.getElementById("star5");

window.addEventListener("load", ()=>{
    let score = document.getElementById("reviewFormScoreInput");
    if(score.value === "1"){
        star1.click();
    }else if(score.value === "2"){
        star1.click();
        star2.click();
    }else if(score.value === "3"){
        star1.click();
        star2.click();
        star3.click();
    }else if(score.value === "4"){
        star1.click();
        star2.click();
        star3.click();
        star4.click();
    }else if(score.value === "5"){
        star1.click();
        star2.click();
        star3.click();
        star5.click();
    }
})


editReviewFormButton.addEventListener("click", () => {
    if (processing) {
        return;
    }
    processing = true;
    editReviewFormButton.disabled = true;
    editReviewForm.submit();
    processing = false;
})

// cancelFormButton.addEventListener("click", () => {
//     if (processingCancel) {
//         return;
//     }
//     processingCancel = true;
//     cancelFormButton.disabled = true;
//     processingCancel = false;
// })
