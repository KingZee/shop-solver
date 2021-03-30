.. java:import:: com.jfoenix.controls JFXButton

.. java:import:: com.jfoenix.controls JFXRadioButton

.. java:import:: javafx.fxml FXMLLoader

.. java:import:: javafx.geometry Rectangle2D

.. java:import:: javafx.scene Node

.. java:import:: javafx.scene Parent

.. java:import:: javafx.scene Scene

.. java:import:: javafx.scene.control CheckBox

.. java:import:: javafx.scene.control Label

.. java:import:: javafx.scene.control TextField

.. java:import:: javafx.scene.paint Color

.. java:import:: javafx.scene.text Text

.. java:import:: javafx.scene.text TextFlow

.. java:import:: javafx.stage Screen

.. java:import:: javafx.stage Stage

.. java:import:: org.junit.jupiter.api.extension ExtendWith

.. java:import:: org.testfx.api FxAssert

.. java:import:: org.testfx.api FxRobot

.. java:import:: org.testfx.api FxToolkit

.. java:import:: org.testfx.framework.junit5 ApplicationExtension

.. java:import:: org.testfx.matcher.base GeneralMatchers

.. java:import:: org.testfx.matcher.base NodeMatchers

.. java:import:: org.testfx.matcher.base ParentMatchers

.. java:import:: scheduler.controller JobChart

.. java:import:: java.util ArrayList

.. java:import:: java.util List

.. java:import:: java.util.concurrent ThreadLocalRandom

ControllerTest.BenchmarkTest
============================

.. java:package:: scheduler
   :noindex:

.. java:type:: @Nested @TestInstance @DisplayName  class BenchmarkTest
   :outertype: ControllerTest

Fields
------
controller
^^^^^^^^^^

.. java:field::  Controller controller
   :outertype: ControllerTest.BenchmarkTest

robot
^^^^^

.. java:field::  FxRobot robot
   :outertype: ControllerTest.BenchmarkTest

stage
^^^^^

.. java:field::  Stage stage
   :outertype: ControllerTest.BenchmarkTest

Methods
-------
benchmark_non_integer_input
^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @Test  void benchmark_non_integer_input()
   :outertype: ControllerTest.BenchmarkTest

benchmark_none_selected
^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @Test  void benchmark_none_selected()
   :outertype: ControllerTest.BenchmarkTest

benchmark_returns_correct_output
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @Test  void benchmark_returns_correct_output()
   :outertype: ControllerTest.BenchmarkTest

cleanup
^^^^^^^

.. java:method:: @AfterEach  void cleanup()
   :outertype: ControllerTest.BenchmarkTest

setup
^^^^^

.. java:method:: @BeforeEach  void setup() throws Exception
   :outertype: ControllerTest.BenchmarkTest

