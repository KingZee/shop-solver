package scheduler.controller;

import javafx.beans.NamedArg;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.Axis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import scheduler.Problem;
import scheduler.Schedule;
import scheduler.ShopType;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JobChart<X, Y> extends XYChart<X, Y> {

    public static List<Series<Number, String>> MapToChart(Schedule schedule, Problem problem) {  //List for end times & job indices
        List<Series<Number, String>> chart = new ArrayList<>();                                       //Array for job processing times
        int[][] timeMatrix = problem.getTimeMatrix();
        int[][] machineMatrix = problem.getMachineMatrix();
        for (int i = 0; i < problem.machineCount; i++) {
            Series<Number, String> machineSeries = new Series<>();
            for (int j = 0; j < timeMatrix.length; j++) {
                if(timeMatrix[j][i] !=0) {
                    int machineIndex = problem.getType() == ShopType.JOB ? machineMatrix[j][i] : i;
                    Node blockPane = new StackPane();
                    Data<Number, String> block = new Data<>();
                    block.setNode(blockPane);
                    block.setYValue("Machine " + (machineIndex+1));
                    block.setXValue(schedule.get(new Point(j, machineIndex)) - timeMatrix[j][i]);   //X value : start time
                    block.setExtraValue(timeMatrix[j][i]);                     //Extra value : processing time
                    blockPane.getStyleClass().add("job-" + (j + 1));
                    machineSeries.getData().add(block);
                }
            }
            chart.add(machineSeries);
        }
        return chart;
    }

    public JobChart(@NamedArg("xAxis") Axis<X> xAxis, @NamedArg("yAxis") Axis<Y> yAxis) {
        this(xAxis, yAxis, FXCollections.observableArrayList());
    }

    public JobChart(@NamedArg("xAxis") Axis<X> xAxis, @NamedArg("yAxis") Axis<Y> yAxis, @NamedArg("data") ObservableList<Series<X, Y>> data) {
        super(xAxis, yAxis);
        if (!(xAxis instanceof NumberAxis && yAxis instanceof CategoryAxis)) {
            throw new IllegalArgumentException("Axis type incorrect.");
        }
        setData(data);
    }

    @Override
    protected void dataItemAdded(Series series, int itemIndex, Data item) {
        getPlotChildren().add(item.getNode());
    }

    @Override
    protected void dataItemRemoved(Data item, Series series) {
        getPlotChildren().remove(item.getNode());
        removeDataItemFromDisplay(series, item);
    }

    @Override
    protected void seriesAdded(Series series, int seriesIndex) {
        for (int j = 0; j < series.getData().size(); j++) {
            Data<X, Y> item = (Data<X, Y>) series.getData().get(j);
            getPlotChildren().add(item.getNode());
        }
    }

    @Override
    protected void seriesRemoved(Series series) {
        for (Object data : series.getData()) {
            getPlotChildren().remove(((XYChart.Data<X, Y>) data).getNode());
        }
        removeSeriesFromDisplay(series);
    }

    @Override
    protected void layoutPlotChildren() {
        final double unitHeight = (getHeight() - 20) / (double) (getData().size() * 2);
        for (Series<X, Y> series : getData()) {
            Iterator<Data<X, Y>> data = getDisplayedDataIterator(series);
            while (data.hasNext()) {
                Data<X, Y> item = data.next();
                double x = getXAxis().getDisplayPosition(item.getXValue());
                double y = getYAxis().getDisplayPosition(item.getYValue());
                if (Double.isNaN(x) || Double.isNaN(y)) {
                    continue;
                }
                Node block = item.getNode();
                Rectangle region;
                if (block != null) {
                    if (block instanceof StackPane) {
                        StackPane container = (StackPane) block;
                        if (container.getShape() == null) {
                            region = new Rectangle((int) item.getExtraValue(), unitHeight);
                        } else if (container.getShape() instanceof Rectangle) {
                            region = (Rectangle) container.getShape();
                        } else {
                            return;
                        }
                        region.setWidth((int) item.getExtraValue() * ((getXAxis() instanceof NumberAxis) ? Math.abs(((NumberAxis) getXAxis()).getScale()) : 1));
                        region.setHeight(unitHeight * ((getYAxis() instanceof NumberAxis) ? Math.abs(((NumberAxis) getYAxis()).getScale()) : 1));
                        y -= unitHeight / 2.0;

                        container.setShape(region);
                        container.setScaleShape(false);
                        container.setCenterShape(false);
                        container.setCacheShape(false);

                        block.setLayoutX(x);
                        block.setLayoutY(y);
                    }
                }
            }
        }
    }

    /**
     * Called when a data item has changed, ie its xValue, yValue or extraValue has changed.
     *
     * @param item The data item who was changed
     */
    @Override
    protected void dataItemChanged(Data item) {
    }

}