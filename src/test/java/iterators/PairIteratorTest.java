package iterators;

import boxedtypes.option.Option;
import datastructures.DataSet;
import iterators.pairiterator.PairIterator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

public class PairIteratorTest {
    private final Supplier<List<String>> first = () -> List.of("Test1", "Test2", "Test3");
    private final Supplier<List<String>> second = () -> List.of("Test1", "Test2");

    @Test
    public void testEqualLengthPairEqualLengthIterator() {
        Iterator<String> first = PairIteratorTest.this.first.get().iterator();
        Iterator<String> second = PairIteratorTest.this.first.get().iterator();
        PairIterator<String, String> iterator = PairIterator.ofEqualLength(first, second);
        while (iterator.hasNext()) {
            DataSet.Pair<String, String> next = iterator.next();
            Assertions.assertEquals(next.first(), next.second());
        }
    }

    @Test
    public void testUnequalLengthPairEqualLengthIterator() {
        Iterator<String> first = PairIteratorTest.this.first.get().iterator();
        Iterator<String> second = PairIteratorTest.this.second.get().iterator();
        PairIterator<String, String> iterator = PairIterator.ofEqualLength(first, second);
        for (int i = 0; i < 2; i++) {
            boolean hasNext = iterator.hasNext();
            Assertions.assertTrue(hasNext);
            DataSet.Pair<String, String> next = iterator.next();
            Assertions.assertEquals(next.first(), next.second());
        }
        Assertions.assertThrows(IteratorUnequalLengthException.class, iterator::hasNext);
    }

    @Test
    public void testEqualLengthPairUnequalLengthIterator() {
        Iterator<String> first = PairIteratorTest.this.first.get().iterator();
        Iterator<String> second = PairIteratorTest.this.first.get().iterator();
        PairIterator<Option<String>, Option<String>> iterator = PairIterator.ofUnequalLength(first, second);
        while (iterator.hasNext()) {
            DataSet.Pair<Option<String>, Option<String>> next = iterator.next();
            Assertions.assertDoesNotThrow(() -> {
                Option<String> firstOption = next.first();
                Option<String> secondOption = next.second();
                String firstStr = firstOption.orElseThrow();
                String secondStr = secondOption.orElseThrow();
                Assertions.assertEquals(firstStr, secondStr);
            });
        }
    }

    @Test
    public void testUnequalLengthPairUnequalLengthIterator() {
        Iterator<String> first = PairIteratorTest.this.first.get().iterator();
        Iterator<String> second = PairIteratorTest.this.second.get().iterator();
        PairIterator<Option<String>, Option<String>> iterator = PairIterator.ofUnequalLength(first, second);
        for (int i = 0; i < 2; i++) {
            boolean hasNext = iterator.hasNext();
            Assertions.assertTrue(hasNext);
            DataSet.Pair<Option<String>, Option<String>> next = iterator.next();
            Assertions.assertDoesNotThrow(() -> {
                Option<String> firstOption = next.first();
                Option<String> secondOption = next.second();
                String firstStr = firstOption.orElseThrow();
                String secondStr = secondOption.orElseThrow();
                Assertions.assertEquals(firstStr, secondStr);
            });
        }
        DataSet.Pair<Option<String>, Option<String>> finalNext = iterator.next();
        Assertions.assertThrows(NoSuchElementException.class, () -> finalNext.second().orElseThrow());
    }
}
