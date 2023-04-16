package datastructures;

import boxedtypes.option.Option;
import result.Result;

/**
 * Helper class to effectively introduce typed tuples in java
 */
public interface DataSet {
    record Pair<A, B>(A first, B second) implements DataSet {
    }

    record Tuple_3<A, B, C>(A first, B second, C third) implements DataSet {
    }

    record Tuple_4<A, B, C, D>(A first, B second, C third, D fourth) implements DataSet {
    }

    record Tuple_5<A, B, C, D, E>(A first, B second, C third, D fourth, E fifth) implements DataSet {
    }

    record Tuple_6<A, B, C, D, E, F>(A first, B second, C third, D fourth, E fifth, F sixth) implements DataSet {
    }

    record Tuple_7<A, B, C, D, E, F, G>(A first, B second, C third, D fourth, E fifth, F sixth,
                                        G seventh) implements DataSet {
    }

    record Tuple_8<A, B, C, D, E, F, G, H>(A first, B second, C third, D fourth, E fifth, F sixth, G seventh,
                                           H eigth) implements DataSet {
    }

    record Tuple_9<A, B, C, D, E, F, G, H, I>(A first, B second, C third, D fourth, E fifth, F sixth, G seventh,
                                              H eigth, I ninth) implements DataSet {
    }

    record Tuple_10<A, B, C, D, E, F, G, H, I, J>(A first, B second, C third, D fourth, E fifth, F sixth, G seventh,
                                                  H eigth, I ninth, J tenth) implements DataSet {
    }

    record Tuple_11<A, B, C, D, E, F, G, H, I, J, K>(A first, B second, C third, D fourth, E fifth, F sixth, G seventh,
                                                     H eigth, I ninth, J tenth, K eleventh) implements DataSet {
    }

    record Tuple_12<A, B, C, D, E, F, G, H, I, J, K, L>(A first, B second, C third, D fourth, E fifth, F sixth,
                                                        G seventh, H eigth, I ninth, J tenth, K eleventh,
                                                        L twelfth) implements DataSet {
    }

    record Tuple_13<A, B, C, D, E, F, G, H, I, J, K, L, M>(A first, B second, C third, D fourth, E fifth, F sixth,
                                                           G seventh, H eigth, I ninth, J tenth, K eleventh, L twelfth,
                                                           M thirteenth) implements DataSet {
    }

    record Tuple_14<A, B, C, D, E, F, G, H, I, J, K, L, M, N>(A first, B second, C third, D fourth, E fifth, F sixth,
                                                              G seventh, H eigth, I ninth, J tenth, K eleventh,
                                                              L twelfth, M thirteenth,
                                                              N fourteenth) implements DataSet {
    }

    record Tuple_15<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O>(A first, B second, C third, D fourth, E fifth, F sixth,
                                                                 G seventh, H eigth, I ninth, J tenth, K eleventh,
                                                                 L twelfth, M thirteenth, N fourteenth,
                                                                 O fifteenth) implements DataSet {
    }

    record Tuple(Object... values) implements DataSet {
        public int length() {
            return values().length;
        }

        public Option<? extends Class<?>> getTypeAt(int index) {
            return getAt(index)
                    .map(Object::getClass)
                    .asOption();
        }

        @SuppressWarnings("unchecked")
        public <T> Result<T, ArrayIndexOutOfBoundsException> getValueAt(int index) {
            return getAt(index)
                    .map(val -> (T) val);
        }

        private Result<Object, ArrayIndexOutOfBoundsException> getAt(int index) {
            return Result.of(() -> values[index]);
        }
    }
}
