package boxedtypes.box;

import boxedtypes.option.Option;

public class Box<E> implements Boxed<E> {
    private E value;

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
