window.onload = function (){

    var httpCitiesRequest = new XMLHttpRequest();

    httpCitiesRequest.onreadystatechange = function(){
        if(httpCitiesRequest.readyState == 4 && httpCitiesRequest.status == 200){
            console.log(JSON.parse(httpCitiesRequest.response));
        }
    };
  httpCitiesRequest.open("GET","aca va el path de el json generado por la API(CitiesController)",true);
  httpCitiesRequest.send();

};