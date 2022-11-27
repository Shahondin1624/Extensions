package framework;

public class AbstractTest {
    protected void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            //nope
        }
    }
}
