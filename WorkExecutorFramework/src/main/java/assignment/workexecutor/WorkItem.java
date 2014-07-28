package assignment.workexecutor;

import java.util.List;

/**
 * Interface for Work item that encapsulates the work which must be executed concurrently
 *
 * @author Deepak Telkar
 * Created by dtelkar on 7/28/14.
 */
public interface WorkItem<T,V> {

    /**
     * Method used to execute the work item
     *
     * @param parallelism Number of threads to be employed for executing work item
     * @param callback The callback which must be call once work item has completed
     */
    void execute(int parallelism, WorkItemCompletionCallback callback);

    /**
     * Method to return the list of results
     *
     * @return List of result values
     */
    List<V> getResults();

}
