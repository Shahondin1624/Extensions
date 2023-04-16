package result;

public record Ok<T, Err extends Throwable>(T ok) implements Result<T, Err> {

}
