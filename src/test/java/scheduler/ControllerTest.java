package scheduler;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.matcher.base.GeneralMatchers;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.matcher.base.ParentMatchers;
import scheduler.controller.JobChart;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
@DisplayName("Full Application Test Suite")
class ControllerTest {

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("GUI Elements Presence")
    class GUITests {

        FxRobot robot;
        Controller controller;
        Stage stage;

        @BeforeAll
        void setup() throws Exception {
            FxToolkit.registerPrimaryStage();
            FxToolkit.setupFixture(() -> {
                stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("main.fxml"));
                Parent root;
                try {
                    root = loader.load();
                } catch (Exception exception) {
                    throw new RuntimeException(exception);
                }
                controller = loader.getController();
                robot = new FxRobot();
                Rectangle2D screen = Screen.getPrimary().getVisualBounds();
                Scene scene = new Scene(root, screen.getWidth() / 1.35, screen.getHeight() / 1.25);
                scene.setFill(Color.TRANSPARENT);
                stage.setY(screen.getHeight() * 0.11);
                stage.setX(screen.getWidth() * 0.18);
                stage.setScene(scene);
                stage.show();
            });
        }

        @AfterAll
        void cleanup() {
            robot.interact(() -> stage.close());
        }

        @Test
        void main_contains_elements() {
            FxAssert.verifyThat(controller.main, ParentMatchers.hasChildren(2));
            FxAssert.verifyThat(controller.main, GeneralMatchers.baseMatcher("Main container has menu", hBox -> hBox.getChildren().contains(controller.menu)));
            FxAssert.verifyThat(controller.main, GeneralMatchers.baseMatcher("Main container has problem pane", hBox -> TestFxUtils.fullChildren(hBox).contains(controller.problemGrid)));
            FxAssert.verifyThat(controller.main, GeneralMatchers.baseMatcher("Main container has output pane", hBox -> TestFxUtils.fullChildren(hBox).contains(controller.outputContainer)));
        }

        @Test
        void title_contains_elements() {
            FxAssert.verifyThat(controller.titleBar, ParentMatchers.hasChildren(3));
            FxAssert.verifyThat(controller.titleBar, GeneralMatchers.baseMatcher("Title has label", hBox -> TestFxUtils.hasItemType(hBox.getChildren(), Label.class)));
            FxAssert.verifyThat(controller.titleBar, GeneralMatchers.baseMatcher("Title has close button", hBox -> hBox.getChildren().contains(controller.closeButton)));
            FxAssert.verifyThat(controller.titleBar, GeneralMatchers.baseMatcher("Title has minimize button", hBox -> hBox.getChildren().contains(controller.miniButton)));
        }

        @Test
        void menu_contains_elements() {
            FxAssert.verifyThat(controller.menu, ParentMatchers.hasChildren(8));
            FxAssert.verifyThat(controller.menu, GeneralMatchers.baseMatcher("Menu is visible", (Node::isVisible)));
            FxAssert.verifyThat(controller.menu, GeneralMatchers.baseMatcher("Menu has a label", vBox -> TestFxUtils.hasItemType(vBox.getChildren(), Label.class)));
            FxAssert.verifyThat(controller.menu, GeneralMatchers.baseMatcher("Menu has calculate button", vBox -> vBox.getChildren().contains(controller.calculateButton)));
            FxAssert.verifyThat(controller.menu, GeneralMatchers.baseMatcher("Menu has shop type selector", vBox -> TestFxUtils.fullChildren(vBox).contains(controller.shopType)));
            FxAssert.verifyThat(controller.menu, GeneralMatchers.baseMatcher("Menu has zero-percent selector", vBox -> TestFxUtils.fullChildren(vBox).contains(controller.zeroPercent)));
            FxAssert.verifyThat(controller.menu, GeneralMatchers.baseMatcher("Menu does not have a matrix", vBox -> !TestFxUtils.fullChildren(vBox).contains(controller.problemGrid)));
        }

        @Test
        void benchmark_mode_toggle() {
            FxAssert.verifyThat(controller.benchmarkCheckbox, jfxCheckBox -> !jfxCheckBox.isSelected());
            FxAssert.verifyThat(controller.benchMatrix, NodeMatchers.isDisabled());
            FxAssert.verifyThat(controller.benchRuns, NodeMatchers.isDisabled());
            FxAssert.verifyThat(controller.problemGrid, NodeMatchers.isEnabled());
            robot.clickOn(controller.benchmarkCheckbox);
            FxAssert.verifyThat(controller.benchmarkCheckbox, CheckBox::isSelected);
            FxAssert.verifyThat(controller.benchMatrix, NodeMatchers.isEnabled());
            FxAssert.verifyThat(controller.benchRuns, NodeMatchers.isEnabled());
            FxAssert.verifyThat(controller.problemGrid, NodeMatchers.isDisabled());
        }

    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("Matrix Controls")
    class MatrixControl {

