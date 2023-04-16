package result;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ResultTest {
    @Test
    public void testOk() {
        Result<String, RuntimeException> result = create(false);
        switch (result) {
            case Ok<String, RuntimeException> ok -> {
                //Ignore
            }
            default -> Assertions.fail();
        }
    }

    @Test
    public void testError() {
        Result<String, RuntimeException> result = create(true);
        switch (result) {
            case Error<String, RuntimeException> error -> {
                Assertions.assertEquals(RuntimeException.class, error.error().getClass());
            }
            default -> Assertions.fail();
        }
    }

    @Test
    public void testResultOfOk() {
        Result<String, Throwable> result = Result.of(() -> "Test");
        if (result instanceof Ok<String, Throwable> ok) {
            Assertions.assertEquals("Test", ok.ok());
        } else {
            Assertions.fail();
        }
    }

    @Test
    public void testResultOfError() {
        Result<String, Throwable> result = Result.of(() -> {
            throw new RuntimeException();
        });
        if (result instanceof Error<String, Throwable> err) {
            Assertions.assertEquals(RuntimeException.class, err.error().getClass());
        } else {
            Assertions.fail();
        }
    }

    private Result<String, RuntimeException> create(boolean asError) {
        if (asError) {
            return new Error<>(new RuntimeException());
        } else {
            return new Ok<>("Test");
        }
    }
}
