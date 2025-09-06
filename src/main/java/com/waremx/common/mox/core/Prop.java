package com.waremx.common.mox.core;


/**
 * {@code Prop} represents a typed property that encapsulates an identifier, a value,
 * and its corresponding class type. It is used as a safe container within a {@code Context}
 * to store and retrieve data with runtime type validation.
 *
 * @param <T> the type of the value stored in this property
 *
 * @author ymoyamac
 * @since 1.0
 */
public class Prop<T> {

    private String id;
    private T value;
    private Class<T> clazz;

    /**
     * Private constructor. Use the static factory method {@link #bind(String, Object)}
     * to create instances safely.
     *
     * @param   id      The unique identifier for the property
     * @param   value   The value to store
     * @param   clazz   The class of the value (used for type checking)
     *
     * @since 1.0
     */
    private Prop(String id, T value, Class<T> clazz) {
        this.id = id;
        this.value = value;
        this.clazz = clazz;
    }

    /**
     * Returns the identifier of this property.
     *
     * @return the property ID
     *
     * @since 1.0
     */
    public String getId() {
        return id;
    }

    /**
     * Sets a new identifier for this property.
     *
     * @param id The new identifier
     *
     * @since 1.0
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Returns the current value of this property.
     *
     * @return the stored value
     *
     * @since 1.0
     */
    public T getValue() {
        return value;
    }

    /**
     * Sets a new value for this property.
     *
     * @param value The new value to store
     *
     * @since 1.0
     */
    public void setValue(T value) {
        this.value = value;
    }

    /**
     * Returns the class type of the stored value.
     *
     * @return  {@link Class} Object representing the type {@code T}
     *
     * @since 1.0
     */
    public Class<T> getClazz() {
        return this.clazz;
    }

    /**
     * Factory method to create a new {@code Prop} instance, automatically inferring the
     * value's class.
     *
     * <p><strong>Note:</strong> This method uses an unchecked cast based on
     * {@code value.getClass()}, so it may not work safely with generic types like
     * {@code List<String>} or other parameterized types.</p>
     *
     * @param   id      The identifier for the property
     * @param   value   The value to associate
     * @param   <T>     The type of the value
     * @return  {@code Prop<T>} New instance
     *
     * @since 1.0
     */
    @SuppressWarnings("unchecked")
    public final static <T> Prop<T> bind(String id, T value) {
        return new Prop<>(id, value, (Class<T>) value.getClass());
    }

    @Override
    public String toString() {
        return "Prop {id=" + id + ", value=" + value + ", clazz=" + clazz + "}";
    }


}