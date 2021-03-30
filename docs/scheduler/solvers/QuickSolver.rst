.. java:import:: scheduler Problem

.. java:import:: scheduler Schedule

.. java:import:: scheduler ShopType

.. java:import:: scheduler Solver

.. java:import:: java.util ArrayList

.. java:import:: java.util Comparator

.. java:import:: java.util List

QuickSolver
===========

.. java:package:: scheduler.solvers
   :noindex:

.. java:type:: public class QuickSolver extends Solver

   A solver implementation that solves problems heuristically

Constructors
------------
QuickSolver
^^^^^^^^^^^

.. java:constructor:: public QuickSolver(Problem prb)
   :outertype: QuickSolver

Methods
-------
solveMakespan
^^^^^^^^^^^^^

.. java:method:: @Override protected List<Schedule> solveMakespan()
   :outertype: QuickSolver

