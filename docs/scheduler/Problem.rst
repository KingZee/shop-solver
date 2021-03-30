.. java:import:: java.util Random

.. java:import:: java.util.concurrent ThreadLocalRandom

.. java:import:: java.util.stream IntStream

Problem
=======

.. java:package:: scheduler
   :noindex:

.. java:type:: public class Problem

   Problem class contains all information about a scheduling problem instance

Fields
------
jobCount
^^^^^^^^

.. java:field:: public int jobCount
   :outertype: Problem

machineCount
^^^^^^^^^^^^

.. java:field:: public int machineCount
   :outertype: Problem

Constructors
------------
Problem
^^^^^^^

.. java:constructor:: public Problem()
   :outertype: Problem

Problem
^^^^^^^

.. java:constructor:: public Problem(int jobs, int machines)
   :outertype: Problem

   Empty constructor with a specific size, to generate a full problem, use the other constructor

   :param jobs: size of jobs
   :param machines: size of machines

   **See also:** :java:ref:`.Problem(int,int,ShopType,Point,double)`

Problem
^^^^^^^

.. java:constructor:: public Problem(int jobs, int machines, ShopType type, Point timeInterval, double zeroChance)
   :outertype: Problem

   Constructor to generate a full scheduling problem

   :param jobs: size of jobs
   :param machines: size of machines
   :param type: Job type
   :param timeInterval: Interval of processing times
   :param zeroChance: Chance of an operation to be empty

   **See also:** :java:ref:`ShopType`

Methods
-------
getMachineMatrix
^^^^^^^^^^^^^^^^

.. java:method:: public int[][] getMachineMatrix()
   :outertype: Problem

getTimeMatrix
^^^^^^^^^^^^^

.. java:method:: public int[][] getTimeMatrix()
   :outertype: Problem

getType
^^^^^^^

.. java:method:: public ShopType getType()
   :outertype: Problem

setType
^^^^^^^

.. java:method:: public void setType(ShopType type)
   :outertype: Problem

updateMachine
^^^^^^^^^^^^^

.. java:method:: public void updateMachine(int rowIndex, int colIndex, int value)
   :outertype: Problem

   Update machine order matrix

   :param rowIndex: Job Index
   :param colIndex: Machine Index
   :param value: Order value

updateTime
^^^^^^^^^^

.. java:method:: public void updateTime(int rowIndex, int colIndex, int value)
   :outertype: Problem

   Update processing time of a specific operation

   :param rowIndex: Job Index
   :param colIndex: Machine Index
   :param value: New Processing time value

