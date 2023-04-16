package proxies;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * Helper class that allows to create proxies of objects with specified callbacks very easily
 */
public class Proxies {
    /**
     * Creates a proxy for the passed object
     *
     * @param original    object for which the proxy shall be created
     * @param interceptor defining how the proxy should handle method calls
     * @param <T>         the generic type of the proxied value
     * @return the proxy
     */
    @SuppressWarnings("unchecked")
    public static <T> T proxy(T original, MethodInterceptor interceptor) {
        Enhancer enhancer = new Enhancer();
        Class<T> clazz = (Class<T>) original.getClass();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(interceptor);
        return (T) enhancer.create();
    }

    /**
     * Creates a {@link MethodInterceptor}
     */
    @SuppressWarnings("unchecked")
    public static <T, R> MethodInterceptor interceptor(MethodInterceptorWrapper<T, R> wrapper) {
        return (obj, method, args, proxy) -> wrapper.intercept((T) obj, args, proxy);
    }
}
