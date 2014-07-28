package assignment.workexecutor;

import java.util.List;

/**
 * Factory for creating various WorkItems
 *
 * Created by dtelkar on 7/28/14.
 */
public class WorkItemFactory {

    /**
     * WorkItem type specifier
     */
    public enum WorkItemType {

        NUMBER_SQUARE_WI,
    }

    /**
     * Factory method to create various WorkItems
     *
     * @param wiType {@link assignment.workexecutor.WorkItemFactory.WorkItemType}
     * @param taskList List of tasks
     * @return Requested WorkItem instance
     */
    public static WorkItem createWorkItem(WorkItemType wiType, List<?> taskList) {

        WorkItem workItem = null;

        switch (wiType) {

            case NUMBER_SQUARE_WI:{

                workItem = new NumberSquareWorkItem((List<Integer>)taskList);
            }
        }

        return workItem;
    }

    // Private constructor
    private WorkItemFactory() {}

}
