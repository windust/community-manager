package com.spinningnoodle.communitymanager.model.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * defines an abstract interface with the callback operation IObserver objects use to notify observers.
 *
 * @author Cream 4 UR Coffee
 * @version 0.1
 */
public abstract class Observable {
	private List<IObserver> observers = new ArrayList<>();

	/**
	 * Attach a observer to this object
	 *
	 * @param observer A class which listens to updates in this class
	 */
	public void atachObserver(IObserver observer) {
		observers.add(observer);
	}

	/**
	 * Detach a observer to this object
	 *
	 * @param observer A class which listens to updates in this class
	 */
	public void detachObserver(IObserver observer) {
		observers.remove(observer);
	}

	/**
	 * Call update method on all observers to let them know a change in state has been made.
	 */
	protected void notifyObservers() {
		for(IObserver observer : observers) {
			observer.update(this);
		}
	}
}
