.. java:import:: javafx.concurrent Service

.. java:import:: javafx.concurrent Task

.. java:import:: java.util ArrayList

.. java:import:: java.util List

.. java:import:: java.util Timer

.. java:import:: java.util TimerTask

Solver.JobData
==============

.. java:package:: scheduler
   :noindex:

.. java:type:: protected static class JobData
   :outertype: Solver

   Local class to represent information about the current schedule list

Constructors
------------
JobData
^^^^^^^

.. java:constructor::  JobData(int best, int worst, double avg, Schedule bestSchedule, Schedule worstSchedule, int totalSchedules)
   :outertype: Solver.JobData

Methods
-------
getAvgTime
^^^^^^^^^^

.. java:method::  double getAvgTime()
   :outertype: Solver.JobData

getBestSchedule
^^^^^^^^^^^^^^^

.. java:method:: public Schedule getBestSchedule()
   :outertype: Solver.JobData

getBestTime
^^^^^^^^^^^

.. java:method::  int getBestTime()
   :outertype: Solver.JobData

getTotalSchedules
^^^^^^^^^^^^^^^^^

.. java:method::  int getTotalSchedules()
   :outertype: Solver.JobData

getWorstSchedule
^^^^^^^^^^^^^^^^

.. java:method:: public Schedule getWorstSchedule()
   :outertype: Solver.JobData

getWorstTime
^^^^^^^^^^^^

.. java:method::  int getWorstTime()
   :outertype: Solver.JobData

