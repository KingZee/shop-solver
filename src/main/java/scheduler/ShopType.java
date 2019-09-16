package scheduler;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

/**
 * Enumeration for all shop types
 */

public enum ShopType {
    OPEN("Open Shop"),
    JOB("Job Shop"),
    FLOW("Flow Shop");

    private String name;

    private static final Map<String, ShopType> vals = new LinkedHashMap<>();

    //Populate the lookup table on loading time
    static {
        for (ShopType shop : ShopType.values()) {
            vals.put(shop.getName(), shop);
        }
    }

    /**
     * Method to get the next enum of this instance
     * @return ShopType enum
     */
    public ShopType next() {
        return (ShopType) vals.values().toArray()[(this.ordinal() + 1) % vals.size()];
    }

    /**
     * Method to get the previous enum of this instance
     * @return ShopType enum
     */
    public ShopType prev() {
        return (ShopType) vals.values().toArray()[((this.ordinal() - 1) + vals.size()) % vals.size()];
    }

    ShopType(String n) {
        this.name = n;
    }

    /**
     * Method to get the name associated with this shop type
     * @return String name
     */
    public String getName() {
        return name;
    }

    /**
     * Reverse lookup the enum instance by its string identifier
     * @param n name of shop type to lookup
     * @return ShopType enum
     */
    public static ShopType getByName(String n) {
        return vals.get(n);
    }

    /**
     * Returns a random shop type instance,
     * useful for benchmarking
     * @return ShopType enum
     */
    public static ShopType getRandom() {
        return (ShopType) vals.values().toArray()[new Random().nextInt(vals.size())];
    }

}