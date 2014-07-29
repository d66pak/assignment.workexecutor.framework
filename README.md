assignment.workexecutor.framework
=================================

assignment work to develop a simple work item executor framework in java 1.7

DESIGN
======

This work item executor framework is designed to support various kinds of work items. Single work item can be either
executed directly by creating specific WorkItem instance and calling execute() method or use the WorkItemExecutor for
asynchronous WorkItem execution.

NOTE: Please read through the class/method specific javadoc comments to understand the design better.

A brief summary about the various classes:

+ WorkItem interface -> Interface for creating different types of work items

+ AbstractWorkItem -> Skeletal implementation of WorkItem interface. All the logic to execute a WorkItem using n threads
is implemented in this abstract class. To support execution of a particular work item, it is required to extend
this class and implement method solveTask() which is responsible for solving a single sub task.
Internally, this class uses ExecutorService and CompletionService to distribute the WorkItem among n threads and wait
for their completion. Same number of threads are maintained by using RetriableTask to retry a failed sub task for given
number of times. CompletionService is used so that results of sub task could be aggregated as soon as any of the sub
task has completed execution. Once all the sub tasks have finished execution, their results are aggregated and can could
be accessed via getResults() method. Additionally, callback method is called to indicate the completion of WorkItem.

+ NumberSquareWorkItem -> Specific implementation of WorkItem to find square of each number

+ RetriableTask -> A wrapper class for Callable that adds retry functionality

+ WorkItemCompletionCallback interface -> Callback interface for indicating that work item execution has completed

+ WorkItemExecutor interface -> Interface defining the framework responsible for executing work items concurrently

+ ParallelWorkItemExecutor -> Specific implementation of WorkItemExecutor. This class accepts work items and executes
them concurrently using ExecutorFramework

+ WorkItemFactory -> Factory class for creating various types of work items

TESTING
=======

TestNG framework is used for testing this solution. It would be easy to understand the solution by analysing the test cases.

NOTE:

+ Test cases are written for NumberSquareWorkItem and ParallelWorkItemExecutor
+ Test cases for straight forward classes are not added

BUILD
=====

+ Apache Maven is used as a project management/build tool
+ Use 'mvn compile test' command to compile and run the tests
+ Build and tested with JDK 1.6, 1.7 on Mac OS X

ASSUMPTIONS
===========

+ Framework for executing WorkItem depends on the type of task provided for execution. In the problem statement there is
no description about the type of task that work item encapsulates. So, assumption is made that each sub task is
independent of each other.