package net.kettlemc.kessentials.util;

import java.util.function.Supplier;

public class Placeholder {
    private final String key;
    private final Supplier<String> supplier;

    private Placeholder(String key, Supplier<String> supplier) {
        this.key = key;
        this.supplier = supplier;
    }

    public static Placeholder of(String key, Supplier<String> supplier) {
        return new Placeholder(key, supplier);
    }

    public String key() {
        return key;
    }

    public String value() {
        return supplier.get();
    }
}
