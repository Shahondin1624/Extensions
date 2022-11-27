package threads;

import exceptions.ExceptionHandler;
import execution.ExecutionWrapper;
import general.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Utilities;

import javax.inject.Inject;
import java.lang.invoke.MethodHandles;

public abstract class DaemonThread implements Stoppable, Named {
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final String name;
    private boolean shutdownRequested = false;
    private CpuClock cpuClock;
    private ExecutionWrapper executor;
    private ExceptionHandler exceptionHandler;
    private Throwable lastException = null;

    public DaemonThread(String name) {
        this.name = name;
    }

    public DaemonThread(String name, ExecutionWrapper executor) {
        this.name = name;
        this.executor = executor;
    }

    public DaemonThread(String name, ExecutionWrapper executor, ExceptionHandler exceptionHandler) {
        this.name = name;
        this.executor = executor;
        this.exceptionHandler = exceptionHandler;
    }

    @Override
    public void requestShutdown() {
        log.info("Shutdown was requested");
        shutdownRequested = true;
    }

    @Override
    public boolean isShutdownRequested() {
        return shutdownRequested;
    }

    @Override
    public void run() {
        startCpuClock();
        log.info("Starting {}...", this);
        while (!shutdownRequested) {
            log.trace("New iteration of {}.{}", this, logCurrentRuntime());
            try {
                if (executor != null) {
                    executor.execute(this::executeWork);
                } else {
                    executeWork();
                }
            } catch (Throwable t) {
                if (exceptionHandler != null) {
                    exceptionHandler.handleThrowable(t);
                } else {
                    log.error("An exception that could not be handled occurred: {}", Utilities.logError(t));
                    lastException = t;
                }
            }
        }
        log.info("Finished executing thread...");
    }

    @Inject
    public void setCpuClock(CpuClock cpuClock) {
        this.cpuClock = cpuClock;
    }

    protected void startCpuClock() {
        if (cpuClock != null) {
            cpuClock.start();
        }
    }

    protected void stopCpuClock() {
        if (cpuClock != null) {
            long runtime = cpuClock.stop();
            log.info("{} ran for a total of {}", this, Utilities.formatNanos(runtime));
        }
    }

    protected String logCurrentRuntime() {
        if (cpuClock != null) {
            long currentRuntime = cpuClock.getCurrentRuntimeDuration();
            return String.format(" %s", Utilities.formatNanos(currentRuntime));
        } else {
            return "";
        }
    }

    public void setExecutor(ExecutionWrapper executor) {
        this.executor = executor;
    }

    public void setExceptionHandler(ExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    public Throwable getLastException() {
        return lastException;
    }

    public void resetLastException() {
        lastException = null;
    }

    @Override
    public String getName() {
        return name;
    }
}
