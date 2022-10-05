window.addEventListener("load", () => {
    let x = document.getElementById("snackbar");
    setTimeout(function(){ x.className = x.className.replace("show", ""); }, 3000);
    x.className = "show";
})