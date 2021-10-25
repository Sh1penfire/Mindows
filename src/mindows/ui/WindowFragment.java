package mindows.ui;

import arc.scene.*;
import mindows.ui.tables.*;
import mindustry.ui.fragments.*;

import static mindows.ui.WindowTables.*;

@SuppressWarnings("all")
public class WindowFragment extends Fragment{
    @Override
    public void build(Group parent){
        parent.fill(t -> {
            t.name = "Windows";
            t.visible(() -> parent.visible);

            // windows (totally not a copyright violation)
            t.center().right();
            t.add(schematics).size(250f).visible(false);

            // sidebar
            t.add(new TaskbarTable(
                schematics
            )).visible(true);
        });
    };
}