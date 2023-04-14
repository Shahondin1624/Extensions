package proxies;

import net.sf.cglib.proxy.MethodProxy;

@FunctionalInterface
public interface MethodInterceptorWrapper<T, R> {
    R intercept(T obj, Object[] args, MethodProxy proxy) throws Throwable;
}
