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
document.forms["meetup_signup"].onsubmit = function (){
  var date = document.activeElement.value;
  var confirmed = confirm("Are you sure you'd like to host the meetup on " + date + "?");

  if(confirmed){
    var food = confirm("Would you like to provide food for the meetup?");

    if(food){
      var hiddenInput = document.createElement("INPUT");
      hiddenInput.style.visibility = 'hidden';
      hiddenInput.setAttribute("name", "food");
      hiddenInput.value = "true";
      this.appendChild(hiddenInput);
    }
  }


  return confirmed;
};
