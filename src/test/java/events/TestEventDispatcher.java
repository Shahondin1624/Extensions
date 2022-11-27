package events;

import execution.ExecutionWrapper;

import java.util.concurrent.CompletableFuture;

public class TestEventDispatcher extends DefaultEventDispatcher {
    private static final ExecutionWrapper ASYNC = function -> CompletableFuture.runAsync(function::execute);

    public TestEventDispatcher() {
        super(TestEventDispatcher.class.getSimpleName(), ASYNC);
    }


}
