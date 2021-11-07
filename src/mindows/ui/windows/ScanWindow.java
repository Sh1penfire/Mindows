package mindows.ui.windows;

import mindows.ui.tables.ScanTable;
import mindows.ui.tables.WindowTable;
import mindustry.gen.Icon;
import mindustry.ui.Styles;

public class ScanWindow extends WindowTable {
    public ScanWindow(){
        super("Scanner", Icon.eyeSmall, t -> {});
    }

    @Override
    public void build() {
        top();
        topBar();

        add(new ScanTable()).grow();

        resizeButton();
    }
}
