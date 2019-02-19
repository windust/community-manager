package com.spinningnoodle.communitymanager.model.observer;

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
