.. java:import:: com.google.common.collect Lists

.. java:import:: scheduler Problem

.. java:import:: scheduler Schedule

.. java:import:: scheduler ShopType

.. java:import:: scheduler Solver

.. java:import:: java.util ArrayList

.. java:import:: java.util List

ExactSolver
===========

.. java:package:: scheduler.solvers
   :noindex:

.. java:type:: public class ExactSolver extends Solver

   An exact solver that iterates through each problem's solution space

Constructors
------------
ExactSolver
^^^^^^^^^^^

.. java:constructor:: public ExactSolver(Problem prb)
   :outertype: ExactSolver

Methods
-------
solveMakespan
^^^^^^^^^^^^^

.. java:method:: @Override protected List<Schedule> solveMakespan()
   :outertype: ExactSolver

