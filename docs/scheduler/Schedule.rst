.. java:import:: java.util ArrayList

.. java:import:: java.util Collection

.. java:import:: java.util HashMap

.. java:import:: java.util List

.. java:import:: java.util.function BiConsumer

Schedule
========

.. java:package:: scheduler
   :noindex:

.. java:type:: public class Schedule extends HashMap<Point, Integer>

   A schedule is a special permutation schedule of operations It's stored as a HashMap of Points ( x : Job index, y : Machine index) A local list variable is added to store the scheduling

Constructors
------------
Schedule
^^^^^^^^

.. java:constructor:: public Schedule(Schedule m)
   :outertype: Schedule

   Create a copy of an existing schedule

   :param m: Schedule to copy

Schedule
^^^^^^^^

.. java:constructor:: public Schedule()
   :outertype: Schedule

Methods
-------
concat
^^^^^^

.. java:method:: public static Schedule concat(Collection<? extends Schedule> list)
   :outertype: Schedule

   Concatenates two or more schedule instances into a single one merging them by order from left to right

   :param list: List of schedules to concatenate
   :return: Single concatenated result

forEach
^^^^^^^

.. java:method:: @Override public void forEach(BiConsumer<? super Point, ? super Integer> action)
   :outertype: Schedule

   Iterates map by order of schedule, returning key & value

getByIndex
^^^^^^^^^^

.. java:method:: public Integer getByIndex(Integer i)
   :outertype: Schedule

   Fetch a job by its index in the schedule

   :param i: Index of job to get
   :return: Point representation of the job

getIndices
^^^^^^^^^^

.. java:method:: public List<Point> getIndices()
   :outertype: Schedule

   Get the order of jobs

   :return: a list of ordered jobs

getMaxValue
^^^^^^^^^^^

.. java:method:: public Integer getMaxValue()
   :outertype: Schedule

   Returns the largest processing time of this schedule instance

   :return: The largest processing time of a schedule

getPrecedentJob
^^^^^^^^^^^^^^^

.. java:method:: public Point getPrecedentJob(Point job)
   :outertype: Schedule

   Returns the previous operation of the same job

   :param job: A Job represented as Point(n,m)
   :return: The previous job Point(n,m?). If it is not found, it tries to find the closest job, or null.

getPreviousJob
^^^^^^^^^^^^^^

.. java:method:: public Point getPreviousJob(Point job)
   :outertype: Schedule

   Returns the previous operation on the same machine

   :param job: A Job represented as Point(n,m)
   :return: The job being executed before this one, job Point(n?,m). If it is not found, it tries to find the closest job, or returns null.

getPreviousTime
^^^^^^^^^^^^^^^

.. java:method:: public int getPreviousTime(Point job)
   :outertype: Schedule

   Returns the processing time from a job's precedent/previous blocking task Useful for computing makespan

   :param job: Job (jobIndex, machineIndex)
   :return: Processing time of preceding blocking task

put
^^^

.. java:method:: @Override public Integer put(Point key, Integer value)
   :outertype: Schedule

   Puts the specific job at the end of the queue if it doesn't exist, or replace its value

   :param key: Point to specify (Job-index,Machine-index)
   :param value: Processing time of specified job
   :return: the previous value associated with the key, or null if there was no mapping

putAll
^^^^^^

.. java:method:: public void putAll(Schedule map)
   :outertype: Schedule

putByIndex
^^^^^^^^^^

.. java:method:: public Integer putByIndex(Integer i, Integer value)
   :outertype: Schedule

   Replace a key by specifying its index

   :param i: Index of key to replace
   :param value: Processing time to replace with
   :return: Old value that was replaced

setIndices
^^^^^^^^^^

.. java:method:: public void setIndices(List<Point> indices)
   :outertype: Schedule

   Set the order of jobs

   :param indices: new list of ordered jobs

