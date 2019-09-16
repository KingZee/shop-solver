package scheduler;

/**
 * Workaround to packaging JavaFX 11 with the shade artifact
 * This is redundant in development as accessing the Main class directly is possible
 */
public class Shade {
    public static void main(String[] args) {
        Main.main(args);
    }
}
