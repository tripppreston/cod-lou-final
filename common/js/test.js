var xhr = new XMLHttpRequest();
xhr.onreadystatechange = function () {
    if(xhr.readyState === 4){
        console.log(xhr.responseText);
    }

};

xhr.open('GET', 'js/harvested.json');
xhr.send();