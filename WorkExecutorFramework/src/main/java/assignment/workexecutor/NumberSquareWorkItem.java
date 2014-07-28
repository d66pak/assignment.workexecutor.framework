package assignment.workexecutor;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Concrete implementation of WorkItem to find square of each number
 *
 * @author Deepak Telkar
 * Created by dtelkar on 7/28/14.
 */
public class NumberSquareWorkItem extends AbstractWorkItem<Integer,Integer> {

    public NumberSquareWorkItem(List<Integer> numberList) {

        mTaskList = numberList;
    }

    @Override
    protected int retryCount() {
        return 5;
    }

    @Override
    protected Callable<Integer> solveTask(final Integer task) {
        return new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return task * task;
            }
        };
    }
}
