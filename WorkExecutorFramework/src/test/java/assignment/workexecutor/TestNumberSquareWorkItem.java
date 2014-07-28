package assignment.workexecutor;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dtelkar on 7/28/14.
 */
public class TestNumberSquareWorkItem {

    @Test
    public void testNumberSquareWorkItem() {


        List<Integer> numberList = new ArrayList<Integer>(10);
        for (int i = 0; i < 10000; ++i) {
            numberList.add(i);
        }

        WorkItem<Integer, Integer> wi = WorkItemFactory.createWorkItem(WorkItemFactory.WorkItemType.NUMBER_SQUARE_WI, numberList);


        wi.execute(10, new WorkItemCompletionCallback() {
            @Override
            public void complete() {
                System.out.println("NumberSquare WorkItem completed!");
            }
        });

        List<Integer> resultList = wi.getResults();

        Assert.assertEquals(numberList.size(), resultList.size());
        for (int num : numberList) {

            Assert.assertTrue(resultList.contains(num * num));
        }

    }
}
