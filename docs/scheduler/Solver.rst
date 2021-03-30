.. java:import:: javafx.concurrent Service

.. java:import:: javafx.concurrent Task

.. java:import:: java.util ArrayList

.. java:import:: java.util List

.. java:import:: java.util Timer

.. java:import:: java.util TimerTask

Solver
======

.. java:package:: scheduler
   :noindex:

.. java:type:: public abstract class Solver extends Service<List<Schedule>>

   Abstract class to inherit from when creating a new Solver type It has helper functions to permute, combine, and parse schedules It inherits from Service to be able to solve the problem in the background

Constructors
------------
Solver
^^^^^^

.. java:constructor:: public Solver()
   :outertype: Solver

Solver
^^^^^^

.. java:constructor:: public Solver(Problem prb)
   :outertype: Solver

Methods
-------
combine
^^^^^^^

.. java:method:: public static List<List<int[]>> combine(List<int[]>... sets)
   :outertype: Solver

   Function to generate cartesian array combinations

   :param sets: Set of arrays to combine
   :return: Cartesian multiplication of arrays

createTask
^^^^^^^^^^

.. java:method:: @Override protected Task<List<Schedule>> createTask()
   :outertype: Solver

   This creates the task that encapsulates the solver function

   :return: task that will resolve with the solved problem if succeeded

getProblem
^^^^^^^^^^

.. java:method:: public final Problem getProblem()
   :outertype: Solver

parseSchedules
^^^^^^^^^^^^^^

.. java:method:: public static JobData parseSchedules(List<Schedule> schedules)
   :outertype: Solver

   Parses a list of schedules, and returns relevant statistics

   :param schedules: List of schedules (solution space)
   :return: JobData structure

permute
^^^^^^^

.. java:method:: public static List<Schedule> permute(Schedule map, int n)
   :outertype: Solver

   Heap's algorithm to generate all permutations of a Schedule

   :param map: A single Schedule to generate permutations for
   :param n: Length of the Schedule
   :return: List of permuted schedules

permuteSubset
^^^^^^^^^^^^^

.. java:method:: public static List<Schedule> permuteSubset(Schedule map, int jobIndex)
   :outertype: Solver

   Permute operations of a specific job within a schedule

   :param map: A single Schedule to generate permutations for
   :param jobIndex: Index of the job to permute operations for
   :return: List of permuted schedules

setProblem
^^^^^^^^^^

.. java:method:: public final void setProblem(Problem problem)
   :outertype: Solver

solveMakespan
^^^^^^^^^^^^^

.. java:method:: protected abstract List<Schedule> solveMakespan()
   :outertype: Solver

   This is the main function used to solve problem instances It is abstract and only usable from children This method is forcefully stopped on user cancel, while memory will be freed once that happens this method should not lock or operate on any objects declared outside of its scope

   :return: A List of Schedules, representing the solution space

   **See also:** :java:ref:`Schedule`

