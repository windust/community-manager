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
import java.util.ArrayList;
import java.util.List;

/**
 * defines an abstract interface with the callback operation Observer objects use to notify observers.
 *
 * @author Cream 4 UR Coffee
 * @version 0.1
 */
public abstract class Observable {
	private List<Observer> observers = new ArrayList<>();

	/**
	 * Attach a observer to this object
	 *
	 * @param observer A class which listens to updates in this class
	 */
	public void atachObserver(Observer observer) {
		observers.add(observer);
	}

	/**
	 * Detach a observer to this object
	 *
	 * @param observer A class which listens to updates in this class
	 */
	public void detachObserver(Observer observer) {
		observers.remove(observer);
	}

	/**
	 * Call update method on all observers to let them know a change in state has been made.
	 */
	protected void notifyObservers() {
		for(Observer observer : observers) {
			observer.update(this);
		}
	}
}
