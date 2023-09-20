package iterators;

import java.util.Iterator;

/**
 * Marker interface
 * Assumes that all iterators will be of the same length and thus won't return {@link boxedtypes.option.Option's}
 * Should this prove to be false, an {@link IteratorUnequalLengthException} will be thrown during the {@link Iterator#hasNext()}
 * method call!
 */
public interface EqualLengthTupleIterator {
}
