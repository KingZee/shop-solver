.. java:import:: java.lang.management ThreadInfo

BenchmarkData
=============

.. java:package:: scheduler
   :noindex:

.. java:type:: public class BenchmarkData

   Class that holds benchmark information for a single problem instance It is public as a benchmark is multiple problem instances for multiple algorithms.

   **See also:** :java:ref:`Benchmark.SolverData`

Methods
-------
getCpuUsage
^^^^^^^^^^^

.. java:method::  Long getCpuUsage()
   :outertype: BenchmarkData

getMemoryUsage
^^^^^^^^^^^^^^

.. java:method::  Long getMemoryUsage()
   :outertype: BenchmarkData

getProblem
^^^^^^^^^^

.. java:method::  Problem getProblem()
   :outertype: BenchmarkData

getSolution
^^^^^^^^^^^

.. java:method::  Solver.JobData getSolution()
   :outertype: BenchmarkData

getThreadData
^^^^^^^^^^^^^

.. java:method::  ThreadInfo getThreadData()
   :outertype: BenchmarkData

setDataSet
^^^^^^^^^^

.. java:method::  void setDataSet(Problem prb, Solver.JobData sol, Long cpuTime, Long memoryUsed, ThreadInfo threadData)
   :outertype: BenchmarkData

