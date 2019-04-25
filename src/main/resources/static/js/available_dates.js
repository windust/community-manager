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
// document.forms["meetup_signup","meetup_requested","meetup_confirm"].onsubmit = function (){
document.forms["meetup_signup"].onsubmit = function (){
  return confirmation();
}

document.forms["meetup_requested"].onsubmit = function (){
  return confirmation();
}

document.forms["meetup_confirm"].onsubmit = function (){
  return confirmation();
}

confirmation = function (){
  var thisButton = document.activeElement;
  var hiddenInput = document.getElementById("hiddenInput");
  var modalYesButton = document.getElementById("modalYes");
  var modalNoButton = document.getElementById("modalNo");

  var date = thisButton.value;
  var hiddenValue = hiddenInput.value;

  if(hiddenInput.name === "unused") {
    if (date === "notHosting" && hiddenValue === "unused") {
      return true;
    }

    hiddenInput.value = "confirm";
    hiddenInput.name = "confirm";
    openVenueConfirmModal(thisButton);
    return false;
  }

  if(hiddenInput.name == "confirm") {
    if (date == "notHosting") {
      closeModal(thisButton);
      resetModal();
      return false;
    }

    openFoodConfirmModal(thisButton);
    hiddenInput.name = "meetup";
    hiddenInput.value = date;
    return false;
  }

  if(hiddenValue == "meetup") {

      closeModal(thisButton);
      return true;

  }
}


openVenueConfirmModal = function (element){
  modal = document.getElementById("modal");
  modal.classList.remove("hidden");
  var date = document.activeElement.value;

  document.getElementById("modalYes").name = "meetup";
  document.getElementById("modalNo").name = "meetup";
  document.getElementById("modalYes").value = date;

  document.getElementById("modalDate").innerHTML = date;
  document.getElementById("modalMessage").innerHTML = "are you sure you want to host on";
}

openFoodConfirmModal = function (element) {
  modal = document.getElementById("modal");
  modal.classList.remove("hidden");
  modal.classList.add("foodModal");
  var date = document.activeElement.value;

  document.getElementById("modalYes").name = "food";
  document.getElementById("modalNo").name = "food";
  document.getElementById("modalYes").value = date;

  document.getElementById("modalDate").innerHTML = date;
  document.getElementById("modalMessage").innerHTML = "can you provide food on";
}

confirmFood = function (element, foodDecision) {
  var date = document.activeElement.value;
  window.location.href = "/venueSignUp?meetup="+date+"&food=" + foodDecision;
}

resetModal = function () {
  var hiddenInput = document.getElementById("hiddenInput");
  var modalYesButton = document.getElementById("modalYes");
  var modalNoButton = document.getElementById("modalNo");

  hiddenInput.name = "unused";
  hiddenInput.value = "unused";

  modalYesButton.name = "meetup";
  modalNoButton.name = "meetup";
}

closeModal = function (element) {
  modal = document.getElementById("modal");
  modal.classList.add("hidden");

}
