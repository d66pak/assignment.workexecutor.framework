package assignment.workexecutor;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by dtelkar on 7/28/14.
 */
public class TestParallelWorkItemExecutor {

    @Test
    public void testExecuteWorkItem() {

        // Create work item executor
        WorkItemExecutor wie = new ParallelWorkItemExecutor();

        // Create work list
        final List<Integer> numberList = new ArrayList<Integer>(Runtime.getRuntime().availableProcessors());
        for (int i = 0; i < 10000; ++i) {
            numberList.add(i);
        }

        final long startTime = System.currentTimeMillis();

        // Create work item
        final WorkItem<Integer, Integer> wi = WorkItemFactory.createWorkItem(WorkItemFactory.WorkItemType.NUMBER_SQUARE_WI, numberList);

        // Submit work item to work item executor framework
        wie.executeWorkItem(wi, 10, new WorkItemCompletionCallback() {
            @Override
            public void complete() {
                System.out.println("NumberSquare WorkItem executed by WorkItemExecutor in : "
                        + (System.currentTimeMillis() - startTime));

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

        System.out.println("Single workitem completed in : " + (System.currentTimeMillis() - startTime));
    }

    @Test
    public void testMultipleWorkItemExecution() {

        final int workItems = 1000;
        final int subWorkItems = 10000;


        // Create multiple work lists
        Random random = new Random();
        List<List<Integer>> workLists = new ArrayList<List<Integer>>(workItems);
        for (int i = 0; i < workItems; ++i) {

            List<Integer> workList = new ArrayList<Integer>(subWorkItems);

            for (int j = 0; j < subWorkItems; ++j) {

                workList.add(random.nextInt(100));
            }

            workLists.add(workList);
        }

        final long startTime = System.currentTimeMillis();
        // Create work item executor
        WorkItemExecutor wie = new ParallelWorkItemExecutor();

        // Create multiple work items and submit them to work item executor framework
        for (int i = 0; i < workItems; ++i) {

            final WorkItem<Integer, Integer> wi =
                    WorkItemFactory.createWorkItem(WorkItemFactory.WorkItemType.NUMBER_SQUARE_WI,
                            workLists.get(i));
            wie.executeWorkItem(wi, Runtime.getRuntime().availableProcessors(),
                    new WorkItemCompletionCallback() {
                        @Override
                        public void complete() {
                            // do nothing
                        }
                    });
        }

        // shutdown the framework
        wie.shutdown();
        System.out.println("WorkItems : " + workItems + " subWorkItems : " + subWorkItems
        + " took : " + (System.currentTimeMillis() - startTime));
    }
}
