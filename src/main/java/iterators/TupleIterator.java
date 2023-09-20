package iterators;

/**
 * Marker interface
 * Implementations of this interface try to mimic Rust's zip() functionality that its iterators possess. It essentially
 * allows combining iterators into each other, so Iterator<A> & Iterator<B> becomes Iterator<A,B> Tuples are not natively
 * supported in Java which makes this a bit harder and not as elegant as in Rust sadly.
 * zip() will stop iterating once it reaches the end of the shorter iterator, I chose to instead provide two different ones:
 * {@link EqualLengthTupleIterator} and {@link UnequalLengthTupleIterator}
 * My opinion is that zipping more than three iterators is probably a bad idea, so I won't provide implementations for that,
 * however the marker interfaces are not sealed, so if desired, one could implement them themselves.
 */
public interface TupleIterator {
}
