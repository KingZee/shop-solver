.. java:import:: com.google.common.collect BiMap

.. java:import:: com.google.common.collect HashBiMap

.. java:import:: javafx.beans Observable

.. java:import:: javafx.beans.property ReadOnlyProperty

.. java:import:: javafx.collections FXCollections

.. java:import:: javafx.concurrent WorkerStateEvent

.. java:import:: javafx.event ActionEvent

.. java:import:: javafx.event Event

.. java:import:: javafx.event EventHandler

.. java:import:: javafx.fxml FXML

.. java:import:: javafx.geometry HPos

.. java:import:: javafx.geometry Insets

.. java:import:: javafx.geometry Pos

.. java:import:: javafx.geometry VPos

.. java:import:: javafx.scene Node

.. java:import:: javafx.scene.chart CategoryAxis

.. java:import:: javafx.scene.chart NumberAxis

.. java:import:: javafx.scene.chart XYChart

.. java:import:: javafx.scene.control Button

.. java:import:: javafx.scene.control Label

.. java:import:: javafx.scene.control TextField

.. java:import:: javafx.scene.shape Circle

.. java:import:: javafx.scene.text Text

.. java:import:: javafx.scene.text TextFlow

.. java:import:: javafx.stage Stage

.. java:import:: org.apache.commons.math3.exception NullArgumentException

.. java:import:: scheduler.controller JobChart

.. java:import:: scheduler.solvers ExactSolver

.. java:import:: scheduler.solvers QuickSolver

.. java:import:: java.text DecimalFormat

.. java:import:: java.util ArrayList

.. java:import:: java.util Iterator

.. java:import:: java.util List

.. java:import:: java.util Map

Controller
==========

.. java:package:: scheduler
   :noindex:

.. java:type:: public class Controller

   Main Controller class for binding UI elements to methods & variables

Fields
------
algorithmButtonList
^^^^^^^^^^^^^^^^^^^

.. java:field:: public List<JFXRadioButton> algorithmButtonList
   :outertype: Controller

algorithmSettings
^^^^^^^^^^^^^^^^^

.. java:field:: @FXML public VBox algorithmSettings
   :outertype: Controller

algorithmToggleGroup
^^^^^^^^^^^^^^^^^^^^

.. java:field:: @FXML public ToggleGroup algorithmToggleGroup
   :outertype: Controller

benchJobs
^^^^^^^^^

.. java:field:: @FXML public TextField benchJobs
   :outertype: Controller

benchMatrix
^^^^^^^^^^^

.. java:field:: @FXML public VBox benchMatrix
   :outertype: Controller

benchRuns
^^^^^^^^^

.. java:field:: @FXML public HBox benchRuns
   :outertype: Controller

benchmarkCheckbox
^^^^^^^^^^^^^^^^^

.. java:field:: @FXML public JFXCheckBox benchmarkCheckbox
   :outertype: Controller

calculateButton
^^^^^^^^^^^^^^^

.. java:field:: @FXML public JFXButton calculateButton
   :outertype: Controller

clearMatrix
^^^^^^^^^^^

.. java:field:: @FXML public JFXButton clearMatrix
   :outertype: Controller

closeButton
^^^^^^^^^^^

.. java:field:: @FXML public Button closeButton
   :outertype: Controller

generateMatrix
^^^^^^^^^^^^^^

.. java:field:: @FXML public JFXButton generateMatrix
   :outertype: Controller

leftInterval
^^^^^^^^^^^^

.. java:field:: @FXML public JFXTextField leftInterval
   :outertype: Controller

leftShopType
^^^^^^^^^^^^

.. java:field:: @FXML public JFXButton leftShopType
   :outertype: Controller

main
^^^^

.. java:field:: @FXML public HBox main
   :outertype: Controller

menu
^^^^

.. java:field:: @FXML public VBox menu
   :outertype: Controller

miniButton
^^^^^^^^^^

.. java:field:: @FXML public Button miniButton
   :outertype: Controller

outputContainer
^^^^^^^^^^^^^^^

.. java:field:: @FXML public VBox outputContainer
   :outertype: Controller

outputPane
^^^^^^^^^^

.. java:field:: @FXML public TitledPane outputPane
   :outertype: Controller

problemGrid
^^^^^^^^^^^

.. java:field:: @FXML public GridPane problemGrid
   :outertype: Controller

problemPane
^^^^^^^^^^^

.. java:field:: @FXML public TitledPane problemPane
   :outertype: Controller

resetMatrix
^^^^^^^^^^^

.. java:field:: @FXML public JFXButton resetMatrix
   :outertype: Controller

rightInterval
^^^^^^^^^^^^^

.. java:field:: @FXML public JFXTextField rightInterval
   :outertype: Controller

rightShopType
^^^^^^^^^^^^^

.. java:field:: @FXML public JFXButton rightShopType
   :outertype: Controller

shopType
^^^^^^^^

.. java:field:: @FXML public JFXTextField shopType
   :outertype: Controller

titleBar
^^^^^^^^

.. java:field:: @FXML public HBox titleBar
   :outertype: Controller

zeroPercent
^^^^^^^^^^^

.. java:field:: @FXML public JFXSlider zeroPercent
   :outertype: Controller

