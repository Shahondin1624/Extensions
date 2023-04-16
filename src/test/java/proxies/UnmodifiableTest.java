package proxies;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class UnmodifiableTest {
    @Test
    public void testDirtyChecking() {
        TestDto original = new TestDto();
        TestDto copy = original.copy();
        Assertions.assertFalse(performDirtyCheck(original, copy), "Detected a nonexistent change!");
        copy.setName("Test");
        Assertions.assertTrue(performDirtyCheck(original, copy), "Did not detect a change!");
    }

    private boolean performDirtyCheck(Object original, Object copy) {
        try {
            Method dirtyCheck = Unmodifiable.class.getDeclaredMethod("dirtyCheck", Object.class, Object.class);
            dirtyCheck.setAccessible(true);
            return (boolean) dirtyCheck.invoke(null, original, copy);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testCloningWithEmptyConstructor() {
        TestDto original = new TestDto();
        original.setName("Test");
        TestDto clone = Unmodifiable.clone(original);
        Assertions.assertEquals(original, clone);
    }

    @Test()
    public void testCloningWithoutEmptyConstructor() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            BadDto dto = new BadDto("Test");
            BadDto clone = Unmodifiable.clone(dto);
        });
    }

    private static class BadDto extends TestDto {
        public BadDto(String block) {
        }
    }

    @Test
    public void testUnmodifiable() {
        Assertions.assertThrows(IllegalModificationException.class, () -> {
            TestDto dto = new TestDto();
            TestDto unmodifiable = Unmodifiable.of(dto);
            unmodifiable.setName("Test");
        });
    }

    @Test
    public void testUnmodifiableWithDepth2() {
        TestDto dto = new TestDto();
        TestDtoDepth outer = new TestDtoDepth();
        outer.setDto(dto);
        TestDtoDepth unmodifiable = Unmodifiable.ofDepth(outer, 2);
        Assertions.assertThrows(IllegalModificationException.class, () -> {
            unmodifiable.setName("Test");
        });
        Assertions.assertThrows(IllegalModificationException.class, () -> {
            unmodifiable.getDto().setName("Test");
        });
    }

    @Test
    public void testUnmodifiableWithFullDepth() {
        TestDto dto = new TestDto();
        TestDtoDepth outer = new TestDtoDepth();
        outer.setDto(dto);
        TestDtoDepth unmodifiable = Unmodifiable.ofFullDepth(outer);
        Assertions.assertThrows(IllegalModificationException.class, () -> {
            unmodifiable.setName("Test");
        });
        Assertions.assertThrows(IllegalModificationException.class, () -> {
            unmodifiable.getDto().setName("Test");
        });
    }

    @Test
    public void testLockCollection() {
        Assertions.assertThrows(ObjectLockedException.class, () -> {
            List<String> list = new ArrayList<>();
            List<String> locked = Unmodifiable.lock(list);
            locked.add("Test");
        });
    }

    @Test
    public void testLockOnlyOneMethod() {
        Assertions.assertThrows(ObjectLockedException.class, () -> {
            List<String> list = new ArrayList<>();
            List<String> locked = Unmodifiable.lock(list, "remove");
            String string = "Test";
            list.add(string);
            Assertions.assertEquals(1, list.size());
            locked.remove(string);
        });
    }

    @Test
    public void testExtendWithLockable() {
        List<String> list = new ArrayList<>();
        Lockable<List<String>> lockable = Unmodifiable.extendWithLockable(list);
        List<String> inner = lockable.locked();
        Assertions.assertNotNull(inner);
        lockable.lock();
        Assertions.assertTrue(lockable.isLocked());
        Assertions.assertThrows(ObjectLockedException.class, () -> {
            lockable.locked().add("Test");
            Assertions.assertEquals(0, lockable.locked().size());
        });
    }
}
