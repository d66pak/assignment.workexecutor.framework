package assignment.workexecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * <p>Skeletal implementation of WorkItem interface.
 * Breaks the WorkItem into individual sub tasks and executes using a CompletionService, employing
 * specified amount of threads.
 * Wraps the callable into RetiableTask which retries the individual task specified number of times
 * before considering as failed
 *
 * <p>NOTE: Since CompletionService is used results are not in the same order individual sub task submission
 *
 * @param <T> Type of individual task
 * @param <V> Type of result returned by each task
 *
 * @author Deepak Telkar
 * Created by dtelkar on 7/28/14.
 */
public abstract class AbstractWorkItem<T,V> implements WorkItem<T,V> {

    // Task list
    protected List<T> mTaskList;

    // Result list
    protected List<V> mResult;

    /**
     * Subclasses must implement this method to provide Callable instance
     * to solve individual task
     *
     * @param task Individual task
     *
     * @return Callable instance which processes individual task
     */
    protected abstract Callable<V> solveTask(T task);

    /**
     * Subclasses must implement this method to provide the max number of times
     * each sub task must be retried
     *
     * @return max retry count
     */
    protected abstract int retryCount();


    @Override
    public List<V> getResults() {

        return mResult;
    }

    @Override
    public void execute(int parallelism, WorkItemCompletionCallback callback) {

        // Create Executor service to solve the work item
        ExecutorService executor = Executors.newFixedThreadPool(parallelism);

        // Use completion service to submit the task
        CompletionService<V> completionService = new ExecutorCompletionService<V>(executor);

        // Submit each task to executor service
        for (T task : mTaskList) {

            // Wrap the normal callable into retriable task
            RetriableTask<V> rt = new RetriableTask<V>(retryCount(), solveTask(task));
            completionService.submit(rt);
        }

        // Create result list
        mResult = new ArrayList<V>(mTaskList.size());

        // Wait until all the sub tasks have completed
        for (int i = 0; i < mTaskList.size(); ++i) {

            try {
                mResult.add(completionService.take().get());
            } catch (ExecutionException e) {
                System.out.println("Sub task encountered an exception, even after retrying...");
                mResult.add(null);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Shutdown the service
        executor.shutdown();

        // All the sub tasks have completed, call the callback
        callback.complete();
    }
}
