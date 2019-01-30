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

function hider() {
    var hostingButtons = document.getElementsByClassName("request-host");
    var tokenUrls = document.getElementsByClassName("generate-token");

    for(var i = 0; i < hostingButtons.length; i++){
      hostingButtons[i].style.display = 'none';
    }

    for(var i = 0; i < tokenUrls.length; i++) {
      tokenUrls.item(i).innerHTML = "<a href=\"http://www.google.com\">Click for your token</a>";
    }
}