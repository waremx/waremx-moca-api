package com.waremx.common.mox.listeners;

public interface Subject {
    /**
     * Registers a listener to receive notifications for a specific event type.
     *
     * @param listener  the listener to subscribe
     *
     * @since 1.0
     */
    void subscribe(String event, Observe listener);

    /**
     * Unregisters a listener from a specific event type.
     *
     * @param event the name or identifier of the event
     *
     * @since 1.0
     */
    void unsubscribe(String event);

    /**
     * Emits an event to all registered listeners for the given event type.
     *
     * @param obj       The payload to deliver
     * @param <T>       The type of the payload
     */
    <T> void emit(String event, T obj);
}