package boxedtypes.option;

@SuppressWarnings({"unchecked", "rawtypes"})
public record None<E>() implements Option<E> {
    public static final None NONE = new None<>();

    public static <E> None<E> none() {
        return (None<E>) NONE;
    }
}
