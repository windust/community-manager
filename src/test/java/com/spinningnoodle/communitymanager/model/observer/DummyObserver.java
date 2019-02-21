package com.spinningnoodle.communitymanager.model.observer;

/**
 * Class created for observer pattern tests.
 *
 * @author Cream 4 UR Coffee
 * @version 0.1
 */
public class DummyObserver implements Observer<DummyObservable> {
    private boolean hasBeenCalled = false;

    boolean hasBeenCalled() {
        return hasBeenCalled;
    }

    @Override
    public void update(DummyObservable observable) {
        hasBeenCalled = true;
    }

    @Override
    public String toString() {
        return "DummyObserver{" +
            "hasBeenCalled=" + hasBeenCalled +
            '}';
    }
}
