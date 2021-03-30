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

ControllerTest.MatrixTest
=========================

.. java:package:: scheduler
   :noindex:

.. java:type:: @Nested @TestInstance @DisplayName  class MatrixTest
   :outertype: ControllerTest

Fields
------
controller
^^^^^^^^^^

.. java:field::  Controller controller
   :outertype: ControllerTest.MatrixTest

robot
^^^^^

.. java:field::  FxRobot robot
   :outertype: ControllerTest.MatrixTest

stage
^^^^^

.. java:field::  Stage stage
   :outertype: ControllerTest.MatrixTest

Methods
-------
cleanup
^^^^^^^

.. java:method:: @AfterAll  void cleanup()
   :outertype: ControllerTest.MatrixTest

clear_non_integer_input
^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @Test  void clear_non_integer_input()
   :outertype: ControllerTest.MatrixTest

prepare
^^^^^^^

.. java:method:: @BeforeEach  void prepare()
   :outertype: ControllerTest.MatrixTest

setup
^^^^^

.. java:method:: @BeforeAll  void setup() throws Exception
   :outertype: ControllerTest.MatrixTest

solve_empty_matrix
^^^^^^^^^^^^^^^^^^

.. java:method:: @Test  void solve_empty_matrix()
   :outertype: ControllerTest.MatrixTest

solve_google_example
^^^^^^^^^^^^^^^^^^^^

.. java:method:: @Test  void solve_google_example()
   :outertype: ControllerTest.MatrixTest

solve_random_matrix
^^^^^^^^^^^^^^^^^^^

.. java:method:: @Test  void solve_random_matrix()
   :outertype: ControllerTest.MatrixTest

