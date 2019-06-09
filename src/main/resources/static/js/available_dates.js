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
  if(thisButton.id == "cancel"){
    resetModal();
    return false;
  }

  var hiddenInput = document.getElementById("hiddenInput");

  var date = thisButton.value;
  var thisName = thisButton.name;
  var hiddenName = hiddenInput.name;

  var foodSponsor = document.getElementById("food"+date).value;

  if(hiddenInput.name === "unused") {
    if (date === "notHosting") {
      resetModal();
      return true;
    }
    hiddenInput.name = "confirm";
    hiddenInput.value = date;

    var taken = false;
    document.getElementsByName("meetup").forEach(function(element) {
      if(element.value == date && element.getAttribute("data_food")==="taken"){
        taken = true;
      }
    });
    if(taken || thisButton.getAttribute("data_food") === "taken"){
      document.getElementById("modalYes").classList.add("hidden");
      document.getElementById("foodMessage").classList.add("hidden");
      document.getElementById("foodDecline").classList.add("hidden");
      document.getElementById("foodProvided").classList.remove("hidden");
      document.getElementById("providingFoodSponsor").innerText = foodSponsor;
      document.getElementById("modalJustYes").value="";
    }

    openConfirmModal();
    return false;
  }
//can the first two arguments be removed here?
  //Nope, need hidden name to determine what the no button does right now.
  if(hiddenName == "confirm" && thisName === "meetup" && date === "notHosting") {
    resetModal();
    return false;
  }

  return true;
}

openConfirmModal = function ( ) {
  mask = document.getElementById("mask");
  mask.classList.add("mask");

  modal = document.getElementById("modal");
  modal.classList.remove("hidden");

  var date = document.activeElement.value;
  var splitDate = date.split("-");
  var formattedDate = splitDate[1] + "-" + splitDate[2] + "-" + splitDate[0];

  document.getElementById("meetupDate").value = date;
  document.getElementById("modalDate").innerHTML = formattedDate;

}

resetModal = function () {
  var hiddenInput = document.getElementById("hiddenInput");
  hiddenInput.name = "unused";
  modal.classList.add("hidden");
  document.getElementById("modalYes").classList.remove("hidden");
  document.getElementById("foodMessage").classList.remove("hidden");
  document.getElementById("foodDecline").classList.remove("hidden");
  document.getElementById("foodProvided").classList.add("hidden");
  // document.getElementById("modalJustYes").value="";
  mask = document.getElementById("mask");
  mask.classList.remove("mask");
}