var hostingButtons = document.getElementsByClassName("request-host");

for(var i = 0; i < hostingButtons.length; i++){
  hostingButtons[i].addEventListener("click", getTokenURL)
}


function openTab(evt, category) {
  var i, tabcontent, tablinks;
  tabcontent = document.getElementsByClassName("tabcontent");
  for (i = 0; i < tabcontent.length; i++) {
    tabcontent[i].style.display = "none";
  }
  tablinks = document.getElementsByClassName("tablinks");
  for (i = 0; i < tablinks.length; i++) {
    tablinks[i].className = tablinks[i].className.replace(" active", "");
  }
  document.getElementById(category).style.display = "block";
  evt.currentTarget.className += " active";
}

function getTokenURL() {
  var ajax = new XMLHttpRequest();
  ajax.onreadystatechange = function() {
    if (this.status == 200) {
      this.parentElement.innerHTML = "<a href=\"/venue?token=something\">http://localhost:8080/venue?token=something</a>";
    }
    else{
      alert(this.status)
    }
  };
  ajax.open("POST", "/getToken", true);
  ajax.send();
}