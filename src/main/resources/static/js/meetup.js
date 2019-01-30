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

  var hidden = false;
  function hider(){
      !hidden
      if(hidden){
          document.getElementById("request-host").style.visibility("hidden");
      }else{
        document.getElementById("request-host").style.visibility("visible");
      }
  }