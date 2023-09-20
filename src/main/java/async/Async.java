package async;

import datastructures.DataSet;
import unsafe.UnsafeFunction;
import unsafe.UnsafeSupplier;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * Helper class that lets the caller easily await all those values asynchronously. Those methods will only return when all
 * values have been computed, but the declared execution order does not matter. This enables to compute values that are
 * independent of each other in parallel. Do not use these methods if your computations are dependent on each other!
 */
public interface Async {
    @Asynchronous
    static <T> T await(UnsafeSupplier<T> function) {
        AsyncSupplier<T> supplier = asAsync(function);
        return supplier.await();
    }

    static <T> AsyncSupplier<T> asAsync(UnsafeSupplier<T> function) {
        return function::getUnsafe;
    }

    @Asynchronous
    static Tuple awaitAllAndCollect(UnsafeSupplier<?>... functions) {
        Objects.requireNonNull(functions);
        List<? extends CompletableFuture<?>> futures = Arrays.stream(functions)
                .map(Async::asAsync)
                .map(AsyncSupplier::future)
                .toList();
        while (futures.stream()
                .map(CompletableFuture::isDone)
                .count() != futures.size()) {
            //wait
        }
        return new Tuple(futures.stream()
                .map(CompletableFuture::join)
                .toArray());
    }

    @Asynchronous
    static <T> T awaitAllAndCollect(UnsafeFunction<Tuple, T> reductorFunction, UnsafeSupplier<?>... functions) {
        Objects.requireNonNull(reductorFunction);
        Objects.requireNonNull(functions);
        return reductorFunction.apply(awaitAllAndCollect(functions));
    }

    @Asynchronous
    static <A, B>
    DataSet.Pair<A, B>
    awaitAll(UnsafeSupplier<A> a, UnsafeSupplier<B> b) {
        Tuple result = awaitAllAndCollect(a, b);
        return new DataSet.Pair<>(result.getAt(0), result.getAt(1));
    }

    @Asynchronous
    static <A, B, C>
    DataSet.Tuple_3<A, B, C>
    awaitAll(UnsafeSupplier<A> a, UnsafeSupplier<B> b, UnsafeSupplier<C> c) {
        Tuple result = awaitAllAndCollect(a, b, c);
        return new DataSet.Tuple_3<>(result.getAt(0), result.getAt(1), result.getAt(2));
    }

    @Asynchronous
    static <A, B, C, D>
    DataSet.Tuple_4<A, B, C, D>
    awaitAll(UnsafeSupplier<A> a, UnsafeSupplier<B> b, UnsafeSupplier<C> c, //
             UnsafeSupplier<D> d) {
        Tuple result = awaitAllAndCollect(a, b, c, d);
        return new DataSet.Tuple_4<>(result.getAt(0), result.getAt(1), result.getAt(2), result.getAt(3));
    }

    @Asynchronous
    static <A, B, C, D, E>
    DataSet.Tuple_5<A, B, C, D, E>
    awaitAll(UnsafeSupplier<A> a, UnsafeSupplier<B> b, UnsafeSupplier<C> c, //
             UnsafeSupplier<D> d, UnsafeSupplier<E> e) {
        Tuple result = awaitAllAndCollect(a, b, c, d, e);
        return new DataSet.Tuple_5<>(result.getAt(0), result.getAt(1), result.getAt(2), result.getAt(3), //
                result.getAt(4));
    }

    @Asynchronous
    static <A, B, C, D, E, F>
    DataSet.Tuple_6<A, B, C, D, E, F>
    awaitAll(UnsafeSupplier<A> a, UnsafeSupplier<B> b, UnsafeSupplier<C> c, //
             UnsafeSupplier<D> d, UnsafeSupplier<E> e, UnsafeSupplier<F> f) {
        Tuple result = awaitAllAndCollect(a, b, c, d, e, f);
        return new DataSet.Tuple_6<>(result.getAt(0), result.getAt(1), result.getAt(2), result.getAt(3), //
                result.getAt(4), result.getAt(5));
    }

