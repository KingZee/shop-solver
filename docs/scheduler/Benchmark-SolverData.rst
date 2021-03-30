.. java:import:: javafx.concurrent Service

.. java:import:: javafx.concurrent Task

.. java:import:: java.lang.management ManagementFactory

.. java:import:: java.lang.management MemoryMXBean

.. java:import:: java.lang.management ThreadInfo

.. java:import:: java.lang.management ThreadMXBean

.. java:import:: java.util List

.. java:import:: java.util.concurrent ThreadLocalRandom

Benchmark.SolverData
====================

.. java:package:: scheduler
   :noindex:

.. java:type:: static class SolverData
   :outertype: Benchmark

   Local class to represent benchmark information about each Solver instance

Constructors
------------
SolverData
^^^^^^^^^^

.. java:constructor::  SolverData(Class<? extends Solver> solver)
   :outertype: Benchmark.SolverData

Methods
-------
addDataSet
^^^^^^^^^^

.. java:method::  void addDataSet(BenchmarkData data)
   :outertype: Benchmark.SolverData

getAlgorithm
^^^^^^^^^^^^

.. java:method::  Class<? extends Solver> getAlgorithm()
   :outertype: Benchmark.SolverData

getBestTime
^^^^^^^^^^^

.. java:method::  int getBestTime()
   :outertype: Benchmark.SolverData

getCpuAverage
^^^^^^^^^^^^^

.. java:method::  long getCpuAverage()
   :outertype: Benchmark.SolverData

getCpuMax
^^^^^^^^^

.. java:method::  long getCpuMax()
   :outertype: Benchmark.SolverData

getCpuMin
^^^^^^^^^

.. java:method::  long getCpuMin()
   :outertype: Benchmark.SolverData

getMemoryAverage
^^^^^^^^^^^^^^^^

.. java:method::  long getMemoryAverage()
   :outertype: Benchmark.SolverData

getMemoryMax
^^^^^^^^^^^^

.. java:method::  long getMemoryMax()
   :outertype: Benchmark.SolverData

getMemoryMin
^^^^^^^^^^^^

.. java:method::  long getMemoryMin()
   :outertype: Benchmark.SolverData

getTimeAverage
^^^^^^^^^^^^^^

.. java:method::  float getTimeAverage()
   :outertype: Benchmark.SolverData

getWorstTime
^^^^^^^^^^^^

.. java:method::  int getWorstTime()
   :outertype: Benchmark.SolverData

