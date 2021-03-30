.. java:import:: javafx.concurrent Service

.. java:import:: javafx.concurrent Task

.. java:import:: java.lang.management ManagementFactory

.. java:import:: java.lang.management MemoryMXBean

.. java:import:: java.lang.management ThreadInfo

.. java:import:: java.lang.management ThreadMXBean

.. java:import:: java.util List

.. java:import:: java.util.concurrent ThreadLocalRandom

Benchmark
=========

.. java:package:: scheduler
   :noindex:

.. java:type:: public class Benchmark extends Service<List<Benchmark.SolverData>>

   Class for Benchmarking multiple algorithms It also inherits from Service as to run in the background

Constructors
------------
Benchmark
^^^^^^^^^

.. java:constructor:: public Benchmark(int runs, int maxJobs, int maxMachines, List<Class<? extends Solver>> solvers)
   :outertype: Benchmark

Methods
-------
createTask
^^^^^^^^^^

.. java:method:: @Override protected Task<List<SolverData>> createTask()
   :outertype: Benchmark

   This creates the task that benchmarks the algorithms

   :return: task that will resolve with the benchmark if succeeded

isBenchmarkingSupported
^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public static boolean isBenchmarkingSupported()
   :outertype: Benchmark

   Boolean check to see if the machine supports accurate measuring of CPU time for any thread

   :return: boolean Does it support measuring CPU time?

   **See also:** :java:ref:`ThreadMXBean.isThreadCpuTimeSupported()`

