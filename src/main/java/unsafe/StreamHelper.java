package unsafe;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamHelper {
    public static <T> Stream<T> filter(Stream<T> stream, UnsafePredicate<T> predicate) {
        return stream.filter(predicate);
    }

    public static <T, R> Stream<R> map(Stream<T> stream, UnsafeFunction<T, R> mapper) {
        return stream.map(mapper);
    }

    public static <T> Stream<T> open(Collection<T> collection) {
        return collection.stream();
    }

    public static <T> Stream<T> openAndFilter(Collection<T> collection, UnsafePredicate<T> predicate) {
        return collection.stream().filter(predicate);
    }

    public static <T, K, V> Map<K, V> toMap(Stream<T> stream, UnsafeFunction<T, K> keyMapper, UnsafeFunction<T, V> valueMapper) {
        return stream.collect(Collectors.toMap(keyMapper, valueMapper));
    }
}
