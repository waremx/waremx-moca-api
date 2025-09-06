package com.waremx.common.mox.uni;


import com.waremx.common.mox.core.Prop;
import com.waremx.common.mox.listeners.Subject;
import com.waremx.common.mox.listeners.Observe;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The {@code Context} class is a generic container for storing properties
 * ({@link Prop}), tracking events, and propagating notifications to
 * registered listeners.
 *
 * It can be used to share typed values across components,
 * subscribe/unsubscribe listeners to events, and emit payloads
 * that are stored as the latest result.
 *
 * @param <T> The type of result values emitted and tracked by the context
 * @param <E> The type used to represent errors within the context
 *
 * @author
 * @since 1.0
 */
public class Context<T, E> implements Subject {
    private final Map<String, Prop<?>> props = new HashMap<>();
    private final Map<String, Observe> listeners = new HashMap<>();
    private E err;

    /**
     * Returns the complete {@code Map} of all props stored in the context. This
     * data structure contains all the properties registered by all listeners.There are
     * no access restrictions and they are visible to everyone.
     *
     * @return {@code Map<String, Prop<?>>} The properties of the context
     *
     * @since 1.0
     */
    public final Map<String, Prop<?>> getProps() {
        return this.props;
    }

    /**
     * Stores a typed value in the context under the given key. This method internally
     * creates a {@link Prop} instance with the {@code bind()} static method.
     *
     * @param   key     The key under which the value is stored
     * @param   value   The value to store
     *
     * @since 1.0
     */
    public final void set(String key, T value) {
        Prop<T> prop = Prop.bind(key, value);
        this.props.put(key, prop);
    }

    /**
     * Stores one or more {@link Prop} instances in the context. This method uses the
     * static {@code bind()} method of the {@link Prop} class to create a new instance.
     *
     * @param props  One or more properties to store
     *
     * @since 1.0
     */
    public final void set(Prop<?> ...props) {
        for (Prop<?> prop : props) {
            this.props.put(prop.getId(), prop);
        }
    }

    /**
     * Retrieves a property value by its key, cast to the expected type.
     * Throws {@link ClassCastException} if the actual type of the stored value
     * does not match the type stored in the corresponding {@link Prop}.
     *
     * @param   name    The key of the property to retrieve
     * @param   <U>     The expected type of the value
     * @return  {@link Optional}
     *          Containing the value if present and type matches, or empty otherwise
     *
     * @throws  ClassCastException
     *          If the type of the stored value does not match
     *
     * @since 1.0
     */
    @SuppressWarnings("unchecked")
    public final <U> Optional<U> get(String name) {
        Prop<?> prop = props.get(name);

        if (prop == null) {
            return Optional.empty();
        }

        return Optional.ofNullable((U) prop.getValue());
    }

    public final E err(E err) {
        this.err = err;
        return this.err;
    }

    public final E err() {
        return this.err;
    }

    /**
     * Subscribes a listener to a specific event.
     * Replaces the listener if the event was already subscribed.
     *
     * @param event     The event name to subscribe to
     * @param listener  The observer that will receive updates
     *
     * @since 1.0
     */
    @Override
    public void subscribe(String event, Observe listener) {
        this.listeners.put(event, listener);
    }

    /**
     * Removes the subscription to the given event,
     * if it exists in the context.
     *
     * @param event The event name to unsubscribe from
     *
     * @since 1.0
     */
    @Override
    public void unsubscribe(String event) {
        this.listeners.remove(event);
    }

    /**
     * Emits an event with the given payload.
     * If a listener is subscribed to the event, it is notified
     * and the payload is stored as the latest result.
     *
     * @param   event   The event name to emit
     * @param   <U>     The type of the payload being sent
     * @param   obj     The event payload
     *
     * @since 1.0
     */
    @Override
    public <U> void emit(String event, U obj) {
        Optional<Observe> listener = Optional.ofNullable(this.listeners.get(event));
        listener.ifPresent(observe -> observe.update(obj));
    }

    public <U> Observe<U> getObserve(String event) {
        return this.listeners.get(event);
    }

}