        FxRobot robot;
        Controller controller;
        Stage stage;

        @BeforeAll
        void setup() throws Exception {
            FxToolkit.registerPrimaryStage();
            FxToolkit.setupFixture(() -> {
                stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("main.fxml"));
                Parent root;
                try {
                    root = loader.load();
                } catch (Exception exception) {
                    throw new RuntimeException(exception);
                }
                controller = loader.getController();
                robot = new FxRobot();
                Rectangle2D screen = Screen.getPrimary().getVisualBounds();
                Scene scene = new Scene(root, screen.getWidth() / 1.35, screen.getHeight() / 1.25);
                scene.setFill(Color.TRANSPARENT);
                stage.setY(screen.getHeight() * 0.11);
                stage.setX(screen.getWidth() * 0.18);
                stage.setScene(scene);
                stage.show();
            });
        }

        @AfterAll
        void cleanup() {
            robot.interact(() -> stage.close());
        }

        @Test
        void matrix_is_generated_type_1() {
            while (!controller.shopType.getText().equals("Flow Shop")) robot.clickOn(controller.leftShopType);
            robot.clickOn(controller.generateMatrix);
            FxAssert.verifyThat(controller.problemGrid, gridPane -> {
                for (Node node : gridPane.getChildren()) {
                    if (node instanceof TextField) {
                        try {
                            Integer.parseInt(((TextField) node).getText());
                        } catch (NumberFormatException e) {
                            return false;
                        }
                    }
                }
                return true;
            });
        }

        @Test
        void matrix_is_generated_type_2() {
            while (!controller.shopType.getText().equals("Open Shop")) robot.clickOn(controller.rightShopType);
            robot.clickOn(controller.generateMatrix);
            FxAssert.verifyThat(controller.problemGrid, gridPane -> {
                for (Node node : gridPane.getChildren()) {
                    if (node instanceof TextField) {
                        try {
                            Integer.parseInt(((TextField) node).getText());
                        } catch (NumberFormatException e) {
                            return false;
                        }
                    }
                }
                return true;
            });
        }

        @Test
        void matrix_is_generated_type_3() {
            while (!controller.shopType.getText().equals("Job Shop")) robot.clickOn(controller.leftShopType);
            robot.clickOn(controller.resetMatrix);
            robot.clickOn(controller.generateMatrix);

            FxAssert.verifyThat(controller.problemGrid, gridPane -> {
                for (Node node : gridPane.getChildren()) {
                    if (node instanceof TextField) {
                        try {
                            Integer.parseInt(((TextField) node).getText());
                        } catch (NumberFormatException e) {
                            return false;
                        }
                    }
                }
                return true;
            });
        }

        @Test
        void matrix_buttons_disappear() {
            while (!controller.shopType.getText().equals("Open Shop")) robot.clickOn(controller.rightShopType);
            robot.clickOn(controller.resetMatrix);

            List<Node> nodes = new ArrayList<>(robot.lookup(".matrix-button").queryAll());
            FxAssert.verifyThat(controller.problemGrid, GeneralMatchers.baseMatcher("Grid has col button", gridPane -> TestFxUtils.fullChildren(gridPane).contains(nodes.get(0))));
            FxAssert.verifyThat(controller.problemGrid, GeneralMatchers.baseMatcher("Grid has row button", gridPane -> TestFxUtils.fullChildren(gridPane).contains(nodes.get(1))));

            final int x_clicks = 8;
            final int y_clicks = 8;
            for (int i = 0; i < x_clicks; i++) {
                robot.clickOn(nodes.get(0));
            }
            for (int j = 0; j < y_clicks; j++) {
                robot.clickOn(nodes.get(1));
            }

            FxAssert.verifyThat(controller.problemGrid, GeneralMatchers.baseMatcher("Grid no longer has col button", gridPane -> !TestFxUtils.fullChildren(gridPane).contains(nodes.get(0))));
            FxAssert.verifyThat(controller.problemGrid, GeneralMatchers.baseMatcher("Grid no longer has row button", gridPane -> !TestFxUtils.fullChildren(gridPane).contains(nodes.get(1))));
        }

