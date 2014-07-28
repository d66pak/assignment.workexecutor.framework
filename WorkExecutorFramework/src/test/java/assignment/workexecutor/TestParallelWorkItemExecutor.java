package assignment.workexecutor;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dtelkar on 7/28/14.
 */
public class TestParallelWorkItemExecutor {

    @Test
    public void testExecuteWorkItem() {

        // Create work item executor
        WorkItemExecutor wie = new ParallelWorkItemExecutor();

        // Create work list
        final List<Integer> numberList = new ArrayList<Integer>(10);
        for (int i = 0; i < 10000; ++i) {
            numberList.add(i);
        }

        // Create work item
        final WorkItem<Integer, Integer> wi = WorkItemFactory.createWorkItem(WorkItemFactory.WorkItemType.NUMBER_SQUARE_WI, numberList);

        // Submit work item to work item executor framework
        wie.executeWorkItem(wi, 10, new WorkItemCompletionCallback() {
            @Override
            public void complete() {
                System.out.println("NumberSquare WorkItem executed by WorkItemExecutor!");

                // Cross check the results once the work item has completed
                List<Integer> resultList = wi.getResults();

                Assert.assertEquals(numberList.size(), resultList.size());
                for (int num : numberList) {

                    Assert.assertTrue(resultList.contains(num * num));
                }
            }
        });


        // shutdown the framework
        wie.shutdown();
    }
}
