package com.spinningnoodle.communitymanager.model.observer;
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
/**
 * Define a class which can watch for state changes in other classes
 *
 * @author Cream 4 UR Coffee
 * @version 0.1
 */
public interface Observer<T extends Observable> {

	/**
	 * Notifies this class a Observable it isa watching has an update
	 *
	 * @param observable the Observable who's state has updated
	 */
	void update(T observable);
}