        @Test
        void matrix_is_cleared() {
            robot.clickOn(controller.generateMatrix);
            FxAssert.verifyThat(controller.problemGrid, gridPane -> {
                for (Node node : gridPane.getChildren()) {
                    if (node instanceof TextField) {
                        try {
                            Integer.parseInt(((TextField) node).getText());
                        } catch (NumberFormatException e) {
                            return false;
                        }
                    }
                }
                return true;
            });

            robot.clickOn(controller.clearMatrix);

            FxAssert.verifyThat(controller.problemGrid, gridPane -> {
                for (Node node : gridPane.getChildren()) {
                    if (node instanceof TextField) {
                        if (!((TextField) node).getText().trim().isEmpty()) return false;
                    }
                }
                return true;
            });
        }

        @Test
        void matrix_size_changes() {
            while (!controller.shopType.getText().equals("Flow Shop")) robot.clickOn(controller.leftShopType);
            robot.clickOn(controller.resetMatrix);

            final int initialColumns = controller.problemGrid.getColumnCount();
            final int initialRows = controller.problemGrid.getRowCount();
            final int x_clicks = ThreadLocalRandom.current().nextInt(0, 8);
            final int y_clicks = ThreadLocalRandom.current().nextInt(0, 8);

            List<Node> nodes = new ArrayList<>(robot.lookup(".matrix-button").queryAll());

            for (int i = 0; i < x_clicks; i++) {
                robot.clickOn(nodes.get(0));
            }
            for (int j = 0; j < y_clicks; j++) {
                robot.clickOn(nodes.get(1));
            }
            FxAssert.verifyThat(controller.problemGrid, gridPane -> gridPane.getColumnCount() == (x_clicks + initialColumns));
            FxAssert.verifyThat(controller.problemGrid, gridPane -> gridPane.getRowCount() == (y_clicks + initialRows));
        }

    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("Solver Logic")
    class MatrixTest {

        FxRobot robot;
        Controller controller;
        Stage stage;

        @BeforeAll
        void setup() throws Exception {
            FxToolkit.registerPrimaryStage();
            FxToolkit.setupFixture(() -> {
                stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("main.fxml"));
                Parent root;
                try {
                    root = loader.load();
                } catch (Exception exception) {
                    throw new RuntimeException(exception);
                }
                controller = loader.getController();
                robot = new FxRobot();
                Rectangle2D screen = Screen.getPrimary().getVisualBounds();
                Scene scene = new Scene(root, screen.getWidth() / 1.35, screen.getHeight() / 1.25);
                scene.setFill(Color.TRANSPARENT);
                stage.setY(screen.getHeight() * 0.11);
                stage.setX(screen.getWidth() * 0.18);
                stage.setScene(scene);
                stage.show();
            });
        }

        @AfterAll
        void cleanup() {
            robot.interact(() -> stage.close());
        }

        @BeforeEach
        void prepare() {
            if (!controller.problemPane.isExpanded()) robot.clickOn(controller.problemPane);
            robot.clickOn(controller.resetMatrix);
        }

