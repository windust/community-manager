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
// document.forms["meetup_signup"].onsubmit = function (){
//   var date = document.activeElement.value;
//   var confirmed = confirm("Are you sure you'd like to host the meetup on " + date + "?");
//
//   if(confirmed){
//     var food = confirm("Would you like to provide food for the meetup?");
//
//     if(food){
//       var hiddenInput = document.createElement("INPUT");
//       hiddenInput.style.visibility = 'hidden';
//       hiddenInput.setAttribute("name", "food");
//       hiddenInput.value = "true";
//       this.appendChild(hiddenInput);
//     }
//   }
//
//
//   return confirmed;
// };

var confirmVenueButtons = document.querySelectorAll("[rel='venueConfirm']");

for(var i = 0; i < confirmVenueButtons.length; i++){
  thisButton = confirmVenueButtons[i];
  confirmVenueButtons[i].addEventListener("click", function() {openVenueConfirmModal(thisButton)});
}

var confirmFoodButtons = document.querySelectorAll("[rel='foodConfirm']");

for(var i = 0; i < confirmFoodButtons.length; i++){
  thisButton = confirmFoodButtons[i];
  confirmFoodButtons[i].addEventListener("click", function() {openFoodConfirmModal(thisButton)});
}

var declineButtons = document.querySelectorAll("[rel='declineConfirm']");

for(var i = 0; i < declineButtons.length; i++){
  thisButton = declineButtons[i];
  declineButtons[i].addEventListener("click", function() {closeModal(thisButton)});
}

openVenueConfirmModal = function (element){
  modal = document.getElementById("modal");
  modal.classList.remove("hidden");
  var date = document.activeElement.value;

  document.getElementById("modalYes").value = date;

  document.getElementById("modalDate").innerHTML = date;
  document.getElementById("modalMessage").innerHTML = "are you sure you want to host on";
}

openFoodConfirmModal = function (element) {
  modal = document.getElementById("modal");
  modal.classList.remove("hidden");
  var date = document.activeElement.value;

  document.getElementById("modalDate").innerHTML = date;
  document.getElementById("modalMessage").innerHTML = "can you provide food on";
  document.getElementById("modalYes").addEventListener("click", function() {confirmFood(thisButton,true)});
  document.getElementById("modalNo").addEventListener("click", function() {confirmFood(thisButton,false)});
  document.getElementById("modalNo").value = date;

}

confirmFood = function (element, foodDecision) {
  var date = document.activeElement.value;
  window.location.href = "/venueSignUp?meetup="+date+"&food=" + foodDecision;
}

closeModal = function (element) {
  modal = document.getElementById("modal");
  modal.classList.add("hidden");

}
