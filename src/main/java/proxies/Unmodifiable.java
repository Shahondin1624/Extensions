package proxies;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Unmodifiable {

    /**
     * returns an unmodifiable copy of the original object. As the original is cloned by reflection, changes to the original
     * after the creation of its unmodifiable clone will not carry over. Note that the immutability is achieved by dirty-checking
     * whether the clone has changed after each method call and thus changes to objects inside the copy will not be detected
     *
     * @param original object of which an unmodifiable copy should be created
     * @param <T>      generic type of 'original'
     * @return an unmodifiable copy of 'original'
     */
    public static <T> T of(T original) {
        MethodInterceptor interceptor = createInterceptor(() -> clone(original));
        return Proxies.proxy(original, interceptor);
    }

    public static <T> T ofFullDepth(T original) { //TODO not yet working
        MethodInterceptor interceptor = createInterceptor(() -> clone(original, Integer.MAX_VALUE));
        return Proxies.proxy(original, interceptor);
    }

    public static <T> T ofDepth(T original, int depth) { //TODO not yet working
        MethodInterceptor interceptor = createInterceptor(() -> clone(original, depth));
        return Proxies.proxy(original, interceptor);
    }

    private static <T> MethodInterceptor createInterceptor(Supplier<T> copyFunction) {
        return Proxies.interceptor((obj, args, proxy) -> {
            T copy = copyFunction.get();
            Object result = proxy.invoke(copy, args);
            if (dirtyCheck(copy, obj)) {
                throw new IllegalModificationException("Calling %s would change this object, which is not permitted!", proxy.getSuperName());
            }
            return result;
        });
    }

    @SuppressWarnings("unchecked")
    private static <T> T clone(T original, int depth) {
        Class<T> clazz = (Class<T>) original.getClass();
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            T newInstance = constructor.newInstance();
            constructor.setAccessible(false);
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                Object originalValue = field.get(original);
                Object clone = originalValue;
                if (depth > 0) {
                    clone = ofDepth(originalValue, depth - 1);
                }
                field.set(newInstance, clone);
                field.setAccessible(false);
            }
            return newInstance;
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param <T> must be a class with a default constructor
     * @return a clone of the original object
     */
    @SuppressWarnings("unchecked")
    public static <T> T clone(T original) {
        Class<T> clazz = (Class<T>) original.getClass();
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            T newInstance = constructor.newInstance();
            constructor.setAccessible(false);
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                Object originalValue = field.get(original);
                field.set(newInstance, originalValue);
                field.setAccessible(false);
            }
            return newInstance;
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /***
     * @return true when any field has been changed
     */
    @SuppressWarnings("unchecked")
    private static <T> boolean dirtyCheck(T original, T copy) {
        Class<T> clazz = (Class<T>) original.getClass();
        try {
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                Object originalValue = field.get(original);
                Object dirtyCheckerValue = field.get(copy);
                if (!Objects.equals(originalValue, dirtyCheckerValue)) {
                    return true;
                }
                field.setAccessible(false);
            }
            return false;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T lock(T object) {
        return lock(object, (String[]) null);
    }

    public static <T> T lock(T object, String... methodNames) {
        Optional<List<Pattern>> patterns = computePatterns(methodNames);
        MethodInterceptor interceptor = Proxies.interceptor((obj, args, proxy) -> {
            String methodName = proxy.getSuperName();
            Supplier<ObjectLockedException> exception = getObjectLockedExceptionSupplier(methodName);
            if (patterns.isPresent()) {
                if (methodMatches(methodName, patterns.get())) {
                    throw exception.get();
                }
            } else {
                throw exception.get();
            }
            return proxy.invoke(obj, args);
        });
        return Proxies.proxy(object, interceptor);
    }

    private static Supplier<ObjectLockedException> getObjectLockedExceptionSupplier(String methodName) {
        return () -> new ObjectLockedException("Calling %s would alter this object which is not permitted", methodName);
    }

    private static boolean methodMatches(String methodName, List<Pattern> lockedMethodNames) {
        for (Pattern pattern : lockedMethodNames) {
            if (pattern.asMatchPredicate().test(methodName)) {
                return true;
            }
        }
        return false;
    }

    private static Optional<List<Pattern>> computePatterns(String... names) {
        if (names == null || names.length == 0) {
            return Optional.empty();
        }
        return Optional.ofNullable(generateMethodNamePatterns(names));
    }

    private static List<Pattern> generateMethodNamePatterns(String... names) {
        return Stream.of(names) //
                .map(Unmodifiable::compileToCglibPattern) //
                .toList();
    }

    private static String wrapWithPattern(String name) {
        return "(CGLIB\\$)*" + name + "(\\$\\d+)*";
    }

    private static Pattern compileToCglibPattern(String name) {
        return Pattern.compile(wrapWithPattern(name));
    }

    @SuppressWarnings("unchecked")
    public static <T extends Lockable<V>, V> T extendWithLockable(V original) {
        final Pattern lockMethodName = compileToCglibPattern("lock");
        final Pattern isLockedMethodName = compileToCglibPattern("isLocked");
        final Pattern lockedMethodName = compileToCglibPattern("locked");
        MethodInterceptor interceptor = Proxies.interceptor(new MethodInterceptorWrapper<>() {
            private boolean locked = false;

            @Override
            public Object intercept(Object obj, Object[] args, MethodProxy proxy) throws Throwable {
                String methodName = proxy.getSuperName();
                Supplier<ObjectLockedException> exception = getObjectLockedExceptionSupplier(methodName);
                if (lockMethodName.asMatchPredicate().test(methodName)) {
                    locked = true;
                    return null;
                } else if (isLockedMethodName.asMatchPredicate().test(methodName)) {
                    return locked;
                } else if (locked) {
                    throw exception.get();
                } else if (lockedMethodName.asMatchPredicate().test(methodName)) {
                    return obj;
                } else {
                    return proxy.invoke(obj, args);
                }
            }
        });
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(original.getClass());
        enhancer.setInterfaces(new Class[]{Lockable.class});
        enhancer.setCallback(interceptor);
        return (T) enhancer.create();
    }
}
