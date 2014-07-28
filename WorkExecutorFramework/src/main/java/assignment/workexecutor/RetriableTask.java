package assignment.workexecutor;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;

/**
 * <p>A wrapper class for Callable that adds retry functionality.
 * call() method is overridden to retry the task for specified number of count.
 * InterruptedException and CancellationException are allowed to propogate instead of causing retries.
 *
 * @param <V> the return type of the call() method
 *
 * @author Deepak Telkar
 * Created by dtelkar on 7/28/14.
 */
public class RetriableTask<V> implements Callable<V> {

    private final int mMaxTries;
    private final Callable<V> mWrappedCallable;

    public RetriableTask(final int maxTries, final Callable<V> callable) {

        mMaxTries = maxTries;
        mWrappedCallable = callable;
    }

    /**
     * Invokes the wrapped Callable's call method and retrying specified
     * number of times if exception occurs.
     *
     * @return return value of wrapped call()
     * @throws Exception
     */
    @Override
    public V call() throws Exception {

        int triesLeft = mMaxTries;

        for(;;) {

            // Execute the callable
            try {
                return mWrappedCallable.call();
            } catch (InterruptedException e) {
                throw e;
            } catch (CancellationException e) {
                throw e;
            } catch (Exception e) {

                --triesLeft;
                // Check if we can resubmit the task
                if (triesLeft <= 0) {

                    // Maximum number of attempts over
                    throw e;
                }
                System.out.println("Warning: Caught exception, retrying...");
            }

        }
    }
}
