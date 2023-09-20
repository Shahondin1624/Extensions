package iterators;

/**
 * Marker interface
 * Assumes that all iterators might not be of the same length and thus returns {@link boxedtypes.option.Option's} to enforce
 * null-safety
 * Should not be used when you know for certain your iterators will be of the same length, as it will make your code less readable
 * due to {@link boxedtypes.option.Option's} overhead
 */
public interface UnequalLengthTupleIterator {
}
