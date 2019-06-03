package com.spinningnoodle.communitymanager.exceptions;
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
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
/**
 * Invalid user exception is thrown when a user that
 * isn't allowed access to the website or if they try
 * to go to the admin page when not logged in.
 *
 * @author Cream 4 UR Coffee
 * @version 0.1
 */
@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Invalid token or not logged in")
public class InvalidUserException extends Exception {

    /**
     * Constructor with a call to the super constructor
     * that passes the a String to super for the reason
     * why the exception was thrown.
     */
    public InvalidUserException(){
        super("Please login or provide a valid token");
    }
}
