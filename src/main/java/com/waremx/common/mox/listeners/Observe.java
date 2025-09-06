package com.waremx.common.mox.listeners;

public interface Observe<U> {
    /**
     * Called when the subject emits an event the listener is subscribed to.
     *
     * @param event     the event payload
     */
    void update(U event);

    U get();
}