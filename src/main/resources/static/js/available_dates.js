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

for(i = 0; i < document.forms.length;i++){
  document.forms[i].onsubmit = function (){
    return confirmation();
  }
}

confirmation = function (){
  var thisButton = document.activeElement;
  var hiddenInput = document.getElementById("hiddenInput");

  var date = thisButton.value;
  var thisName = thisButton.name;
  var hiddenValue = hiddenInput.value;
  var hiddenName = hiddenInput.name;

  // alert("This date: " + date + " This name: " + thisName + hiddenName);

  if(hiddenInput.name === "unused") {
    if (date === "notHosting") {
      return true;
    }
    hiddenInput.name = "confirm";
    hiddenInput.value = date;
    openVenueConfirmModal();
    return false;
  }

  if(hiddenName == "confirm" && thisName === "meetup" && date === "notHosting") {
    resetModal();
    alert("notHosting");
    return false;
  }

  if(hiddenName == "confirm" && this.Name === "food") {
    hiddenInput.name = "meetup";
    alert("Hosting Food");

  }
  alert("Hidden name "+hiddenInput.name + " Hidden date " + hiddenInput.value);
  return true;
}

openConfirmModal = function ( confirmMessage) {
  modal = document.getElementById("modal");
  modal.classList.remove("hidden");

  var date = document.activeElement.value;
  document.getElementById("modalYes").value = date;
  document.getElementById("meetupDate").value = date;
  document.getElementById("modalDate").innerHTML = date;

  document.getElementById("modalMessage").innerHTML = confirmMessage;
}

openVenueConfirmModal = function (){
  openConfirmModal("are you sure you want to host on");
}

openFoodConfirmModal = function () {
  openConfirmModal("food", "can you provide food on");
  modal.classList.add("foodModal");
}

resetModal = function () {
  var hiddenInput = document.getElementById("hiddenInput");
  var modalYesButton = document.getElementById("modalYes");
  var modalNoButton = document.getElementById("modalNo");

  hiddenInput.name = "unused";
  // hiddenInput.value = "unused";

  // modalYesButton.name = "meetup";
  // modalNoButton.name = "meetup";

  modal.classList.remove("foodModal");
  modal.classList.add("hidden");
}
