package sample;

import com.jfoenix.controls.JFXButton;
import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import sample.solvers.ExactSolver;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Controller {
    //Main container
    @FXML
    private javafx.scene.layout.HBox main;

    //Top title bar for close/minimise/title Label
    @FXML
    private javafx.scene.control.Button closeButton;
    @FXML
    private javafx.scene.control.Button miniButton;
    @FXML
    private javafx.scene.layout.HBox titleBar;

    //Main pane : problem, and output accordions
    @FXML
    private javafx.scene.control.TitledPane problemContainer;
    @FXML
    private javafx.scene.control.TitledPane outputContainer;

    //Menu buttons / slider references
    @FXML
    private com.jfoenix.controls.JFXTextField shopType;
    @FXML
    private com.jfoenix.controls.JFXSlider zeroPercent;
    @FXML
    private com.jfoenix.controls.JFXTextField leftInterval;
    @FXML
    private com.jfoenix.controls.JFXTextField rightInterval;

    GridPane table = new GridPane();    //This will always be set after initialize()
    ArrayList<ArrayList<Node>> nodes = new ArrayList<>();  //Keep references to GridPane by indices
    int rows = 3;                //Initial row/col length for matrix on load
    int cols = 3;
    Problem problem = new Problem(rows-1,cols-1);        //The instance of the problem

    @FXML
    public void initialize() {

        //Reset value of shopType Input field
        shopType.setText(ShopType.FLOW.getName());

        //Initialize reference to matrix Grid Pane
        for (Node currentNode : ((AnchorPane) problemContainer.getContent()).getChildren()){
            if (currentNode instanceof GridPane){
                table = (GridPane) currentNode;
                break;
            }
        }
            /*<>*/
        //Add default machine / job rows
        table.addRow(0,matrixLabel("J/M"),matrixLabel("M1"),matrixLabel("M2"),matrixButton("columnButton",this::addColumn));
        table.getRowConstraints().add(0,defaultRowConst());
        table.addColumn(0,matrixLabel("J1"),matrixLabel("J2"),matrixButton("rowButton",this::addRow));
        table.getColumnConstraints().add(0,defaultColConst());

        for(int i = 1; i < rows; i++) {
            nodes.add(new ArrayList<>());   //add row in nodes
            for (int j = 1; j < cols; j++) {
                Node field = matrixField();
                nodes.get(i - 1).add(j - 1, field);
                table.add(field,j,i);           //gridpane .Add takes column then row
                table.getColumnConstraints().add(j,defaultColConst());
                table.getRowConstraints().add(i,defaultRowConst());
            }
        }
    }

    @FXML
    private void addColumn(ActionEvent e){
        GridPane.setColumnIndex((Node)e.getSource(), cols +1);
        table.addColumn(cols,matrixLabel("M"+ cols));
        for(int i = 0; i< rows -1 ; i++){
            Node field = matrixField();
            nodes.get(i).add(field);
            table.addColumn(cols,field);
        }
        table.getColumnConstraints().add(cols,defaultColConst());
        cols++;
        generateMatrix();
    }

    @FXML
    private void addRow(ActionEvent e){
        GridPane.setRowIndex((Node)e.getSource(), rows +1);
        table.addRow(rows,matrixLabel("J"+ rows));
        ArrayList<Node> row = new ArrayList<>();
        for(int i = 0; i< cols -1 ; i++){
            Node field = matrixField();
            row.add(field);
            table.addRow(rows,field);
        }
        nodes.add(row);
        table.getRowConstraints().add(rows,defaultRowConst());
        rows++;
        generateMatrix();
    }

    //region Styled Components
    private Label matrixLabel(String text){
        Label lbl = new Label(text);
        lbl.getStyleClass().add("matrix-label");
        return lbl;
    }

    private Node matrixField(){
        VBox container = new VBox();
        TextField mxfld = new TextField();
        mxfld.setPrefSize(45,45);
        mxfld.textProperty().addListener(this::updateTimeMatrix);
        mxfld.getStyleClass().add("matrix-input");
        container.getChildren().add(mxfld);
        return container;
    }

    private JFXButton matrixButton(String id, EventHandler<ActionEvent> fnc){
        JFXButton btn = new JFXButton("  ");
        btn.setId(id);
        btn.setOnAction(fnc);
        btn.setButtonType(JFXButton.ButtonType.RAISED);
        btn.setStyle("-fx-background-image:  url('resources/add-icon.png');");
        btn.getStyleClass().add("matrix-button");
        return btn;
    }

    private JFXButton machineButton(String val, EventHandler<ActionEvent> fnc){
        JFXButton btn = new JFXButton(val);
        btn.setOnAction(fnc);
        btn.setPrefSize(45,45);
        //btn.setStyle("-fx-background-image:  url('resources/add-icon.png');");
        btn.getStyleClass().add("machine-button");
        return btn;
    }

    //endregion

    //Row/Column style constraints
    private RowConstraints defaultRowConst(){
        RowConstraints rowLayout = new RowConstraints();

        rowLayout.setVgrow(Priority.ALWAYS);
        rowLayout.setMinHeight(0);
        rowLayout.setValignment(VPos.CENTER);
        return rowLayout;
    }

    private ColumnConstraints defaultColConst(){
        ColumnConstraints colLayout = new ColumnConstraints();

        colLayout.setHgrow(Priority.ALWAYS);
        colLayout.setMinWidth(0);
        colLayout.setHalignment(HPos.CENTER);
        return colLayout;
    }

    @FXML
    private void generateMatrix(){
        int min = !leftInterval.getText().isEmpty() ? Integer.parseInt(leftInterval.getText()) : 0;
        int max = !leftInterval.getText().isEmpty() ? Integer.parseInt(rightInterval.getText()) : 0;
        problem = new Problem(rows -1, cols -1,ShopType.getByName(shopType.getText()),new Point(min,max),zeroPercent.getValue()/100);

        int labelInc = 1;
        for(Node label : table.getChildren()){
            if(label instanceof Label && ((Label) label).getText().charAt(0)=='M'){
                ((Label) label).setText("M"+labelInc++);
            }
        }

        for(int i=0;i<nodes.size();i++){
            for(int j=0;j<nodes.get(i).size();j++){
                for(Node txtbox : ((Pane)nodes.get(i).get(j)).getChildren()) {
                    if(txtbox instanceof TextField) {
                        ((TextField) txtbox).setText(Integer.toString(problem.getTimeMatrix()[i][j]));
                    }
                }
            }
        }

        if(ShopType.getByName(shopType.getText()) == ShopType.JOB){     //Job Shop needs machine rows

            for(Node label : table.getChildren()){              //So we clear the top row
                if(label instanceof Label && ((Label) label).getText().charAt(0)=='M'){
                    ((Label) label).setText("M");
                }
            }

            for(int i=0;i<nodes.size();i++){
                for(int j=0;j<nodes.get(i).size();j++){
                    VBox cell = ((VBox)nodes.get(i).get(j));
                    for(Node txtbox : cell.getChildren()) {
                        if(txtbox instanceof TextField) {
                            ((TextField) txtbox).setText(Integer.toString(problem.getTimeMatrix()[i][j]));
                        }
                    }
                    if(cell.getChildren().size()==1)cell.getChildren().add(machineButton("M"+(problem.getMachineMatrix()[i][j]+1),this::updateMachineMatrix));
                }
            }

        }
    }

    @FXML
    private void solveMatrix(){
        ExactSolver solver = new ExactSolver(problem);
        ArrayList<ArrayList<MachineMap>> schedule = solver.solveMakespan();
    }

    private void updateTimeMatrix(Observable txtprop, String old, String str){
        int val;
        try { val = Integer.parseInt(str); } catch (Exception e){ return; }

        Node cell = ((TextField)((ReadOnlyProperty)txtprop).getBean()).getParent();
        for(ArrayList row : nodes){
            if(row.contains(cell)){
                problem.updateTime(nodes.indexOf(row),row.indexOf(cell),val);
                //System.out.println(Arrays.deepToString(problem.getTimeMatrix()));
            }
        }
        //problem
    }

    private void updateMachineMatrix(ActionEvent e){
        Node butt = (JFXButton)e.getSource();
        Node cell = butt.getParent();
        for(ArrayList row : nodes){
            if(row.contains(cell)){
                int val = Integer.parseInt(((JFXButton) butt).getText().substring(1));
                int newVal = val%problem.machineCount;
                ((JFXButton) butt).setText("M"+(newVal+1));
                problem.updateMachine(nodes.indexOf(row),row.indexOf(cell),newVal);
                System.out.println(Arrays.deepToString(problem.getMachineMatrix()));
            }
        }
    }

    @FXML
    private void switchShop(ActionEvent e){
        if(((JFXButton)e.getSource()).getId().equals("leftShopType")){
            shopType.setText(ShopType.getByName(shopType.getText()).prev().getName());
        } else {
            shopType.setText(ShopType.getByName(shopType.getText()).next().getName());
        }
    }

    double initX = 0;

    @FXML
    private void setWindow(){
        Point mouse = MouseInfo.getPointerInfo().getLocation();
        Stage stage = (Stage) titleBar.getScene().getWindow();
        initX = mouse.getX() - stage.getX();
    }

    @FXML
    private void dragWindow(){
        Stage stage = (Stage) titleBar.getScene().getWindow();
        Point mouse = MouseInfo.getPointerInfo().getLocation();
        stage.setX(mouse.getX()-initX);
        stage.setY(mouse.getY());
    }

    @FXML
    private void closeWindow(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void reduceWindow(){
        Stage stage = (Stage) miniButton.getScene().getWindow();
        stage.setIconified(true);
    }

}