package async;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.SOURCE)
@Target(value = {ElementType.METHOD})
/**
 Marker interface to indicate this method is executed asynchronously
 */
public @interface Asynchronous {
}
