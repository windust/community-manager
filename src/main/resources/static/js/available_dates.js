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
  var hiddenValue = hiddenInput.value;

  if(hiddenInput.name === "unused") {
    if (date === "notHosting" && hiddenValue === "unused") {
      return true;
    }
    hiddenInput.name = "confirm";
    openVenueConfirmModal();
    return false;
  }

  if(hiddenInput.name == "confirm") {
    if (date == "notHosting") {
      resetModal();
      return false;
    }
    openFoodConfirmModal();
    hiddenInput.name = "meetup";
    hiddenInput.value = date;
    return false;
  }

  if(hiddenValue == "meetup") {
      return true;
  }
}

openConfirmModal = function (confirmType, confirmMessage) {
  modal = document.getElementById("modal");
  modal.classList.remove("hidden");

  var date = document.activeElement.value;
  document.getElementById("modalYes").value = date;
  document.getElementById("modalDate").innerHTML = date;

  document.getElementById("modalYes").name = confirmType;
  document.getElementById("modalNo").name = confirmType;

  document.getElementById("modalMessage").innerHTML = confirmMessage;
}

openVenueConfirmModal = function (){
  openConfirmModal("meetup", "are you sure you want to host on");
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
  hiddenInput.value = "unused";

  modalYesButton.name = "meetup";
  modalNoButton.name = "meetup";

  modal.classList.remove("foodModal");
  modal.classList.add("hidden");
}
