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
var foodButtons = document.querySelectorAll("[rel='request-food']");

for(var i = 0; i < hostingButtons.length; i++){
  const thisButton = hostingButtons[i];
  hostingButtons[i].addEventListener("click", function() {getVenueTokenURL(thisButton)});
}

for(var i = 0; i < foodButtons.length; i++){
  const thisButton = foodButtons[i];
  foodButtons[i].addEventListener("click", function() {getFoodTokenURL(thisButton)});
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

function getVenueTokenURL(element) {
  var xhr = new XMLHttpRequest();
  var primaryKey = element.getAttribute('data-venueIndentifier');
  var requestedDate = document.getElementById("date").innerHTML;

  xhr.open('POST', "/getVenueToken?venueKey=" + primaryKey + "&" + "date=" + requestedDate, true);

  xhr.onload = function() {
    if(this.status === 200) {
      element.parentElement.innerHTML =
          "<a href=\"/venue?token=" + this.responseText +
          "\">http://localhost:8080/venue?token=" + this.responseText + "</a>";
    } else {
      alert('Error fetching token URL. STATUS: ' + this.status);
    }
  };

  xhr.send("venueKey=" + primaryKey + "&" + "date=" + requestedDate);
}

function getFoodTokenURL(element) {
  var xhr = new XMLHttpRequest();
  var primaryKey = element.getAttribute('data-foodIndentifier');
  var requestedDate = document.getElementById("date").innerHTML;

  xhr.open('POST', "/getFoodToken?foodKey=" + primaryKey + "&" + "date=" + requestedDate, true);

  xhr.onload = function() {
    if(this.status === 200) {
      element.parentElement.innerHTML =
          "<a href=\"/food?token=" + this.responseText +
          "\">http://localhost:8080/food?token=" + this.responseText + "</a>";
    } else {
      alert('Error fetching token URL. STATUS: ' + this.status);
    }
  };

  xhr.send("foodKey=" + primaryKey + "&" + "date=" + requestedDate);
}