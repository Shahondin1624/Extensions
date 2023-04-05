package boxedtypes.box;

import boxedtypes.option.Option;

public interface Boxed<E> {
    Option<E> getValue();
}
