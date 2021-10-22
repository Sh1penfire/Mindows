package mindows.ui;

import mindustry.gen.Icon;

public class WindowTables {
    public static WindowTable

    schematics = new WindowTable("Schematics", Icon.copy, t -> {
        t.top().left();
        t.labelWrap("PLACEHOLDER").top().left();
    });
}
