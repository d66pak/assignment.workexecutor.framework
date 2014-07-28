package assignment.workexecutor;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>Concrete implementation of WorkItem interface
 * Uses ExecutorService to execute individual WorkItem
 *
 * @author Deepak Telkar
 * Created by dtelkar on 7/28/14.
 */
public class ParallelWorkItemExecutor implements WorkItemExecutor {

    private final ExecutorService mExecutorService;

    public ParallelWorkItemExecutor() {

        mExecutorService = Executors.newCachedThreadPool();
    }

    @Override
    public void executeWorkItem(final WorkItem<?, ?> workItem, final int parallelism, final WorkItemCompletionCallback callback) {

        // Submit work item to executor service for execution
        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {

                workItem.execute(parallelism, callback);
            }
        });
    }

    @Override
    public void shutdown() {

        mExecutorService.shutdown();
    }
}