        @Test
        void solve_random_matrix() {
            while (!controller.shopType.getText().equals("Flow Shop")) robot.clickOn(controller.leftShopType);

            List<Node> nodes = new ArrayList<>(robot.lookup(".matrix-button").queryAll());

            final int x_clicks = ThreadLocalRandom.current().nextInt(0, 4);
            final int y_clicks = ThreadLocalRandom.current().nextInt(0, 4);
            for (int i = 0; i < x_clicks; i++) {
                robot.clickOn(nodes.get(0));
            }
            for (int j = 0; j < y_clicks; j++) {
                robot.clickOn(nodes.get(1));
            }

            robot.clickOn(controller.generateMatrix);
            robot.clickOn(controller.calculateButton);

            try {
                synchronized (robot) {
                    while (controller.problemPane.isExpanded())
                        robot.wait(100);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            FxAssert.verifyThat(controller.outputContainer, GeneralMatchers.baseMatcher("Output has a chart", vBox -> TestFxUtils.hasItemType(TestFxUtils.fullChildren(vBox), JobChart.class)));
        }

        @Test
        void solve_empty_matrix() {
            while (!controller.shopType.getText().equals("Flow Shop")) robot.clickOn(controller.leftShopType);

            List<Node> nodes = new ArrayList<>(robot.lookup(".matrix-button").queryAll());

            final int x_clicks = ThreadLocalRandom.current().nextInt(0, 4);
            final int y_clicks = ThreadLocalRandom.current().nextInt(0, 4);
            for (int i = 0; i < x_clicks; i++) {
                robot.clickOn(nodes.get(0));
            }
            for (int j = 0; j < y_clicks; j++) {
                robot.clickOn(nodes.get(1));
            }

            robot.clickOn(controller.clearMatrix);

            for (Node node : TestFxUtils.fullChildren(controller.problemGrid)) {
                if (node instanceof TextField) {
                    robot.clickOn(node);
                    robot.write("0");
                }
            }

            robot.clickOn(controller.calculateButton);

            try {
                synchronized (robot) {
                    while (controller.problemPane.isExpanded())
                        robot.wait(100);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            FxAssert.verifyThat(controller.outputContainer, GeneralMatchers.baseMatcher("Output has a chart", vBox -> TestFxUtils.hasItemType(TestFxUtils.fullChildren(vBox), JobChart.class)));

            TextFlow textFlow = robot.lookup(".textflow").query();
            StringBuilder fullText = new StringBuilder();
            for (Node txt : textFlow.getChildren()) {
                if (txt instanceof Text) {
                    fullText.append(((Text) txt).getText());
                }
            }

            assertTrue(fullText.toString().contains("Best Time : 0"));
            assertTrue(fullText.toString().contains("Worst Time : 0"));
            assertTrue(fullText.toString().contains("Average Time : 0.0"));
        }

        @Test
        void clear_non_integer_input() {
            while (!controller.shopType.getText().equals("Flow Shop")) robot.clickOn(controller.leftShopType);

            String[] unallowedStrings = {"--", "test", "int", ".", "*"};

            List<Node> nodes = new ArrayList<>(robot.lookup(".matrix-button").queryAll());

            final int x_clicks = ThreadLocalRandom.current().nextInt(0, 4);
            final int y_clicks = ThreadLocalRandom.current().nextInt(0, 4);
            for (int i = 0; i < x_clicks; i++) {
                robot.clickOn(nodes.get(0));
            }
            for (int j = 0; j < y_clicks; j++) {
                robot.clickOn(nodes.get(1));
            }

            robot.clickOn(controller.clearMatrix);

            for (Node node : TestFxUtils.fullChildren(controller.problemGrid)) {
                if (node instanceof TextField) {
                    robot.clickOn(node);
                    robot.write(unallowedStrings[ThreadLocalRandom.current().nextInt(0, unallowedStrings.length)]);
                }
            }

            FxAssert.verifyThat(controller.problemGrid, gridPane -> {
                for (Node node : gridPane.getChildren()) {
                    if (node instanceof TextField) {
                        if (!((TextField) node).getText().trim().isEmpty()) return false;
                    }
                }
                return true;
            });
        }

        @Test
        void solve_google_example() {    //https://developers.google.com/optimization/scheduling/job_shop
            while (!controller.shopType.getText().equals("Job Shop")) robot.clickOn(controller.leftShopType);

            String[] machineSequence = {"M1", "M2", "M1", "M3", "M3", "M2", "M1", "M2", "M3"};
            int[] timeSequence = {3, 2, 2, 1, 2, 4, 0, 4, 3};

            List<Node> nodes = new ArrayList<>(robot.lookup(".matrix-button").queryAll());
            robot.clickOn(nodes.get(0));
            robot.clickOn(nodes.get(1));
            robot.clickOn(controller.clearMatrix);

            int i = 0, j = 0;
            for (Node node : TestFxUtils.fullChildren(controller.problemGrid)) {
                if (node instanceof TextField) {
                    robot.clickOn(node);
                    robot.write("" + timeSequence[i]);
                    i++;
                } else if (node.getStyleClass().contains("machine-button")) {
                    while (!((JFXButton) node).getText().trim().equals(machineSequence[j])) robot.clickOn(node);
                    j++;
                }
            }

            robot.clickOn(controller.calculateButton);

            try {
                synchronized (robot) {
                    while (controller.problemPane.isExpanded())
                        robot.wait(100);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }


            FxAssert.verifyThat(controller.outputContainer, GeneralMatchers.baseMatcher("Output has a chart", vBox -> TestFxUtils.hasItemType(TestFxUtils.fullChildren(vBox), JobChart.class)));

            TextFlow textFlow = robot.lookup(".textflow").query();
            StringBuilder fullText = new StringBuilder();
            for (Node txt : textFlow.getChildren()) {
                if (txt instanceof Text) {
                    fullText.append(((Text) txt).getText());
                }
            }

            assertTrue(fullText.toString().contains("Best Time : 11"));
        }

    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("Benchmark Mode Logic")
    class BenchmarkTest {

        FxRobot robot;
        Controller controller;
        Stage stage;

        @BeforeEach
        void setup() throws Exception {
            FxToolkit.registerPrimaryStage();
            FxToolkit.setupFixture(() -> {
                stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("main.fxml"));
                Parent root;
                try {
                    root = loader.load();
                } catch (Exception exception) {
                    throw new RuntimeException(exception);
                }
                controller = loader.getController();
                robot = new FxRobot();
                Rectangle2D screen = Screen.getPrimary().getVisualBounds();
                Scene scene = new Scene(root, screen.getWidth() / 1.35, screen.getHeight() / 1.25);
                scene.setFill(Color.TRANSPARENT);
                stage.setY(screen.getHeight() * 0.11);
                stage.setX(screen.getWidth() * 0.18);
                stage.setScene(scene);
                stage.show();
            });
            if (!controller.problemPane.isExpanded()) robot.clickOn(controller.problemPane);
            if (!controller.benchmarkCheckbox.isSelected()) robot.clickOn(controller.benchmarkCheckbox);
        }

        @AfterEach
        void cleanup() {
            if (stage != null) robot.interact(() -> stage.close());
        }

        @Test
        void benchmark_none_selected() {
            for (JFXRadioButton node : controller.algorithmButtonList) {
                if (node.isSelected()) robot.clickOn(node);
            }

            for (Node node : TestFxUtils.fullChildren(controller.benchMatrix)) {
                if (node instanceof TextField) {
                    robot.clickOn(node);
                    robot.write('1');
                }
            }

            for (Node node : controller.benchRuns.getChildren()) {
                if (node instanceof TextField) {
                    robot.clickOn(node);
                    robot.write('1');
                }
            }

            robot.clickOn(controller.calculateButton);

            TextFlow textFlow = robot.lookup(".textflow").query();
            StringBuilder fullText = new StringBuilder();
            for (Node txt : textFlow.getChildren()) {
                if (txt instanceof Text) {
                    fullText.append(((Text) txt).getText());
                }
            }

            assertTrue(fullText.toString().contains("Please select one or more algorithms to benchmark"));
        }

        @Test
        void benchmark_non_integer_input() {
            for (JFXRadioButton node : controller.algorithmButtonList) {
                if (!node.isSelected()) robot.clickOn(node);
            }

            for (Node node : TestFxUtils.fullChildren(controller.benchMatrix)) {
                if (node instanceof TextField) {
                    robot.clickOn(node);
                    robot.write("-1");
                }
            }

            for (Node node : controller.benchRuns.getChildren()) {
                if (node instanceof TextField) {
                    robot.clickOn(node);
                    robot.write("-1");
                }
            }

            robot.clickOn(controller.calculateButton);

            TextFlow textFlow = robot.lookup(".textflow").query();
            StringBuilder fullText = new StringBuilder();
            for (Node txt : textFlow.getChildren()) {
                if (txt instanceof Text) {
                    fullText.append(((Text) txt).getText());
                }
            }

            assertTrue(fullText.toString().contains("Please input a valid number for the benchmark settings"));
        }

        @Test
        void benchmark_returns_correct_output() {
            for (JFXRadioButton node : controller.algorithmButtonList) {
                if (!node.isSelected()) robot.clickOn(node);
            }

            for (Node node : TestFxUtils.fullChildren(controller.benchMatrix)) {
                if (node instanceof TextField) {
                    robot.clickOn(node);
                    robot.write("3");
                }
            }

            for (Node node : controller.benchRuns.getChildren()) {
                if (node instanceof TextField) {
                    robot.clickOn(node);
                    robot.write("2");
                }
            }

            robot.clickOn(controller.calculateButton);

            try {
                synchronized (robot) {
                    while (controller.problemPane.isExpanded()) {
                        robot.wait(100);
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            TextFlow textFlow = robot.lookup(".textflow").query();
            StringBuilder fullText = new StringBuilder();
            for (Node txt : textFlow.getChildren()) {
                if (txt instanceof Text) {
                    fullText.append(((Text) txt).getText());
                }
            }

            assertTrue(fullText.toString().contains("CPU Runtime"));
            assertTrue(fullText.toString().contains("Memory Usage"));
            assertTrue(fullText.toString().contains("Solution time"));

        }

    }

}