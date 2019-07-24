package scheduler;

import java.util.LinkedHashMap;
import java.util.Map;

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

    public ShopType next() {
        return (ShopType) vals.values().toArray()[(this.ordinal() + 1) % vals.size()];
    }

    public ShopType prev() {
        return (ShopType) vals.values().toArray()[((this.ordinal() - 1) + vals.size()) % vals.size()];
    }

    ShopType(String n) {
        this.name = n;
    }

    public String getName() {
        return name;
    }

    public static ShopType getByName(String n) {
        return vals.get(n);
    }

}