package framework;

import org.junit.jupiter.api.Assertions;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class AbstractSecureTest<X> extends AbstractTest {
    private final Supplier<X> functionSupplier;

    protected AbstractSecureTest(Supplier<X> functionSupplier) {
        this.functionSupplier = functionSupplier;
    }

    protected TestBuilder<X> runSafe(Consumer<X> test) {
        return new TestBuilder<X>(test, functionSupplier);
    }

    protected <T> void assertListContentEquals(List<T> expectedList, List<T> actualList) {
        Assertions.assertNotNull(expectedList);
        Assertions.assertNotNull(actualList);
        Assertions.assertEquals(expectedList.size(), actualList.size(), "Size of lists not equal");
        for (int i = 0; i < expectedList.size(); i++) {
            T expected = expectedList.get(i);
            T actual = actualList.get(i);
            Assertions.assertEquals(expected, actual, String.format("Values at index %d are not equal", i));
        }
    }
}
