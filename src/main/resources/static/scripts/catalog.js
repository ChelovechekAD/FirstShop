var cur_url;
var select_category;
function onload_catalog(){
    cur_url = window.location.pathname;
    let heightButton = document.getElementsByClassName("list-item-body-footer-count-minus")[0].offsetHeight;
    document.getElementsByClassName("list-item-body-footer-count-minus")[0].style.width = heightButton + "px";
    document.getElementsByClassName("list-item-body-footer-count-plus")[0].style.width = heightButton + "px";
    let array = document.getElementsByClassName("list-item-body-footer-count-plus")
    Array.from(array).forEach((el) => el.style.width = heightButton + "px");
    array = document.getElementsByClassName("list-item-body-footer-count-minus")
    Array.from(array).forEach((el) => el.style.width = heightButton + "px");

}
window.addEventListener( 'resize', onload_catalog, false );
function onclick_catalog(e){
    select_category = e.id;
    console.log(select_category);
    window.location.href = cur_url + "/" + select_category;
    console.log(cur_url + "/" + select_category)
}

function onclick_plus_minus(e){
    let count = e.parentElement.children[1].children[0].value;
    if (e.className === "list-item-body-footer-count-plus"){
        e.parentElement.children[1].children[0].value = Number(count) + 1;
    }else if (e.className === "list-item-body-footer-count-minus"){
        if (count > 1){
            e.parentElement.children[1].children[0].value = Number(count) - 1;
        }
    }

}

function redirect_to_full_description(type, id){
    window.location.href = cur_url + "/" + id;
}
