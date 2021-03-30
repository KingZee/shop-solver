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

ControllerTest.GUITests
=======================

.. java:package:: scheduler
   :noindex:

.. java:type:: @Nested @TestInstance @DisplayName  class GUITests
   :outertype: ControllerTest

Fields
------
controller
^^^^^^^^^^

.. java:field::  Controller controller
   :outertype: ControllerTest.GUITests

robot
^^^^^

.. java:field::  FxRobot robot
   :outertype: ControllerTest.GUITests

stage
^^^^^

.. java:field::  Stage stage
   :outertype: ControllerTest.GUITests

Methods
-------
benchmark_mode_toggle
^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @Test  void benchmark_mode_toggle()
   :outertype: ControllerTest.GUITests

cleanup
^^^^^^^

.. java:method:: @AfterAll  void cleanup()
   :outertype: ControllerTest.GUITests

main_contains_elements
^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @Test  void main_contains_elements()
   :outertype: ControllerTest.GUITests

menu_contains_elements
^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @Test  void menu_contains_elements()
   :outertype: ControllerTest.GUITests

setup
^^^^^

.. java:method:: @BeforeAll  void setup() throws Exception
   :outertype: ControllerTest.GUITests

title_contains_elements
^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @Test  void title_contains_elements()
   :outertype: ControllerTest.GUITests

