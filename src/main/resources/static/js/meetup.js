/**
 *  LICENSE
 *  Copyright (c) 2019 Cream 4 UR Coffee: Kevan Barter, Melanie Felton, Quentin Guenther, Jhakon Pappoe, and Tyler Roemer.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at:
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 *
 *  END OF LICENSE INFORMATION
 */
var hostingButtons = document.querySelectorAll("[rel='request-host']");

for(var i = 0; i < hostingButtons.length; i++){
  const thisButton = hostingButtons[i];
  hostingButtons[i].addEventListener("click", function() {getTokenURL(thisButton)});
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

function getTokenURL(element) {
  var ajax = new XMLHttpRequest();
  //TODO find out why 0 is returned before actual content
  ajax.onreadystatechange = function() {
    if (this.status == 200) {
      element.parentElement.innerHTML = "<a href=\"/venue?token=Expedia-valid\">http://localhost:8080/venue?token=" + this.responseText + "Expedia-valid</a>";
    }
    else{
      alert(this.status)
    }
  };
  ajax.open("POST", "/getToken", true);
  ajax.send();
}