package result2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import unsafe.Result2;
import unsafe.UnsafeConsumer;

import java.util.Optional;
import java.util.function.Consumer;

import static org.mockito.Mockito.*;

@Deprecated
public class Result2Test {

    @Test
    public void testResultNoExceptionsDefaultHandling() {
        Result2<Integer> result = Result2.of(() -> 5 + 5);
        Assertions.assertTrue(result.hasResult());
        Assertions.assertFalse(result.hasError());
        int res = result.getResultDirectly();
        Assertions.assertEquals(10, res);
    }

    @Test
    public void testResultNoExceptionsOptionalHandling() {
        Result2<Integer> result = Result2.of(() -> 5 + 5);
        Optional<Integer> optionalResult = result.getResult();
        Assertions.assertTrue(optionalResult.isPresent());
        int res = optionalResult.get();
        Assertions.assertEquals(10, res);
    }

    @Test
    public void testResultNoExceptionsUnwrapHandlingNoException() {
        Result2<Integer> result = Result2.of(() -> 5 + 5);
        Result2<Integer> nextResult = result.unwrap(res -> res * 5);
        Assertions.assertTrue(nextResult.hasResult());
        Assertions.assertFalse(nextResult.hasError());
        int res = nextResult.getResultDirectly();
        Assertions.assertEquals(50, res);
    }

    @Test
    public void testResultNoExceptionsUnwrapResultNoException() {
        Result2<Integer> result = Result2.of(() -> 5 + 5);
        int res = result.unwrapResult(val -> val * 5);
        Assertions.assertEquals(50, res);
    }

    @Test
    public void testResultNoExceptionsHandle() {
        Result2<Integer> result = Result2.of(() -> 5 + 5);
        Optional<Integer> optional = result.handle(val -> val * 5, Assertions::fail);
        Assertions.assertTrue(optional.isPresent());
        Assertions.assertEquals(50, optional.get());
    }

    @Test
    public void testResultExceptionDefaultHandling() {
        Result2<Object> result = failing();
        Assertions.assertTrue(result.hasError());
        Assertions.assertFalse(result.hasResult());
        Throwable err = result.getErrorDirectly();
        Assertions.assertNotNull(err);
        assertIsRuntimeException(err);
    }

    @Test
    public void testResultExceptionOptionalHandling() {
        Result2<Object> result = failing();
        Optional<Throwable> optionalErr = result.getError();
        Assertions.assertTrue(optionalErr.isPresent());
        Throwable err = optionalErr.get();
        assertIsRuntimeException(err);
    }

    @Test
    public void testResultExceptionUnwrapHandlingNoException() {
        Result2<Integer> result = failing();
        Result2<Integer> nextResult = result.unwrap(res -> res * 5);
        Assertions.assertFalse(nextResult.hasResult());
        Assertions.assertTrue(nextResult.hasError());
        Throwable err = nextResult.getErrorDirectly();
        assertIsRuntimeException(err);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testResultExceptionUnwrapResultNoException() {
        Result2<Integer> result = failing();
        UnsafeConsumer<Throwable> mock = mock(UnsafeConsumer.class);
        result.unwrapException(mock);
        verify(mock, times(1)).accept(any(RuntimeException.class));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testResultExceptionHandle() {
        Result2<Integer> result = failing();
        Consumer<Throwable> mock = mock(Consumer.class);
        Optional<Integer> optionalInteger = result.handle(res -> res * 5, mock);
        Assertions.assertFalse(optionalInteger.isPresent());
        verify(mock, times(1)).accept(any(RuntimeException.class));
    }

    private <T> Result2<T> failing() {
        return Result2.of(() -> {
            throw new RuntimeException();
        });
    }

    private void assertIsRuntimeException(Throwable err) {
        Assertions.assertSame(RuntimeException.class, err.getClass());
    }
}
