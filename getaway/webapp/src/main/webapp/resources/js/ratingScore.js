let star1 = document.getElementById("star1");
let star2 = document.getElementById("star2");
let star3 = document.getElementById("star3");
let star4 = document.getElementById("star4");
let star5 = document.getElementById("star5");

star1.addEventListener("click", () => {
    let score = document.getElementById("scoreInput");
    score.value = "1";
})
star2.addEventListener("click", () => {
    let score = document.getElementById("scoreInput");
    score.value = "2";
})
star3.addEventListener("click", () => {
    let score = document.getElementById("scoreInput");
    score.value = "3";
})
star4.addEventListener("click", () => {
    let score = document.getElementById("scoreInput");
    score.value = "4";
})
star5.addEventListener("click", () => {
    let score = document.getElementById("scoreInput");
    score.value = "5";
})