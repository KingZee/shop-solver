package scheduler;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.jfoenix.controls.*;
import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.FXCollections;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import scheduler.controller.JobChart;
import scheduler.solvers.ExactSolver;
import scheduler.solvers.QuickSolver;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Controller {
    //Main container
    @FXML
    private HBox main;

    //Top title bar for close/minimise/title Label
    @FXML
    private javafx.scene.control.Button closeButton;
    @FXML
    private javafx.scene.control.Button miniButton;
    @FXML
    private HBox titleBar;

    //Main pane : Problem grid, and output VBox
    @FXML
    private GridPane problemGrid;
    @FXML
    private VBox outputContainer;

    //Menu buttons / slider references
    @FXML
    private JFXTextField shopType;
    @FXML
    private JFXSlider zeroPercent;
    @FXML
    private JFXTextField leftInterval;
    @FXML
    private JFXTextField rightInterval;
    @FXML
    private JFXButton calculateButton;
    @FXML
    private ToggleGroup algorithmToggleGroup;
    @FXML
    private VBox algorithmSettings;
    private List<JFXRadioButton> algorithmButtonList = new ArrayList<>();

    //Benchmark boxes
    @FXML
    private JFXCheckBox benchmarkCheckbox;
    @FXML
    private VBox benchMatrix;
    @FXML
    private HBox benchRuns;
    @FXML
    private TextField benchJobs, benchMachines;

    private List<List<Node>> nodes = new ArrayList<>();  //Keep references to GridPane by indices
    private final int initialRows = 3,
            initialColumns = 3;
    private int rows = initialRows;                //Initial row/col length for matrix on load
    private int cols = initialColumns;

    private Problem problem = new Problem(rows - 1, cols - 1);        //The instance of the problem
    private BiMap<String, Class<? extends Solver>> algorithmMap = HashBiMap.create(Map.of(
            "Quick Solver", QuickSolver.class,
            "Exact Solver", ExactSolver.class));

    private Class<? extends Solver> activeSolverType;   //Solver currently in use
    private List<Class<? extends Solver>> benchSolverTypes = new ArrayList<>(); //Solvers selected for benchmarking

    @FXML
    public void initialize() {
        shopType.setText(ShopType.FLOW.getName());

        benchMatrix.setDisable(true);
        benchRuns.setDisable(true);
        benchmarkCheckbox.setDisable(!Benchmark.isBenchmarkingSupported());

        activeSolverType = QuickSolver.class;
        for (Node node : algorithmSettings.getChildren()) {
            if (node instanceof JFXRadioButton) algorithmButtonList.add((JFXRadioButton) node);
        }

        initGrid();
    }

    private void initGrid() {
        //Add default machine / job rows
        problemGrid.addRow(0, matrixLabel("J/M"), matrixLabel("M1"), matrixLabel("M2"), matrixButton("columnButton", this::addColumn));
        problemGrid.getRowConstraints().add(0, defaultRowConst());
        problemGrid.addColumn(0, matrixLabel("J1"), matrixLabel("J2"), matrixButton("rowButton", this::addRow));
        problemGrid.getColumnConstraints().add(0, defaultColConst());

        for (int i = 1; i < rows; i++) {
            nodes.add(new ArrayList<>());   //add row in nodes
            for (int j = 1; j < cols; j++) {
                Node field = matrixField();
                nodes.get(i - 1).add(j - 1, field);
                problemGrid.add(field, j, i);           //gridpane .Add takes column then row
                problemGrid.getColumnConstraints().add(j, defaultColConst());
                problemGrid.getRowConstraints().add(i, defaultRowConst());
            }
        }
    }

    @FXML
    private void updateSolver(Event e) {
        JFXRadioButton currentButton = (JFXRadioButton) e.getSource();
        if (benchmarkCheckbox.isSelected()) {
            if (currentButton.isSelected()) {
                currentButton.setSelected(true);
                benchSolverTypes.add(algorithmMap.get(currentButton.getText()));
            } else {
                currentButton.setSelected(false);
                benchSolverTypes.remove(algorithmMap.get(currentButton.getText()));
            }
        } else {
            JFXRadioButton selectedRadioButton = (JFXRadioButton) algorithmToggleGroup.getSelectedToggle();
            activeSolverType = algorithmMap.get(selectedRadioButton.getText());
        }
    }

    @FXML
    private void addColumn(ActionEvent e) {
        GridPane.setColumnIndex((Node) e.getSource(), cols + 1);
        problemGrid.addColumn(cols, matrixLabel("M" + cols));
        for (int i = 0; i < rows - 1; i++) {
            Node field = matrixField();
            nodes.get(i).add(field);
            problemGrid.addColumn(cols, field);
        }
        problemGrid.getColumnConstraints().add(cols, defaultColConst());
        cols++;
        if (cols > 10) {
            ((GridPane) ((Node) e.getSource()).getParent()).getChildren().remove(e.getSource());
        }
        generateMatrix();
    }

    @FXML
    private void addRow(ActionEvent e) {
        GridPane.setRowIndex((Node) e.getSource(), rows + 1);
        problemGrid.addRow(rows, matrixLabel("J" + rows));
        List<Node> row = new ArrayList<>();
        for (int i = 0; i < cols - 1; i++) {
            Node field = matrixField();
            row.add(field);
            problemGrid.addRow(rows, field);
        }
        nodes.add(row);
        problemGrid.getRowConstraints().add(rows, defaultRowConst());
        rows++;
        if (problem.getType() == ShopType.JOB && rows > 5) {
            ((GridPane) ((Node) e.getSource()).getParent()).getChildren().remove(e.getSource());
        } else if (rows > 10) {
            ((GridPane) ((Node) e.getSource()).getParent()).getChildren().remove(e.getSource());
        }
        generateMatrix();
    }

    @FXML
    public void clearMatrix() {
        for (List<Node> row : nodes) {
            for (Node cell : row) {
                for (Node content : ((VBox) cell).getChildren()) {
                    if (content instanceof TextField)
                        ((TextField) content).setText("");
                }
            }
        }
    }

    @FXML
    public void resetMatrix() {

        problemGrid.getChildren().clear();
        rows = initialRows;
        cols = initialColumns;
        nodes.clear();

        initGrid();
    }

    //region Styled Components
    private Label matrixLabel(String text) {
        Label lbl = new Label(text);
        lbl.getStyleClass().add("matrix-label");
        return lbl;
    }

    private Text outputText(String text) {
        Text txt = new Text(text + "\n");
        txt.getStyleClass().add("matrix-label");
        return txt;
    }

    private Node matrixField() {
        VBox container = new VBox();
        TextField mxfld = new TextField();
        mxfld.setPrefSize(45, 45);
        mxfld.textProperty().addListener(this::updateTimeMatrix);
        mxfld.getStyleClass().add("matrix-input");
        container.getChildren().add(mxfld);
        return container;
    }

    private JFXButton matrixButton(String id, EventHandler<ActionEvent> fnc) {
        JFXButton btn = new JFXButton("  ");
        btn.setId(id);
        btn.setOnAction(fnc);
        btn.setButtonType(JFXButton.ButtonType.RAISED);
        btn.setStyle("-fx-background-image:  url('assets/add-icon.png');");
        btn.getStyleClass().add("matrix-button");
        return btn;
    }

    private JFXButton machineButton(String val, EventHandler<ActionEvent> fnc) {
        JFXButton btn = new JFXButton(val);
        btn.setOnAction(fnc);
        btn.setPrefSize(45, 45);
        //btn.setStyle("-fx-background-image:  url('assets/add-icon.png');");
        btn.getStyleClass().add("machine-button");
        return btn;
    }

    private TextFlow textFlow() {
        TextFlow txt = new TextFlow();
        txt.setOpaqueInsets(new Insets(6, 0, 0, 0));
        return txt;
    }

    private Text benchmarkTitle(String text) {
        Text txt = new Text(text);
        txt.getStyleClass().add("bench-title");
        return txt;
    }

    private Text benchmarkHeader(String text) {
        Text txt = new Text(text);
        txt.getStyleClass().add("bench-header");
        return txt;
    }

    //endregion

    //Row/Column style constraints
    private RowConstraints defaultRowConst() {
        RowConstraints rowLayout = new RowConstraints();

        rowLayout.setVgrow(Priority.ALWAYS);
        rowLayout.setMinHeight(0);
        rowLayout.setValignment(VPos.CENTER);
        return rowLayout;
    }

    private ColumnConstraints defaultColConst() {
        ColumnConstraints colLayout = new ColumnConstraints();

        colLayout.setHgrow(Priority.ALWAYS);
        colLayout.setMinWidth(0);
        colLayout.setHalignment(HPos.CENTER);
        return colLayout;
    }

    /*private TableView benchmarkTable(List<Benchmark.SolverData> data){
        TableView output = new TableView();
        output.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        output.getColumns().add(new TableColumn<>());

        for(Benchmark.SolverData solverData : data){
            TableColumn<String,String> column = new TableColumn<>(algorithmMap.inverse().get(solverData.getAlgorithm()));
            output.getColumns().add(column);
        }

        return output;
    }*/

    @FXML
    private void toggleBenchmarkMode(Event event) {
        boolean isChecked = benchmarkCheckbox.isSelected();
        problemGrid.setDisable(isChecked);
        benchMatrix.setDisable(!isChecked);
        benchRuns.setDisable(!isChecked);

        if (isChecked) {
            JFXRadioButton selectedRadioButton = (JFXRadioButton) algorithmToggleGroup.getSelectedToggle();
            benchSolverTypes.add(algorithmMap.get(selectedRadioButton.getText()));

            algorithmButtonList.forEach(jfxRadioButton -> jfxRadioButton.setToggleGroup(null));
            calculateButton.setOnAction(this::solveBenchmark);
        } else {
            benchSolverTypes.clear();
            algorithmButtonList.forEach(jfxRadioButton -> jfxRadioButton.setToggleGroup(algorithmToggleGroup));

            JFXRadioButton selectedRadioButton = (JFXRadioButton) algorithmToggleGroup.getSelectedToggle();
            activeSolverType = algorithmMap.get(selectedRadioButton.getText());
            calculateButton.setOnAction(this::solveMatrix);
        }
    }

    @FXML
    private void generateMatrix() {
        int min = !leftInterval.getText().isEmpty() ? Integer.parseInt(leftInterval.getText()) : 0;
        int max = !leftInterval.getText().isEmpty() ? Integer.parseInt(rightInterval.getText()) : 0;
        problem = new Problem(rows - 1, cols - 1, ShopType.getByName(shopType.getText()), new Point(min, max), zeroPercent.getValue() / 100);

        int labelInc = 1;
        for (Node label : problemGrid.getChildren()) {
            if (label instanceof Label && ((Label) label).getText().charAt(0) == 'M') {
                ((Label) label).setText("M" + labelInc++);
            }
        }

        for (int i = 0; i < nodes.size(); i++) {
            for (int j = 0; j < nodes.get(i).size(); j++) {
                for (Node txtbox : ((Pane) nodes.get(i).get(j)).getChildren()) {
                    if (txtbox instanceof TextField) {
                        ((TextField) txtbox).setText(Integer.toString(problem.getTimeMatrix()[i][j]));
                    }
                }
            }
        }

        if (ShopType.getByName(shopType.getText()) == ShopType.JOB) {
            for (int i = 0; i < nodes.size(); i++) {
                for (int j = 0; j < nodes.get(i).size(); j++) {
                    VBox cell = ((VBox) nodes.get(i).get(j));
                    for (Node box : cell.getChildren()) {
                        if (box instanceof TextField) {
                            ((TextField) box).setText(Integer.toString(problem.getTimeMatrix()[i][j]));
                        }
                        if (box instanceof JFXButton) {
                            ((JFXButton) box).setText("M" + (problem.getMachineMatrix()[i][j] + 1));
                        }
                    }
                    if (cell.getChildren().size() == 1)
                        cell.getChildren().add(machineButton("M" + (problem.getMachineMatrix()[i][j] + 1), this::updateMachineMatrix));
                }
            }
        }
    }

    private void solveBenchmark(Event e) {
        try {
            int benchmarkRuns = 0;
            for (Node node : benchRuns.getChildren()) {
                if (node instanceof TextField) benchmarkRuns = Integer.parseInt(((TextField) node).getText());
            }
            int benchmarkJobs = Integer.parseInt(benchJobs.getText());
            int benchmarkMachines = Integer.parseInt(benchMachines.getText());

            Benchmark bench = new Benchmark(benchmarkRuns, benchmarkJobs, benchmarkMachines, benchSolverTypes);
            bench.setOnCancelled(this::onCancelled);
            bench.setOnFailed(this::onFailed);
            bench.setOnSucceeded(this::onBenchmarkDone);
            bench.setOnScheduled(this::onQueued);
            bench.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void onBenchmarkDone(Event event) {
        resetCalculateButton();

        WorkerStateEvent worker = (WorkerStateEvent) event;
        List<Benchmark.SolverData> result = (List<Benchmark.SolverData>) worker.getSource().getValue();

        List<Node> children = outputContainer.getChildren();
        children.clear();

        TextFlow txtflow = textFlow();
        children.add(txtflow);
        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        for (Benchmark.SolverData data : result) {
            txtflow.getChildren().add(benchmarkTitle(algorithmMap.inverse().get(data.getAlgorithm())));

            txtflow.getChildren().add(outputText("\n"));

            txtflow.getChildren().add(benchmarkHeader("CPU Runtime : "));
            txtflow.getChildren().add(outputText(""));
            txtflow.getChildren().add(outputText("Maximum : " + decimalFormat.format(data.getCpuMax() * 1e-9) + "s" + "\t\t" + "Average : " + decimalFormat.format(data.getCpuAverage() * 1e-9) + "s" + "\t\t" + "Minimum : " + decimalFormat.format(data.getCpuMin() * 1e-9) + "s"));

            txtflow.getChildren().add(outputText(""));

            txtflow.getChildren().add(benchmarkHeader("Memory Usage : "));
            txtflow.getChildren().add(outputText(""));
            txtflow.getChildren().add(outputText("Maximum : " + decimalFormat.format(data.getMemoryMax()) + "Mb" + "\t\t" + "Average : " + decimalFormat.format(data.getMemoryAverage()) + "Mb" + "\t\t" + "Minimum : " + decimalFormat.format(data.getMemoryMin()) + "Mb"));

            txtflow.getChildren().add(outputText(""));

            txtflow.getChildren().add(benchmarkHeader("Solution time : "));
            txtflow.getChildren().add(outputText(""));
            txtflow.getChildren().add(outputText("Shortest : " + data.getBestTime() + "\t\t" + "Average : " + data.getTimeAverage() + "\t\t" + "Minimum : " + data.getWorstTime()));

            txtflow.getChildren().add(outputText(""));

        }

        TitledPane out = ((Accordion) main.getChildren().get(0)).getPanes().get(1);
        ((Accordion) main.getChildren().get(0)).setExpandedPane(out);

        result.clear();
        System.gc();

    }

    @FXML
    private void solveMatrix(Event e) {
        try {
            Solver solver = activeSolverType.getConstructor(Problem.class).newInstance(problem);
            solver.setOnCancelled(this::onCancelled);
            solver.setOnFailed(this::onFailed);
            solver.setOnSucceeded(this::onMatrixSolved);
            solver.setOnScheduled(this::onQueued);
            solver.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void onQueued(Event event) {
        calculateButton.setOnAction((e -> ((WorkerStateEvent) event).getSource().cancel()));
        calculateButton.getStyleClass().add("processing");
        calculateButton.textProperty().setValue("Cancel...");
        calculateButton.setContentDisplay(ContentDisplay.BOTTOM);
        System.out.println("Event running in background!");
    }

    private void onMatrixSolved(Event event) {
        resetCalculateButton();

        WorkerStateEvent worker = (WorkerStateEvent) event;
        List<Schedule> result = (List<Schedule>) worker.getSource().getValue();
        Solver.JobData data = Solver.parseSchedules(result);


        List<XYChart.Series<Number, String>> chartData = JobChart.MapToChart(data.getBestSchedule(), problem);

        NumberAxis xAxis = new NumberAxis();
        CategoryAxis yAxis = new CategoryAxis();

        xAxis.setLabel("Processing Time");
        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(0);
        xAxis.setUpperBound(data.getWorstTime()); //Matrix max time

        List<String> machines = new ArrayList<>();
        for (int i = 1; i <= problem.machineCount; i++) {
            machines.add("Machine " + i);
        }
        yAxis.setTickLabelGap(10);
        yAxis.setCategories(FXCollections.observableArrayList(machines));

        JobChart<Number, String> chart = new JobChart<>(xAxis, yAxis, FXCollections.observableList(chartData));
        chart.setAlternativeRowFillVisible(true);
        chart.setLegendVisible(false);
        chart.setMaxHeight(300);
        VBox.setVgrow(chart, Priority.ALWAYS);

        List<Node> children = outputContainer.getChildren();
        children.clear();

        children.add(0, chart);
        children.add(1, generateLegend());

        TextFlow txtflow = textFlow();
        children.add(2, txtflow);

        txtflow.getChildren().add(outputText("Schedules calculated : " + data.getTotalSchedules()));
        txtflow.getChildren().add(outputText(""));
        txtflow.getChildren().add(outputText("Best Time : " + data.getBestTime()));
        txtflow.getChildren().add(outputText("Worst Time : " + data.getWorstTime()));
        txtflow.getChildren().add(outputText("Average Time : " + Math.round(data.getAvgTime() * 100d) / 100d));

        TitledPane out = ((Accordion) main.getChildren().get(0)).getPanes().get(1);
        ((Accordion) main.getChildren().get(0)).setExpandedPane(out);

        result.clear();
        System.gc();
    }

    private void onFailed(Event event) {
        WorkerStateEvent worker = (WorkerStateEvent) event;
        Throwable error = worker.getSource().getException();

        List<Node> children = outputContainer.getChildren();
        children.clear();
        TextFlow textnode = textFlow();
        children.add(textnode);

        if (error instanceof IllegalArgumentException && error.getMessage().contains("Cartesian product too large")) {
            textnode.getChildren().add(outputText("Error : Matrix is too big to be solved by this solver."));
        } else if (error instanceof OutOfMemoryError) {
            textnode.getChildren().add(outputText("Error : Program ran out of memory."));
        } else {
            error.printStackTrace(System.out);
            textnode.getChildren().add(outputText("Error : " + error.getLocalizedMessage()));
        }

        resetCalculateButton();
        TitledPane out = ((Accordion) main.getChildren().get(0)).getPanes().get(1);
        ((Accordion) main.getChildren().get(0)).setExpandedPane(out);
        System.out.println("Event failed!");
    }

    private void onCancelled(Event event) {
        WorkerStateEvent worker = (WorkerStateEvent) event;
        resetCalculateButton();
        System.out.println("Event canceled!");
    }

    //Returns Chart Legend
    private HBox generateLegend() {
        HBox legend = new HBox();
        legend.setAlignment(Pos.CENTER);
        for (int i = 1; i <= problem.jobCount; i++) {
            TabPane tip = new TabPane();
            tip.setShape(new Circle(6));
            tip.setScaleShape(false);
            tip.getStyleClass().add("job-" + i);
            legend.getChildren().add(tip);
            legend.getChildren().add(new Label("Job " + i));
        }
        return legend;
    }

    private void resetCalculateButton() {
        calculateButton.textProperty().setValue("Calculate");
        calculateButton.setContentDisplay(ContentDisplay.TEXT_ONLY);
        calculateButton.getStyleClass().remove("processing");
        if (benchmarkCheckbox.isSelected())
            calculateButton.setOnAction(this::solveBenchmark);
        else
            calculateButton.setOnAction(this::solveMatrix);
    }

    private void updateTimeMatrix(Observable txtprop, String old, String str) {
        int val;
        try {
            val = Integer.parseInt(str);
        } catch (Exception e) {
            return;
        }

        Node cell = ((TextField) ((ReadOnlyProperty) txtprop).getBean()).getParent();
        for (List row : nodes) {
            if (row.contains(cell)) {
                problem.updateTime(nodes.indexOf(row), row.indexOf(cell), val);
            }
        }
    }

    private void updateMachineMatrix(ActionEvent e) {
        JFXButton butt = (JFXButton) e.getSource();
        Node cell = butt.getParent();
        for (List row : nodes) {
            if (row.contains(cell)) {
                int val = Integer.parseInt(butt.getText().substring(1));
                int newVal = val % problem.machineCount;
                butt.setText("M" + (newVal + 1));
                problem.updateMachine(nodes.indexOf(row), row.indexOf(cell), newVal);
            }
        }
    }

    @FXML
    private void switchShop(ActionEvent e) {
        if (((JFXButton) e.getSource()).getId().equals("leftShopType")) {
            problem.setType(problem.getType().prev());
            shopType.setText(problem.getType().getName());
        } else {
            problem.setType(problem.getType().next());
            shopType.setText(problem.getType().getName());
        }
        clearMatrix();
    }

    private double initX = 0;

    @FXML
    private void setWindow() {
        Point mouse = MouseInfo.getPointerInfo().getLocation();
        Stage stage = (Stage) titleBar.getScene().getWindow();
        initX = mouse.getX() - stage.getX();
    }

    @FXML
    private void dragWindow() {
        Stage stage = (Stage) titleBar.getScene().getWindow();
        Point mouse = MouseInfo.getPointerInfo().getLocation();
        stage.setX(mouse.getX() - initX);
        stage.setY(mouse.getY());
    }

    @FXML
    private void closeWindow() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void reduceWindow() {
        Stage stage = (Stage) miniButton.getScene().getWindow();
        stage.setIconified(true);
    }

}