package assignment.workexecutor;

/**
 * Callback interface for indicating that work item execution is completed
 *
 * @author Deepak Telkar
 * Created by dtelkar on 7/28/14.
 */
public interface WorkItemCompletionCallback {

    /**
     * Method called when work item execution has completed
     */
    void complete();
}
