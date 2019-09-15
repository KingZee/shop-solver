package scheduler;

import javafx.scene.Node;
import javafx.scene.Parent;

import java.util.ArrayList;
import java.util.List;

public class TestFxUtils {

    public static boolean hasItemType(List nodeList, Class<? extends Node> type) {
        for (Object item : nodeList) {
            if (type.isAssignableFrom(item.getClass())) return true;
        }
        return false;
    }

    public static <T extends Parent> List<Node> fullChildren(T parent) {
        return fullChildren(parent, new ArrayList<>());
    }

    private static <T extends Parent> List<Node> fullChildren(T parent, List<Node> nodes) {
        for (Node node : parent.getChildrenUnmodifiable()) {
            if (node instanceof Parent) {
                nodes.add(node);
                fullChildren((Parent) node, nodes);
            } else {
                nodes.add(node);
            }
        }
        return nodes;
    }
}