var cur_url;
var select_category;
function onloud(){
    cur_url = window.location.pathname;
}
function onclick_catalog(event){
    event.preventDefault()
    select_category = event.target.id;
    console.log(select_category);
}

function getUrl(){
    console.log(cur_url);
}

function getUrlSafely(){
    console.log(window.location.pathname);
}
