package com.spinningnoodle.communitymanager.model.observer;

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
