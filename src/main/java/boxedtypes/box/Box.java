package boxedtypes.box;

import boxedtypes.option.Option;

/**
 * A container that can be used to provide a reference to a value that can be shared between multiple objects or threads
 * even when the initial value is null
 *
 * @param <E> type of the stored value
 */
public class Box<E> implements Boxed<E> {
    private volatile E value;

    public Box() {
    }

    public Box(E value) {
        this.value = value;
    }

    public Option<E> getValue() {
        return Option.of(value);
    }

    public void setValue(E value) {
        this.value = value;
    }
}
