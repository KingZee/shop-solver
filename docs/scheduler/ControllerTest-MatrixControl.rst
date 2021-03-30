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

ControllerTest.MatrixControl
============================

.. java:package:: scheduler
   :noindex:

.. java:type:: @Nested @TestInstance @DisplayName  class MatrixControl
   :outertype: ControllerTest

Fields
------
controller
^^^^^^^^^^

.. java:field::  Controller controller
   :outertype: ControllerTest.MatrixControl

robot
^^^^^

.. java:field::  FxRobot robot
   :outertype: ControllerTest.MatrixControl

stage
^^^^^

.. java:field::  Stage stage
   :outertype: ControllerTest.MatrixControl

Methods
-------
cleanup
^^^^^^^

.. java:method:: @AfterAll  void cleanup()
   :outertype: ControllerTest.MatrixControl

matrix_buttons_disappear
^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @Test  void matrix_buttons_disappear()
   :outertype: ControllerTest.MatrixControl

matrix_is_cleared
^^^^^^^^^^^^^^^^^

.. java:method:: @Test  void matrix_is_cleared()
   :outertype: ControllerTest.MatrixControl

matrix_is_generated_type_1
^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @Test  void matrix_is_generated_type_1()
   :outertype: ControllerTest.MatrixControl

matrix_is_generated_type_2
^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @Test  void matrix_is_generated_type_2()
   :outertype: ControllerTest.MatrixControl

matrix_is_generated_type_3
^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @Test  void matrix_is_generated_type_3()
   :outertype: ControllerTest.MatrixControl

matrix_size_changes
^^^^^^^^^^^^^^^^^^^

.. java:method:: @Test  void matrix_size_changes()
   :outertype: ControllerTest.MatrixControl

setup
^^^^^

.. java:method:: @BeforeAll  void setup() throws Exception
   :outertype: ControllerTest.MatrixControl

