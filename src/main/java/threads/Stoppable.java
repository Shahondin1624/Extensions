package threads;

public interface Stoppable extends Runnable {
    void requestShutdown();

    boolean isShutdownRequested();

    void executeWork();
}
