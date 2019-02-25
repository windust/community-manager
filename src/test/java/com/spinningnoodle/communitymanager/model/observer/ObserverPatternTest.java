package com.spinningnoodle.communitymanager.model.observer;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ObserverPatternTest {
    private Observable observable;
    private DummyObserver observer;

    @BeforeEach
    void setUp() {
        observable = new DummyObservable();
        observer = new DummyObserver();
    }

    @Test
    void dummyObserverUpdateWillChangeObjectState() {
        boolean observerState = observer.hasBeenCalled();

        observer.update(new DummyObservable());
        assertEquals(!observerState, observer.hasBeenCalled());
    }

    @Test
    void canAttachObserverToObservableAndUpdate() {
        observable.atachObserver(observer);

        observable.notifyObservers();
        assertTrue(observer.hasBeenCalled());
    }

    @Test
    void canDetachObserverFromObservable() {
        observable.atachObserver(observer);
        observable.detachObserver(observer);

        observable.notifyObservers();
        assertFalse(observer.hasBeenCalled());
    }
}