    @Asynchronous
    static <A, B, C, D, E, F, G>
    DataSet.Tuple_7<A, B, C, D, E, F, G>
    awaitAll(UnsafeSupplier<A> a, UnsafeSupplier<B> b, UnsafeSupplier<C> c, //
             UnsafeSupplier<D> d, UnsafeSupplier<E> e, UnsafeSupplier<F> f, //
             UnsafeSupplier<G> g) {
        Tuple result = awaitAllAndCollect(a, b, c, d, e, f, g);
        return new DataSet.Tuple_7<>(result.getAt(0), result.getAt(1), result.getAt(2), result.getAt(3), //
                result.getAt(4), result.getAt(5), result.getAt(6));
    }

    @Asynchronous
    static <A, B, C, D, E, F, G, H>
    DataSet.Tuple_8<A, B, C, D, E, F, G, H>
    awaitAll(UnsafeSupplier<A> a, UnsafeSupplier<B> b, UnsafeSupplier<C> c, //
             UnsafeSupplier<D> d, UnsafeSupplier<E> e, UnsafeSupplier<F> f, //
             UnsafeSupplier<G> g, UnsafeSupplier<H> h) {
        Tuple result = awaitAllAndCollect(a, b, c, d, e, f, g, h);
        return new DataSet.Tuple_8<>(result.getAt(0), result.getAt(1), result.getAt(2), result.getAt(3), //
                result.getAt(4), result.getAt(5), result.getAt(6), result.getAt(7));
    }

    @Asynchronous
    static <A, B, C, D, E, F, G, H, I>
    DataSet.Tuple_9<A, B, C, D, E, F, G, H, I>
    awaitAll(UnsafeSupplier<A> a, UnsafeSupplier<B> b, UnsafeSupplier<C> c, //
             UnsafeSupplier<D> d, UnsafeSupplier<E> e, UnsafeSupplier<F> f, //
             UnsafeSupplier<G> g, UnsafeSupplier<H> h, UnsafeSupplier<I> i) {
        Tuple result = awaitAllAndCollect(a, b, c, d, e, f, g, h, i);
        return new DataSet.Tuple_9<>(result.getAt(0), result.getAt(1), result.getAt(2), result.getAt(3), //
                result.getAt(4), result.getAt(5), result.getAt(6), result.getAt(7), result.getAt(8));
    }

    @Asynchronous
    static <A, B, C, D, E, F, G, H, I, J>
    DataSet.Tuple_10<A, B, C, D, E, F, G, H, I, J>
    awaitAll(UnsafeSupplier<A> a, UnsafeSupplier<B> b, UnsafeSupplier<C> c, //
             UnsafeSupplier<D> d, UnsafeSupplier<E> e, UnsafeSupplier<F> f, //
             UnsafeSupplier<G> g, UnsafeSupplier<H> h, UnsafeSupplier<I> i, //
             UnsafeSupplier<J> j) {
        Tuple result = awaitAllAndCollect(a, b, c, d, e, f, g, h, i, j);
        return new DataSet.Tuple_10<>(result.getAt(0), result.getAt(1), result.getAt(2), result.getAt(3), //
                result.getAt(4), result.getAt(5), result.getAt(6), result.getAt(7), result.getAt(8), //
                result.getAt(9));
    }

    @Asynchronous
    static <A, B, C, D, E, F, G, H, I, J, K>
    DataSet.Tuple_11<A, B, C, D, E, F, G, H, I, J, K>
    awaitAll(UnsafeSupplier<A> a, UnsafeSupplier<B> b, UnsafeSupplier<C> c, //
             UnsafeSupplier<D> d, UnsafeSupplier<E> e, UnsafeSupplier<F> f, //
             UnsafeSupplier<G> g, UnsafeSupplier<H> h, UnsafeSupplier<I> i, //
             UnsafeSupplier<J> j, UnsafeSupplier<K> k) {
        Tuple result = awaitAllAndCollect(a, b, c, d, e, f, g, h, i, j, k);
        return new DataSet.Tuple_11<>(result.getAt(0), result.getAt(1), result.getAt(2), result.getAt(3), //
                result.getAt(4), result.getAt(5), result.getAt(6), result.getAt(7), result.getAt(8), //
                result.getAt(9), result.getAt(10));
    }

