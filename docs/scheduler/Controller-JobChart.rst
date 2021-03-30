.. java:import:: javafx.beans NamedArg

.. java:import:: javafx.collections FXCollections

.. java:import:: javafx.collections ObservableList

.. java:import:: javafx.scene Node

.. java:import:: javafx.scene.chart Axis

.. java:import:: javafx.scene.chart CategoryAxis

.. java:import:: javafx.scene.chart NumberAxis

.. java:import:: javafx.scene.chart XYChart

.. java:import:: javafx.scene.layout StackPane

.. java:import:: javafx.scene.shape Rectangle

.. java:import:: scheduler Problem

.. java:import:: scheduler Schedule

.. java:import:: scheduler ShopType

.. java:import:: java.util ArrayList

.. java:import:: java.util Iterator

.. java:import:: java.util List

Controller.JobChart
===================

.. java:package:: scheduler
   :noindex:

.. java:type:: public class JobChart<X, Y> extends XYChart<X, Y>

   Custom extension of JavaFX Chart class to be able to display jobs/machines in a well formatted graph

   :param <X>: x-Axis type
   :param <Y>: y-Axis type

Constructors
------------
JobChart
^^^^^^^^

.. java:constructor:: public JobChart(Axis<X> xAxis, Axis<Y> yAxis)
   :outertype: JobChart

JobChart
^^^^^^^^

.. java:constructor:: public JobChart(Axis<X> xAxis, Axis<Y> yAxis, ObservableList<Series<X, Y>> data)
   :outertype: JobChart

Methods
-------
MapToChart
^^^^^^^^^^

.. java:method:: public static List<Series<Number, String>> MapToChart(Schedule schedule, Problem problem)
   :outertype: JobChart

   Method that parses a Schedule & problem instances, and returns a Series instance with coordinates used to draw the chart

   :param schedule: Schedule containing a single solution to render
   :param problem: Problem containing all initial processing times
   :return: A list of Series to render for each machine

dataItemAdded
^^^^^^^^^^^^^

.. java:method:: @Override protected void dataItemAdded(Series series, int itemIndex, Data item)
   :outertype: JobChart

   Called when a data item has been added.

dataItemChanged
^^^^^^^^^^^^^^^

.. java:method:: @Override protected void dataItemChanged(Data item)
   :outertype: JobChart

   Called when a data item has changed, ie its xValue, yValue or extraValue has changed.

   :param item: The data item who was changed

dataItemRemoved
^^^^^^^^^^^^^^^

.. java:method:: @Override protected void dataItemRemoved(Data item, Series series)
   :outertype: JobChart

   Called when a data item has been removed.

layoutPlotChildren
^^^^^^^^^^^^^^^^^^

.. java:method:: @Override protected void layoutPlotChildren()
   :outertype: JobChart

   Called before rendering when the class is instantiated.

seriesAdded
^^^^^^^^^^^

.. java:method:: @Override protected void seriesAdded(Series series, int seriesIndex)
   :outertype: JobChart

   Called when a full series has been added.

seriesRemoved
^^^^^^^^^^^^^

.. java:method:: @Override protected void seriesRemoved(Series series)
   :outertype: JobChart

   Called when a full series has been removed.

