package boxedtypes.option;

import java.util.Objects;

public record Some<E>(E value) implements Option<E> {
    public Some {
        Objects.requireNonNull(value, "Null values are forbidden");
    }
}
