package proxies;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

public class Proxies {
    @SuppressWarnings("unchecked")
    public static <T> T proxy(T original, MethodInterceptor interceptor) {
        Enhancer enhancer = new Enhancer();
        Class<T> clazz = (Class<T>) original.getClass();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(interceptor);
        return (T) enhancer.create();
    }

    @SuppressWarnings("unchecked")
    public static <T, R> MethodInterceptor interceptor(MethodInterceptorWrapper<T, R> wrapper) {
        return (obj, method, args, proxy) -> wrapper.intercept((T) obj, args, proxy);
    }
}
