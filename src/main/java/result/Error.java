package result;

public record Error<T, Err extends Throwable>(Err error) implements Result<T, Err> {
}
