package proxies;

import net.sf.cglib.proxy.MethodProxy;

/**
 * Helper interface to allow for easier and generic declaration of {@link net.sf.cglib.proxy.MethodInterceptor}s
 *
 * @param <T> the type of the object that should register the callback
 * @param <R> type of the return value of the intercepted method
 */
@FunctionalInterface
public interface MethodInterceptorWrapper<T, R> {
    R intercept(T obj, Object[] args, MethodProxy proxy) throws Throwable;
}
