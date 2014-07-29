package assignment.workexecutor;

/**
 * <p>Interface for Work Item Executor that is responsible for executing WorkItems concurrently
 *
 * @author Deepak Telkar
 * Created by dtelkar on 7/28/14.
 */
public interface WorkItemExecutor {

    /**
     * Method to execute work item
     *
     * @param workItem WorkItem to be executed
     * @param parallelism Number of threads to be employed for executing work item
     * @param callback Callback that must be called once work item execution has completed
     */
    void executeWorkItem(final WorkItem<?, ?> workItem, final int parallelism, final WorkItemCompletionCallback callback);

    /**
     * Method to shutdown the framework
     * NOTE: Must be called once the framework is no longer required.
     */
    void shutdown();

}