    @Asynchronous
    static <A, B, C, D, E, F, G, H, I, J, K, L>
    DataSet.Tuple_12<A, B, C, D, E, F, G, H, I, J, K, L>
    awaitAll(UnsafeSupplier<A> a, UnsafeSupplier<B> b, UnsafeSupplier<C> c, //
             UnsafeSupplier<D> d, UnsafeSupplier<E> e, UnsafeSupplier<F> f, //
             UnsafeSupplier<G> g, UnsafeSupplier<H> h, UnsafeSupplier<I> i, //
             UnsafeSupplier<J> j, UnsafeSupplier<K> k, UnsafeSupplier<L> l) {
        Tuple result = awaitAllAndCollect(a, b, c, d, e, f, g, h, i, j, k, l);
        return new DataSet.Tuple_12<>(result.getAt(0), result.getAt(1), result.getAt(2), result.getAt(3), //
                result.getAt(4), result.getAt(5), result.getAt(6), result.getAt(7), result.getAt(8), //
                result.getAt(9), result.getAt(10), result.getAt(11));
    }

    @Asynchronous
    static <A, B, C, D, E, F, G, H, I, J, K, L, M>
    DataSet.Tuple_13<A, B, C, D, E, F, G, H, I, J, K, L, M>
    awaitAll(UnsafeSupplier<A> a, UnsafeSupplier<B> b, UnsafeSupplier<C> c, //
             UnsafeSupplier<D> d, UnsafeSupplier<E> e, UnsafeSupplier<F> f, //
             UnsafeSupplier<G> g, UnsafeSupplier<H> h, UnsafeSupplier<I> i, //
             UnsafeSupplier<J> j, UnsafeSupplier<K> k, UnsafeSupplier<L> l, //
             UnsafeSupplier<M> m) {
        Tuple result = awaitAllAndCollect(a, b, c, d, e, f, g, h, i, j, k, l, m);
        return new DataSet.Tuple_13<>(result.getAt(0), result.getAt(1), result.getAt(2), result.getAt(3), //
                result.getAt(4), result.getAt(5), result.getAt(6), result.getAt(7), result.getAt(8), //
                result.getAt(9), result.getAt(10), result.getAt(11), result.getAt(12));
    }

    @Asynchronous
    static <A, B, C, D, E, F, G, H, I, J, K, L, M, N>
    DataSet.Tuple_14<A, B, C, D, E, F, G, H, I, J, K, L, M, N>
    awaitAll(UnsafeSupplier<A> a, UnsafeSupplier<B> b, UnsafeSupplier<C> c, //
             UnsafeSupplier<D> d, UnsafeSupplier<E> e, UnsafeSupplier<F> f, //
             UnsafeSupplier<G> g, UnsafeSupplier<H> h, UnsafeSupplier<I> i, //
             UnsafeSupplier<J> j, UnsafeSupplier<K> k, UnsafeSupplier<L> l, //
             UnsafeSupplier<M> m, UnsafeSupplier<N> n) {
        Tuple result = awaitAllAndCollect(a, b, c, d, e, f, g, h, i, j, k, l, m, n);
        return new DataSet.Tuple_14<>(result.getAt(0), result.getAt(1), result.getAt(2), result.getAt(3), //
                result.getAt(4), result.getAt(5), result.getAt(6), result.getAt(7), result.getAt(8), //
                result.getAt(9), result.getAt(10), result.getAt(11), result.getAt(12), result.getAt(13));
    }

    @Asynchronous
    static <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O>
    DataSet.Tuple_15<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O>
    awaitAll(UnsafeSupplier<A> a, UnsafeSupplier<B> b, UnsafeSupplier<C> c, //
             UnsafeSupplier<D> d, UnsafeSupplier<E> e, UnsafeSupplier<F> f, //
             UnsafeSupplier<G> g, UnsafeSupplier<H> h, UnsafeSupplier<I> i, //
             UnsafeSupplier<J> j, UnsafeSupplier<K> k, UnsafeSupplier<L> l, //
             UnsafeSupplier<M> m, UnsafeSupplier<N> n, UnsafeSupplier<O> o) {
        Tuple result = awaitAllAndCollect(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o);
        return new DataSet.Tuple_15<>(result.getAt(0), result.getAt(1), result.getAt(2), result.getAt(3), //
                result.getAt(4), result.getAt(5), result.getAt(6), result.getAt(7), result.getAt(8), //
                result.getAt(9), result.getAt(10), result.getAt(11), result.getAt(12), result.getAt(13), //
                result.getAt(14));
    }
}
