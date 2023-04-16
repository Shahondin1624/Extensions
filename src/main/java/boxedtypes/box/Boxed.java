package boxedtypes.box;

import boxedtypes.option.Option;

/**
 * Provides effectively a wrapper around a value. Implementations that are not {@link Box} must deal with thread safety
 *
 * @param <E> type of the stored value
 */
public interface Boxed<E> {
    Option<E> getValue();
}
